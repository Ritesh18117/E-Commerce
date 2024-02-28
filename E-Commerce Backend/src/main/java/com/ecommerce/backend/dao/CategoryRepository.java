package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    @Query("SELECT categoryName FROM Category")
    List<String> findAllCategoryNames();
    Category findByCategoryName(String categoryName);
}
