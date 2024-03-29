package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.OrderTracking;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface OrderTrackingRepository extends CrudRepository<OrderTracking,Long> {
    List<OrderTracking> findAllBySellerId(Long sellerId);
    OrderTracking findByOrderId(Long orderId);
}
