package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.Seller;
import com.ecommerce.backend.services.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/notApprovedSellers")
    public ResponseEntity<List<Seller>> notApprovedSellers(@RequestHeader(value = "Authorization") String authorizationHeader){
        return sellerService.notApprovedSellers(authorizationHeader);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/approveSeller/{sellerId}")
    public ResponseEntity<Map<String,String>> approveSeller(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Long sellerId){
        return sellerService.approveSeller(authorizationHeader,sellerId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/rejectSeller/{sellerId}")
    public ResponseEntity<Map<String,String>> rejectSeller(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable Long sellerId){
        return sellerService.rejectSeller(authorizationHeader,sellerId);
    }
}
