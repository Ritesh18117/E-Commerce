package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.AdminRepository;
import com.ecommerce.backend.dao.CustomerRepository;
import com.ecommerce.backend.dao.SellerRepository;
import com.ecommerce.backend.entities.Admin;
import com.ecommerce.backend.entities.Customer;
import com.ecommerce.backend.entities.User;
import com.ecommerce.backend.dao.UserRepository;
import com.ecommerce.backend.dto.NewUserRequest;
import com.ecommerce.backend.entities.Seller;
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
    private SellerService sellerService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AdminService adminService;
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
                    adminService.addAdmin(admin);
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
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
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
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        // Check if the Authorization header is not null and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token part by removing "Bearer " prefix
            return authorizationHeader.substring(7); // "Bearer ".length() == 7
        }
        return null; // Return null or handle accordingly if token extraction fails
    }
}
