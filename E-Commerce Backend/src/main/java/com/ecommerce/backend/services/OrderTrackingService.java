package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.*;
import com.ecommerce.backend.entities.*;
import com.ecommerce.backend.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.sql.Date;
import java.time.LocalDate;
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
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductVariationRepository productVariationRepository;

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

    public ResponseEntity<List<OrderTracking>> getAllByOrderTrackingStatus(@RequestHeader(value = "Authorization") String authorizationHeader,String status,int count){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            if(!Objects.equals(admin,null)){
                if(Objects.equals(admin.getStatus(),"Active")){
                    List<OrderTracking> orderTracings = orderTrackingRepository.getByStatusPageBreak(status,count,count-1);
                    for (OrderTracking ot : orderTracings) {
                        ot.getOrder().getCustomer().setUser(null);
                        ot.getOrder().getProductVariation().getProduct().setSeller(null);
                        ot.getOrder().getProductVariation().getProduct().setVerifiedBy(null);
                        ot.getSeller().setUser(null);
                        ot.getSeller().setVerifiedBy(null);
                    }
                    return ResponseEntity.of(Optional.of(orderTracings));
                } else{
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<OrderTracking>> getMyOrderTracking(@RequestHeader(value = "Authorization") String authorizationHeader,int count){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Seller seller = sellerRepository.findByUserId(userId);
            List<OrderTracking> orderTracings = orderTrackingRepository.getAllBySellerId(seller.getId(),count,count-1);
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
            Optional<OrderTracking> orderTracking = orderTrackingRepository.findById(orderTrackingId);
            if(Objects.equals(seller.getId(), orderTracking.get().getSeller().getId())){
                if(Objects.equals(status, "packed")){
                    orderTracking.get().setStatus(OrderStatus.PACKED);
                    orderTracking.get().setStatusChangedAt(Date.valueOf(LocalDate.now()));
                    orderTracking.get().setAlert("");
                    orderTrackingRepository.save(orderTracking.get());
                    System.out.println(orderTracking.get().getStatusChangedAt());
                    Map<String,String> output = new HashMap<>();
                    output.put("Message","Changed Successfully to");
                    return ResponseEntity.of(Optional.of(output));
                }
                else if(Objects.equals(status, "shipped")){
                    orderTracking.get().setStatus(OrderStatus.SHIPPED);
                    System.out.println(Date.valueOf(LocalDate.now()));
                    orderTracking.get().setStatusChangedAt(Date.valueOf(LocalDate.now()));
                    orderTracking.get().setAlert("");
                    orderTrackingRepository.save(orderTracking.get());
                    Map<String,String> output = new HashMap<>();
                    output.put("Message","Changed Successfully");
                    return ResponseEntity.of(Optional.of(output));
                }
                else if(Objects.equals(status, "at_delivery_centre")){
                    orderTracking.get().setStatus(OrderStatus.AT_DELIVERY_CENTRE);
                    orderTracking.get().setStatusChangedAt(Date.valueOf(LocalDate.now()));
                    orderTracking.get().setAlert("");
                    orderTrackingRepository.save(orderTracking.get());
                    Map<String,String> output = new HashMap<>();
                    output.put("Message","Changed Successfully");
                    return ResponseEntity.of(Optional.of(output));
                }
                else if(Objects.equals(status, "out_for_delivery")){
                    orderTracking.get().setStatus(OrderStatus.OUT_FOR_DELIVERY);
                    orderTracking.get().setStatusChangedAt(Date.valueOf(LocalDate.now()));
                    orderTracking.get().setAlert("");
                    orderTrackingRepository.save(orderTracking.get());
                    Map<String,String> output = new HashMap<>();
                    output.put("Message","Changed Successfully");
                    return ResponseEntity.of(Optional.of(output));
                }
                else if(Objects.equals(status, "delivered")){
                    orderTracking.get().setStatus(OrderStatus.DELIVERED);
                    orderTracking.get().setStatusChangedAt(Date.valueOf(LocalDate.now()));
                    orderTracking.get().setAlert("");
                    orderTracking.get().getOrder().setDeliveryDate(Date.valueOf(LocalDate.now()));
                    orderTracking.get().getOrder().setOrderStatus("DELIVERED");
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

    public ResponseEntity<Map<String,String>> CancelOrder(@RequestHeader(value = "Authorization") String authorizationHeader,Long orderTrackingId){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Customer customer = customerRepository.findByUserId(userId);
            if(Objects.equals(customer,null)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else{
                Optional<OrderTracking> orderTracking = orderTrackingRepository.findById(orderTrackingId);
                if(Objects.equals(orderTracking.get().getOrder().getCustomer().getId(), customer.getId())){
                    if(orderTracking.get().getStatus() == OrderStatus.ORDER_PLACED || orderTracking.get().getStatus() == OrderStatus.PACKED){
                        long pvId = orderTracking.get().getOrder().getProductVariation().getId();
                        int quantity = orderTracking.get().getOrder().getQuantity();
                        Optional<ProductVariation> pv = productVariationRepository.findById(pvId);
                        pv.get().setQuantity(pv.get().getQuantity() + quantity);

                        orderTracking.get().setStatus(OrderStatus.CANCELLED);
                        orderTracking.get().setStatusChangedAt(Date.valueOf(LocalDate.now()));
                        orderTracking.get().getOrder().setOrderStatus("CANCELLED");
                        orderTracking.get().setAlert("");
                        orderTracking.get().setUpdatedAt(Date.valueOf(LocalDate.now()));

                        productVariationRepository.save(pv.get());
                        orderTrackingRepository.save(orderTracking.get());

                        Map<String,String> output = new HashMap<>();
                        output.put("Message","Changed Successfully");
                        return ResponseEntity.of(Optional.of(output));
                    } else{
                        Map<String, String > output = new HashMap<>();
                        output.put("Error", "Cannot cancel, because shipped");
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(output);
                    }
                } else{
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<OrderTracking> getOrderTrackingByOrderId(@RequestHeader(value = "Authorization") String authorizationHeader,Long orderId){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Customer customer = customerRepository.findByUserId(userId);
            OrderTracking orderTracking = orderTrackingRepository.findByOrderId(orderId);
            if(Objects.equals(customer.getId(),orderTracking.getOrder().getCustomer().getId())){
//                orderTracking.setOrder(null);
                orderTracking.setSeller(null);
                return ResponseEntity.of(Optional.of(orderTracking));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<Map<String,String>> alert(@RequestHeader(value = "Authorization") String authorizationHeader,Long orderTrackingId){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            if(Objects.equals(admin,"null") || Objects.equals(admin.getStatus(),"inActive")){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else{
                Optional<OrderTracking> orderTracking = orderTrackingRepository.findById(orderTrackingId);
                if (orderTracking.isPresent()){
                    orderTracking.get().setAlert("true");
                    orderTrackingRepository.save(orderTracking.get());
                    Map<String,String> output = new HashMap<>();
                    output.put("Message","Alert Send Successfully!");
                    return ResponseEntity.of(Optional.of(output));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<OrderTracking>> getMyOrderTrackingByStatus(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable String status, @PathVariable int count){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Seller seller = sellerRepository.findByUserId(userId);

            List<OrderTracking> orderTracings = orderTrackingRepository.getBySellerIdAndStatus(seller.getId(),status,count,count-1);
            System.out.println(orderTracings.size());
            for (OrderTracking ot : orderTracings) {
                ot.getOrder().getCustomer().setUser(null);
                ot.getOrder().getProductVariation().getProduct().setSeller(null);
                ot.getOrder().getProductVariation().getProduct().setVerifiedBy(null);
                ot.getSeller().setUser(null);
                ot.getSeller().setVerifiedBy(null);
            }
            return ResponseEntity.of(Optional.of(orderTracings));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
