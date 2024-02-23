package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.AdminRepository;
import com.ecommerce.backend.dao.CustomerRepository;
import com.ecommerce.backend.dao.ProductRepository;
import com.ecommerce.backend.dao.SellerRepository;
import com.ecommerce.backend.entities.Admin;
import com.ecommerce.backend.entities.Customer;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.entities.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
            adminRepository.save(admin);
            return ResponseEntity.of(Optional.of(admin));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
