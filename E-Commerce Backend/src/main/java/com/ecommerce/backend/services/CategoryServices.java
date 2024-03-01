package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.AdminRepository;
import com.ecommerce.backend.dao.CategoryRepository;
import com.ecommerce.backend.dao.UserRepository;
import com.ecommerce.backend.entities.Admin;
import com.ecommerce.backend.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;

@Component
public class CategoryServices {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    public ResponseEntity<List<Category>> getAllCategory(){
        try{
            List<Category> categories = (List<Category>) categoryRepository.findAll();
            if(categories.size() <= 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(categories));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Category> addCategory(@RequestHeader(value = "Authorization") String authorizationHeader, Category category){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Admin admin = adminRepository.findByUserId(userId);
            if (Objects.equals(admin.getStatus(), "Active")){
                categoryRepository.save(category);                   
                return ResponseEntity.of(Optional.of(category));
            } else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String,String>> deleteCategroyById(@RequestHeader(value = "Authorization") String authorizationHeader,Long categoryId){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Admin admin = adminRepository.findByUserId(userId);
            if(Objects.equals(admin.getStatus(),"Active")){
                Optional<Category> category = categoryRepository.findById(categoryId);
                if(category.isPresent()){
                    categoryRepository.deleteById(categoryId);
                    Map<String,String> output = new HashMap<>();
                    output.put("Message","Deleted Successfully!");
                    return ResponseEntity.of(Optional.of(output));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Map<String, Object>>> findByCategoryId(Long categoryId){
        return productService.findByCategories(categoryId);
    }


    private String extractTokenFromHeader(String authorizationHeader) {
        // Check if the Authorization header is not null and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token part by removing "Bearer " prefix
            return authorizationHeader.substring(7); // "Bearer ".length() == 7
        }
        return null; // Return null or handle accordingly if token extraction fails
    }
}
