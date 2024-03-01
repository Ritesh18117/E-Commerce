package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    @Autowired
    private CategoryServices categoryServices;

    @GetMapping("/test")
    public String test(){
        return "Hello World from Category!!";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAllCategory(){
        return categoryServices.getAllCategory();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addCategory")
    public ResponseEntity<Category> addCategory(@RequestHeader(value = "Authorization") String authorizationHeader,@RequestBody Category category){
        return categoryServices.addCategory(authorizationHeader,category);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteCategoryById/{categoryId}")
    public ResponseEntity<Map<String,String>> deleteCategoryById(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable Long categoryId){
        return categoryServices.deleteCategroyById(authorizationHeader,categoryId);
    }

    @GetMapping("/getProductByCategoryId/{categoryId}")
    public ResponseEntity<List<Map<String, Object>>> getProductByCategoryId(@PathVariable Long categoryId){
        return categoryServices.findByCategoryId(categoryId);
    }
}
