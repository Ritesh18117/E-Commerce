package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {
    List<Product> findBySeller_Id(long sellerId);
    List<Product> findAllByApprovalStatus(String approvalStatus);
}
