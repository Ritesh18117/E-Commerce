package com.ecommerce.backend.services;

import com.ecommerce.backend.entities.Customer;
import com.ecommerce.backend.dao.CustomerRepository;
import com.ecommerce.backend.dao.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    // For testing it's here else move to AdminService
    public ResponseEntity<List<Customer>> getAllCustomer(){
        try{
            List<Customer> customers = (List<Customer>) customerRepository.findAll();
            if(customers.size() <= 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.of(Optional.of(customers));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        try{
            customerRepository.save(customer);
            return ResponseEntity.of(Optional.of(customer));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Customer> myProfile(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Customer customer = customerRepository.findByUserId(userId);
            customer.getUser().setPassword(null);
            return ResponseEntity.of(Optional.of(customer));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Customer> updateProfile(@RequestBody Customer updatedCustomer, @RequestHeader(value = "Authorization") String authorizationHeader){
        try {
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Customer existingCustomer = customerRepository.findByUserId(userId);

            updatedCustomer.setUser(existingCustomer.getUser());

            BeanUtils.copyProperties(updatedCustomer, existingCustomer, "id");
            Customer savedCustomer = customerRepository.save(existingCustomer);

            savedCustomer.getUser().setPassword(null);
            return ResponseEntity.of(Optional.of(savedCustomer));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
