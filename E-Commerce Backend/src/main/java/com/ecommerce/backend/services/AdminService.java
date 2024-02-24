package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.*;
import com.ecommerce.backend.entities.Admin;
import com.ecommerce.backend.entities.Customer;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.entities.Seller;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<Seller>> getAllSeller(){
        try{
            List<Seller> sellerList = (List<Seller>) sellerRepository.findAll();
            if(sellerList.size() <= 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(sellerList));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Customer>> getAllCustomer(){
        try{
            List<Customer> customerList = (List<Customer>) customerRepository.findAll();
            if(customerList.size() <= 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(customerList));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Product>> getAllProduct(){
        try{
            List<Product> products = (List<Product>) productRepository.findAll();
            if(products.size() <= 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.of(Optional.of(products));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin){
        try{
            admin.setStatus("inActive");
            adminRepository.save(admin);
            return ResponseEntity.of(Optional.of(admin));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Admin> myProfile(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Admin admin = adminRepository.findByUserId(userId);
            if (admin != null){
                admin.getUser().setPassword(null);
                return ResponseEntity.of(Optional.of(admin));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Admin> updateProfile(@RequestHeader(value = "Authorization") String authorizationHeader, Admin updatedAdmin){
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Admin existingAdmin = adminRepository.findByUserId(userId);
            System.out.println("Admin Id : " + existingAdmin.getId());

            String existingStatus = existingAdmin.getStatus();
            updatedAdmin.setStatus(existingStatus);
            Long id = existingAdmin.getId();
            updatedAdmin.setId(id);

            updatedAdmin.setUser(existingAdmin.getUser());
            System.out.println(updatedAdmin.getName());

            BeanUtils.copyProperties(updatedAdmin, existingAdmin, "id");
            Admin savedAdmin = adminRepository.save(existingAdmin);
            System.out.println(savedAdmin.getName());

            savedAdmin.getUser().setPassword(null);
            return ResponseEntity.of(Optional.of(savedAdmin));
        } catch (Exception e){
            e.printStackTrace();
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
