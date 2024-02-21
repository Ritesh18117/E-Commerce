package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.ProductVariation;
import org.springframework.data.repository.CrudRepository;

public interface ProductVariationRepository extends CrudRepository<ProductVariation,Long> {
    ProductVariation findByProductIdAndSize(long product_id,String size);
}
