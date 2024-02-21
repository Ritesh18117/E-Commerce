package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.Customer;
import com.ecommerce.backend.services.AdminService;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.entities.Seller;
import com.ecommerce.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ProductService productService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllSeller")
    public ResponseEntity<List<Seller>> getAllSeller(){
        return adminService.getAllSeller();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllCustomer")
    public ResponseEntity<List<Customer>> getAllCustomer(){
        return adminService.getAllCustomer();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/adminProfile")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProduct(){
        return adminService.getAllProduct();
    }

//    @DeleteMapping("/deleteUser/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable Long id){
//        return adminService.deleteUser(id);
//    }
}
