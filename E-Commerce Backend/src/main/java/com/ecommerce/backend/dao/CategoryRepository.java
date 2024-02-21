package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long> {
}
