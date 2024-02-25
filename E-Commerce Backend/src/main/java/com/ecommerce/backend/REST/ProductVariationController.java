package com.ecommerce.backend.REST;

import com.ecommerce.backend.dto.ProductVariationRequest;
import com.ecommerce.backend.entities.ProductVariation;
import com.ecommerce.backend.services.ProductVariationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/productVariation")
public class ProductVariationController {
    @Autowired
    private ProductVariationServices productVariationServices;

    @GetMapping("/test")
    public String test(){
        return "This is test for ProductVariation request";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductVariation>> getAllProductVariation(){
        return productVariationServices.getAllProductVariation();
    }
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping("/addProductVariation")
    public ResponseEntity<Map<String, Object>> addProductVariation(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody ProductVariationRequest productVariationRequest){
        return productVariationServices.addProductVariation(authorizationHeader,productVariationRequest);
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PatchMapping("/updateProductVariation")
    public ResponseEntity<Optional<ProductVariation>> updateProductVariation(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody ProductVariation productVariation){
        return productVariationServices.updateProductVariation(authorizationHeader,productVariation);
    }

    @GetMapping("/getProductVariationById/{id}")
    public ResponseEntity<ProductVariation> getProductVariationById(@PathVariable Long id){
        return productVariationServices.getProductVariationById(id);
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @DeleteMapping("/deleteByProductVariationId/{productId}/{size}")
    public ResponseEntity<Map<String,String>> deleteByProductIdAndSize(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Long productId,@PathVariable String size){
        return productVariationServices.deleteByProductIdAndSize(authorizationHeader,productId,size);
    }

}
