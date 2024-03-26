package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.AdminRepository;
import com.ecommerce.backend.entities.Admin;
import com.ecommerce.backend.entities.OrderTracking;
import com.ecommerce.backend.entities.Seller;
import com.ecommerce.backend.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ecommerce.backend.dao.OrderRepository;
import com.ecommerce.backend.dao.OrderTrackingRepository;
import com.ecommerce.backend.dao.SellerRepository;
import org.springframework.web.bind.annotation.RequestHeader;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @Autowired
    private AdminRepository adminRepository;

    public ResponseEntity<List<OrderTracking>> getAllOrderTracking(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            if(admin != null){
                List<OrderTracking> orderTracings = (List<OrderTracking>) orderTrackingRepository.findAll();
                for (OrderTracking ot : orderTracings) {
                    ot.getOrder().getCustomer().setUser(null);
                    ot.getOrder().getProductVariation().getProduct().setSeller(null);
                    ot.getOrder().getProductVariation().getProduct().setVerifiedBy(null);
                    ot.getSeller().setUser(null);
                    ot.getSeller().setVerifiedBy(null);
                }

                return ResponseEntity.of(Optional.of(orderTracings));
            } else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<OrderTracking>> getMyOrderTracking(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Seller seller = sellerRepository.findByUserId(userId);
            List<OrderTracking> orderTracings = orderTrackingRepository.findAllBySellerId(seller.getId());
            for (OrderTracking ot : orderTracings) {
                ot.getOrder().getCustomer().setUser(null);
                ot.getOrder().getProductVariation().getProduct().setSeller(null);
                ot.getOrder().getProductVariation().getProduct().setVerifiedBy(null);
                ot.getSeller().setUser(null);
                ot.getSeller().setVerifiedBy(null);
            }
            return ResponseEntity.of(Optional.of(orderTracings));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String,String>> statusChange(@RequestHeader(value = "Authorization") String authorizationHeader,Long orderTrackingId,String status){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Seller seller = sellerRepository.findByUserId(userId);
            System.out.println(seller.getId());
            System.out.println(orderTrackingId);
            Optional<OrderTracking> orderTracking = orderTrackingRepository.findById(orderTrackingId);
            System.out.println(orderTracking.get().getId());
            if(Objects.equals(seller.getId(), orderTracking.get().getSeller().getId())){
                if(Objects.equals(status, "packed")){
                    orderTracking.get().setStatus(OrderStatus.PACKED);
                    orderTracking.get().setStatusChangedAt(Date.valueOf(LocalDate.now()));
                    orderTrackingRepository.save(orderTracking.get());
                    Map<String,String> output = new HashMap<>();
                    output.put("Message","Changed Successfully");
                    return ResponseEntity.of(Optional.of(output));
                }
                else if(Objects.equals(status, "shipped")){
                    orderTracking.get().setStatus(OrderStatus.SHIPPED);
                    orderTracking.get().setStatusChangedAt(Date.valueOf(LocalDate.now()));
                    orderTrackingRepository.save(orderTracking.get());
                    Map<String,String> output = new HashMap<>();
                    output.put("Message","Changed Successfully");
                    return ResponseEntity.of(Optional.of(output));
                }
                else if(Objects.equals(status, "at_delivery_centre")){
                    orderTracking.get().setStatus(OrderStatus.AT_DELIVERY_CENTRE);
                    orderTracking.get().setStatusChangedAt(Date.valueOf(LocalDate.now()));
                    orderTrackingRepository.save(orderTracking.get());
                    Map<String,String> output = new HashMap<>();
                    output.put("Message","Changed Successfully");
                    return ResponseEntity.of(Optional.of(output));
                }
                else if(Objects.equals(status, "out_for_delivery")){
                    orderTracking.get().setStatus(OrderStatus.OUT_FOR_DELIVERY);
                    orderTracking.get().setStatusChangedAt(Date.valueOf(LocalDate.now()));
                    orderTrackingRepository.save(orderTracking.get());
                    Map<String,String> output = new HashMap<>();
                    output.put("Message","Changed Successfully");
                    return ResponseEntity.of(Optional.of(output));
                }
                else if(Objects.equals(status, "delivered")){
                    orderTracking.get().setStatus(OrderStatus.DELIVERED);
                    orderTracking.get().setStatusChangedAt(Date.valueOf(LocalDate.now()));
                    orderTrackingRepository.save(orderTracking.get());
                    Map<String,String> output = new HashMap<>();
                    output.put("Message","Changed Successfully");
                    return ResponseEntity.of(Optional.of(output));
                }
                else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            } else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
