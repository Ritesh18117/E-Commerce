package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.*;
import com.ecommerce.backend.entities.*;
import com.ecommerce.backend.dto.NewUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private SuperAdminRepository superAdminRepository;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private SuperAdminService superAdminService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    @Lazy
    private PasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userDetail = Optional.ofNullable(userRepository.findByUsername(username));

        // Converting userDetail to UserDetails
        return userDetail.map(UserDetailss::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
    public ResponseEntity<List<User>> getAllUser(){
        try{
            // Test for change Password ===============
//            Optional<User> user = userRepository.findById(21L);
//            user.get().setPassword(encoder.encode("shopshow@shopshow.com"));
//            userRepository.save(user.get());
            // ===================
            List<User> users = (List<User>) userRepository.findAll();
            if(users.size() <= 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(users));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<User> addUser(@RequestBody NewUserRequest user){
        try{
            if(Objects.equals(user.getPassword(), user.getConfirmPassword())){
                User newUser = new User();
                newUser.setPassword(encoder.encode(user.getPassword()));
                newUser.setUsername(user.getUsername());
                newUser.setRole(user.getRole());
                userRepository.save(newUser);
                if(Objects.equals(user.getRole(), "ROLE_SELLER")){
                    Seller seller = new Seller();
                    seller.setUser(newUser);
                    seller.setApprovalStatus("false");
                    seller.setStatusChangeDate(Date.valueOf(LocalDate.now()));
                    sellerService.newSeller(seller);
                }
                if(Objects.equals(user.getRole(), "ROLE_CUSTOMER")){
                    Customer customer = new Customer();
                    customer.setUser(newUser);
                    customerService.addCustomer(customer);
                }
                if(Objects.equals(user.getRole(), "ROLE_ADMIN")){
                    Admin admin = new Admin();
                    admin.setUser(newUser);
                    admin.setVerifiedSellers(new ArrayList<>());
                    admin.setVerifiedProducts(new ArrayList<>());
                    adminService.addAdmin(admin);
                }
                if(Objects.equals(user.getRole(), "ROLE_SUPER_ADMIN")){
                    SuperAdmin superAdmin = new SuperAdmin();
                    superAdmin.setUser(newUser);
                    superAdminService.addSuperAdmin(superAdmin);
                }
                return ResponseEntity.of(Optional.of(newUser));
            } else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String,String>> getUsername(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Optional<User> user = userRepository.findById(userId);
            if (Objects.equals(user.get().getRole(), "ROLE_SELLER")) {
                Seller seller = sellerRepository.findByUserId(userId);
                Map<String, String> output = new HashMap<>();
                output.put("name", seller.getCompanyName());
                return ResponseEntity.of(Optional.of(output));
            } else if (Objects.equals(user.get().getRole(), "ROLE_CUSTOMER")) {
                Customer customer = customerRepository.findByUserId(userId);
                Map<String, String> output = new HashMap<>();
                output.put("name", customer.getName());
                return ResponseEntity.of(Optional.of(output));
            } else if (Objects.equals(user.get().getRole(), "ROLE_ADMIN")) {
                Admin admin = adminRepository.findByUserId(userId);
                Map<String, String> output = new HashMap<>();
                output.put("name", admin.getName());
                return ResponseEntity.of(Optional.of(output));
            } else if(Objects.equals(user.get().getRole(),"ROLE_SUPER_ADMIN")){
                SuperAdmin superAdmin = superAdminRepository.findByUserId(userId);
                Map<String,String> output = new HashMap<>();
                output.put("name",superAdmin.getName());
                return ResponseEntity.of(Optional.of(output));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
