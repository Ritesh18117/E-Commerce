package com.ecommerce.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.backend.dao.OrderRepository;
import com.ecommerce.backend.dao.OrderTrackingRepository;
import com.ecommerce.backend.dao.SellerRepository;

@Component
public class OrderTrackingService {
    @Autowired
    private OrderTrackingRepository orderTrackingRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SellerRepository sellerRepository;

}
