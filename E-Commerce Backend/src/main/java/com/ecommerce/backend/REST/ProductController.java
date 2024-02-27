package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        return "This is test request from Product controller";
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product,@RequestHeader(value = "Authorization") String authorizationHeader){
        return productService.addProduct(product,authorizationHeader);
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/myProducts")
    public ResponseEntity<List<Product>> myProducts(@RequestHeader(value = "Authorization") String authorizationHeader){
        return productService.myProducts(authorizationHeader);
    }

    @GetMapping("/approvedProducts")
    public ResponseEntity<List<Map<String, Object>>> getApprovedProducts(){
        return productService.approvedProducts();
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
}


