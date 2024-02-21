package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        System.out.println("helloer");
        return categoryServices.getAllCategory();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addCategory")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        return categoryServices.addCategory(category);
    }
}
