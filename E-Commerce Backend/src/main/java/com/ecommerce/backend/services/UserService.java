package com.ecommerce.backend.services;

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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private CustomerService customerService;
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
            if(users.size() <= 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.of(Optional.of(users));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<User> addUser(@RequestBody NewUserRequest user){
        try{
            System.out.println(user.getConfirmPassword());
            System.out.println(user.getPassword());
            if(Objects.equals(user.getPassword(), user.getConfirmPassword())){
                System.out.println(user.getEmail());
                User newUser = new User();
                newUser.setPassword(encoder.encode(user.getPassword()));
                newUser.setUsername(user.getEmail());
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
                return ResponseEntity.of(Optional.of(newUser));
            } else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
