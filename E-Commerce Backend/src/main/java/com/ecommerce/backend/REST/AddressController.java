package com.ecommerce.backend.REST;

import com.ecommerce.backend.services.AddressService;
import com.ecommerce.backend.entities.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/test")
    public String test(){
        return "This is test from Address Controller!";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<Address>> getAllAddress(){
        return addressService.getAllAddress();
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/myAddresses")
    public ResponseEntity<List<Address>> getMyAddresses(@RequestHeader(value = "Authorization") String authorizationHeader){
        return addressService.myAddresses(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @Transactional
    @PostMapping("/addAddress")
    public ResponseEntity<Address> addAddress(@RequestBody Address address,@RequestHeader(value = "Authorization") String authorizationHeader){
        return addressService.addAddress(address,authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @Transactional
    @DeleteMapping("/deleteAddress/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable("id") long id, @RequestHeader(value = "Authorization") String authorizationHeader){
        return addressService.deleteAddress(id, authorizationHeader);
    }
}
