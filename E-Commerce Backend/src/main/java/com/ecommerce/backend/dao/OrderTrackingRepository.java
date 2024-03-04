package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.OrderTracking;
import org.springframework.data.repository.CrudRepository;

public interface OrderTrackingRepository extends CrudRepository<OrderTracking,Long> {
}
