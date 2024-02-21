package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.Order;
import com.ecommerce.backend.services.OrderService;
import com.ecommerce.backend.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    public String test(){
        return "This is test from Order Controller!";
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAllOrder(){
        return orderService.getAllOrder();
    }
//    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/placeOrder")
    // https://chat.openai.com/share/665ce237-6222-4842-82ec-73e8254153e4
    public ResponseEntity<String> addOrder(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody OrderRequest orderRequest){
        return orderService.placeOrder(authorizationHeader,orderRequest);
    }
}
