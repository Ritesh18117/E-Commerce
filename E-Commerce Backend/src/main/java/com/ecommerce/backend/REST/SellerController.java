package com.ecommerce.backend.REST;

import com.ecommerce.backend.services.SellerService;
import com.ecommerce.backend.entities.Seller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/seller")
@Validated
public class SellerController {
    @Autowired
    private SellerService sellerService;
    @GetMapping("/test")
    public String test(){
        return "This is Seller Test For Route!";
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/myProfile")
    public ResponseEntity<Optional<Seller>> getSellerById(@RequestHeader(value = "Authorization") String authorizationHeader){
        return sellerService.getSellerById(authorizationHeader);
    }
    @PostMapping("/newSeller")
    public ResponseEntity<Seller> newSeller(@Valid @RequestBody Seller seller){
        return sellerService.newSeller(seller);
    }
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PatchMapping("/updateProfile")
    public ResponseEntity<Seller> updateSellerProfile(@RequestBody Seller updatedSeller,@RequestHeader(value = "Authorization") String authorizationHeader){
        return sellerService.updateSellerProfile(updatedSeller,authorizationHeader);
    }
}
