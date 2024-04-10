package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.Seller;
import com.ecommerce.backend.services.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.*;
import java.net.*;

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
    @GetMapping("/approvedSellers")
    public ResponseEntity<List<Seller>> getApprovedSellers(@RequestHeader(value = "Authorization") String authorizationHeader){
        return sellerService.approvedSellers(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/rejectedSellers")
    public ResponseEntity<List<Seller>> getRejectedSellers(@RequestHeader(value = "Authorization") String authorizationHeader){
        return sellerService.rejectedSeller(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/approveSeller/{sellerId}")
    public ResponseEntity<Map<String,String>> approveSeller(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Long sellerId){
        return sellerService.approveSeller(authorizationHeader,sellerId);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/rejectSeller/{sellerId}")
    public ResponseEntity<Map<String, String>> rejectSeller(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Long sellerId, @RequestParam("comment") String comment) {
        return sellerService.rejectSeller(authorizationHeader, sellerId, comment);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getMySellerVerifyList")
    public ResponseEntity<List<Seller>> getMySellerVerifyList(@RequestHeader(value = "Authorization") String authorizationHeader){
        return sellerService.getMySellerVerifyList(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/revokeSellerVerify/{sellerId}")
    public ResponseEntity<Map<String,String>> revokeSellerVerify(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Long sellerId){
        return sellerService.revokeSellerVerify(authorizationHeader,sellerId);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getSellerByGST/{gstNumber}")
    public ResponseEntity<Seller> getSellerByGST(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable String gstNumber){
        return sellerService.getSellerByGST(authorizationHeader,gstNumber);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getSellerByLicenceNumber/{licenceNumber}")
    public ResponseEntity<Seller> getSellerByLicenceNumber(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable String licenceNumber){
        return sellerService.getSellerByLicenceNumber(authorizationHeader,licenceNumber);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getSellerByCompanyName/{companyName}")
    public ResponseEntity<Seller> getSellerByCompanyName(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable String companyName){
        return sellerService.getSellerByCompanyName(authorizationHeader,companyName);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getSellerBySellerId/{id}")
    public ResponseEntity<Seller> getSellerBySellerId(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Long id){
        return sellerService.getSellerBySellerId(authorizationHeader,id);
    }
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping("/uploadDocument")
    public ResponseEntity<Map<String,String>> uploadDocuments(@RequestHeader(value = "Authorization") String authorizationHeader,
                                                  @RequestPart("gstDocument") MultipartFile gstDocumentFile,
                                                  @RequestPart("licenceDocument") MultipartFile licenceDocumentFile) {
        return sellerService.uploadDocuments(authorizationHeader,gstDocumentFile, licenceDocumentFile);
    }
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/getMyDocuments")
    public ResponseEntity<Seller> getMyDocument(@RequestHeader(value = "Authorization") String authorizationHeader){
        return sellerService.getMyDocument(authorizationHeader);
    }
}