package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.OrderTracking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderTrackingRepository extends CrudRepository<OrderTracking,Long> {
    @Query(value = "SELECT * FROM order_tracking o WHERE o.seller_id = :sellerId ORDER BY o.created_at DESC OFFSET 5 * :minus LIMIT 5 * :count - 5 * :minus", nativeQuery = true)
    List<OrderTracking> getAllBySellerId(Long sellerId,int count,int minus);
    @Query(value = "SELECT * FROM order_tracking o WHERE o.seller_id = :sellerId AND o.status =:status ORDER BY o.created_at ASC OFFSET 5 * :minus LIMIT 5 * :count - 5 * :minus", nativeQuery = true)
    List<OrderTracking> getBySellerIdAndStatus(Long sellerId,String status ,int count,int minus);
    OrderTracking findByOrderId(Long orderId);
    @Query(value = "SELECT * FROM order_tracking o WHERE o.status =:status ORDER BY o.created_at DESC OFFSET 5 * :minus LIMIT 5 * :count - 5 * :minus", nativeQuery = true)
    List<OrderTracking> getByStatusPageBreak(String status, int count,int minus);
}
