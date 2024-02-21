package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {
}
