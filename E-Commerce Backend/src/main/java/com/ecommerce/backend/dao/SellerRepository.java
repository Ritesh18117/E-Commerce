package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Seller;
import org.springframework.data.repository.CrudRepository;

public interface SellerRepository extends CrudRepository<Seller, Long> {
    Seller findByUserId(Long userId);
}
