package com.ecommerce.backend.REST;

import com.ecommerce.backend.dao.ProductRepository;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.sound.sampled.Port;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("/test")
    public String test(){
//        productService.updateImages();
        return "This is test request from Product controller";
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestHeader(value = "Authorization") String authorizationHeader,
                                              MultipartHttpServletRequest request) {
        try {
            return productService.addProduct(authorizationHeader, request);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/myProducts")
    public ResponseEntity<List<Product>> myProducts(@RequestHeader(value = "Authorization") String authorizationHeader){
        return productService.myProducts(authorizationHeader);
    }
    @GetMapping("/approvedProducts/{count}")
    public ResponseEntity<List<Map<String, Object>>> getApprovedProducts(@PathVariable int count){
        return productService.approvedProducts(count);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts(){
        return productService.getAllProduct();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/notApprovedProducts")
    public ResponseEntity<List<Product>> notApprovedProducts(@RequestHeader(value = "Authorization") String authorizationHeader){
        return productService.notApprovedProduct(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/approveProduct/{productId}")
    public ResponseEntity<Map<String, String>> approveProduct(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Long productId){
        return productService.approveProduct(authorizationHeader,productId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/rejectProduct/{productId}")
    public ResponseEntity<Map<String, String>> rejectProduct(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Long productId){
        return productService.rejectProduct(authorizationHeader,productId);
    }

    @GetMapping("/getByProductId/{product_id}")
    public ResponseEntity<List<Map<String, Object>>> getByProductId(@PathVariable Long product_id){
        return productService.findByProductId(product_id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getMyProductVerifyList")
    public ResponseEntity<List<Product>> getMyProductVerifyList(@RequestHeader(value = "Authorization") String authorizationHeader){
        return productService.getMyProductVerifyList(authorizationHeader);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/revokeProductVerify/{productId}")
    public ResponseEntity<Map<String,String>> revokeProductVerify(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Long productId){
        return productService.revokeProductVerify(authorizationHeader,productId);
    }

    @GetMapping("/search/{searchItem}")
    public ResponseEntity<List<Map<String,Object>>> search(@PathVariable String searchItem){
        return productService.search(searchItem);
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PatchMapping("/updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestHeader(value = "Authorization") String authorizationHeader,
                                                 MultipartHttpServletRequest request) {
        try {
            return productService.updateProduct(authorizationHeader, request);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/searchItem/{color}")
    public ResponseEntity<List<Product>> searchByColor(@PathVariable String color){
        return productService.searchByColor(color);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllMyRejectedProducts")
    public ResponseEntity<List<Product>> getAllRejectedProducts(@RequestHeader(value = "Authorization") String authorizationHeader){
        return productService.getAllMyRejectedProducts(authorizationHeader);
    }
}


