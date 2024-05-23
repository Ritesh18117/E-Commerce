package com.ecommerce.backend.dao;


import com.ecommerce.backend.entities.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ReviewRepository extends CrudRepository<Review,Long> {
    List<Review> getReviewByProductId(Long Product_id);
    List<Review> getReviewByCustomerId(Long Customer_id);
//    @Query("SELECT r FROM Review r JOIN FETCH r.customer WHERE r.product.id = :productId")
//    List<Review> getReviewByProductId(Long productId);
}
