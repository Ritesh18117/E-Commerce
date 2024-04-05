package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {
    List<Order> findAllByCustomerId(Long customerId);
}
