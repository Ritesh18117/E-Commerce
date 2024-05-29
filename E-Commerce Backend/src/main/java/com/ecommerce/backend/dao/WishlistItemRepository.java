package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.WishlistItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WishlistItemRepository extends CrudRepository<WishlistItem, Long> {
    List<WishlistItem> findAllByCustomerId(Long customerId);
    WishlistItem findByCustomerIdAndProductId(Long customerId, Long productId);
    void deleteByCustomerIdAndProductId(Long customerId, Long productId);
}
