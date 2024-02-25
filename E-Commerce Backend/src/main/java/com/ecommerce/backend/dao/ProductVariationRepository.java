package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.ProductVariation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductVariationRepository extends CrudRepository<ProductVariation,Long> {
    ProductVariation findByProductIdAndSize(long product_id,String size);
    List<ProductVariation> findAllByProductCategoryId(Long category_id);
    List<ProductVariation> findAllByProductId(Long product_id);

    ProductVariation findByProductIdAndSize(Long product_id,String size);
}
