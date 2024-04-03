package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.Customer;
import com.ecommerce.backend.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@Validated
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/test")
    public String test(){
        return "This is testing from Customer Controller";
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/myProfile")
    public ResponseEntity<Customer> myProfile(@RequestHeader(value = "Authorization") String authorizationHeader){
        return customerService.myProfile(authorizationHeader);
    }

//    @PostMapping("/addCustomer")
//    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer){
//        return customerService.addCustomer(customer);
//    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PatchMapping("/updateCustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer updateCustomer,@RequestHeader(value = "Authorization") String authorizationHeader){
        return customerService.updateProfile(updateCustomer,authorizationHeader);
    }
}
