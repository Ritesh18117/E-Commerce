package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.OrderTracking;
import com.ecommerce.backend.services.OrderTrackingService;
import org.aspectj.weaver.ast.Or;
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
    @GetMapping("/myOrderTracking/{count}")
    public ResponseEntity<List<OrderTracking>> myOrderTracking(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable int count){
        return orderTrackingService.getMyOrderTracking(authorizationHeader,count);
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/sendAlert/{orderTrackingId}")
    public ResponseEntity<Map<String,String>> sendAlert(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable Long orderTrackingId){
        return orderTrackingService.alert(authorizationHeader,orderTrackingId);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/cancelOrder/{orderTrackingId}")
    public ResponseEntity<Map<String,String>> cancelOrder(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable Long orderTrackingId){
        return orderTrackingService.CancelOrder(authorizationHeader,orderTrackingId);
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/getMyOrderTrackingByStatus/{status}/{count}")
    public ResponseEntity<List<OrderTracking>> getMyOrderTrackingByStatus(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable String status,@PathVariable int count){
        return orderTrackingService.getMyOrderTrackingByStatus(authorizationHeader,status,count);
    }
}