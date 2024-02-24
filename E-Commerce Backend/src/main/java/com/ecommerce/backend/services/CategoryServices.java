package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.CategoryRepository;
import com.ecommerce.backend.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryServices {
    @Autowired
    private CategoryRepository categoryRepository;

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

    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        try{
            categoryRepository.save(category);
            return ResponseEntity.of(Optional.of(category));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
