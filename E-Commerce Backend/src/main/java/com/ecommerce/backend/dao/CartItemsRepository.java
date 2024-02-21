package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.CartItems;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemsRepository extends CrudRepository<CartItems,Long> {
    List<CartItems> findAllByCustomerId(Long customer_id);
    CartItems findByCustomerIdAndProductVariationId(Long customerId, Long productVariationId);
}
