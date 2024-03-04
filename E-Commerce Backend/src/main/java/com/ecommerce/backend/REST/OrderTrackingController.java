package com.ecommerce.backend.REST;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/orderTracking")
public class OrderTrackingController {
 
    @GetMapping("test")
    public String test() {
        return "This is Test";
    }
    
}
