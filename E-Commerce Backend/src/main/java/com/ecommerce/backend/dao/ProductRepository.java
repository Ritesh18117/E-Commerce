package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Customer;
import com.ecommerce.backend.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    List<Product> findBySeller_Id(long sellerId);
    List<Product> findAllByApprovalStatus(String approvalStatus);
//    List<Product> findAllByApprovalStatusAndVerifiedById(String approvalStatus, Long verifiedById);
    List<Product> findAllByColor(String color);
    List<Product> findAllByCategoryId(Long categoryName);
    @Query(value = "SELECT p.product_id FROM products p WHERE p.approval_status = 'true' ORDER BY p.added_date DESC OFFSET 10 * :minus LIMIT 10 * :count - 10 * :minus", nativeQuery = true)
    List<Long> findPageBreakProduct(int count,int minus);
    @Query(value = "SELECT * FROM products p WHERE p.approval_status = 'rejected' AND p.verified_by_admin_id =:verifiedById ORDER BY p.added_date DESC OFFSET 10 * :minus LIMIT 10 * :count - 10 * :minus", nativeQuery = true)
    List<Product> findAllRejectedProductsAndVerifiedById(Long verifiedById,int count,int minus);

//    Customer findByUserId(Long userId);
}
