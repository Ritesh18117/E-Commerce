package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.OrderTracking;
import com.ecommerce.backend.services.OrderTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/orderTracking")
public class OrderTrackingController {
    @Autowired
    private OrderTrackingService orderTrackingService;
    @GetMapping("test")
    public String test() {
        return "This is Test";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<OrderTracking>> getAll(@RequestHeader(value = "Authorization") String authorizationHeader){
        return orderTrackingService.getAllOrderTracking(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/myOrderTracking")
    public ResponseEntity<List<OrderTracking>> myOrderTracking(@RequestHeader(value = "Authorization") String authorizationHeader){
        return orderTrackingService.getMyOrderTracking(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/changeStatus/{orderTrackingId}/{status}")
    public ResponseEntity<Map<String,String>> changeStatus(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Long orderTrackingId,@PathVariable String status){
        return orderTrackingService.statusChange(authorizationHeader,orderTrackingId,status);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/getByOrderId/{orderId}")
    public ResponseEntity<OrderTracking> getByOrderId(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable Long orderId){
        return orderTrackingService.getOrderTrackingByOrderId(authorizationHeader,orderId);
    }
}
