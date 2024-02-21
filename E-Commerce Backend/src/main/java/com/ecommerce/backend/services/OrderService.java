package com.ecommerce.backend.services;

import com.ecommerce.backend.entities.Customer;
import com.ecommerce.backend.entities.Order;
import com.ecommerce.backend.dao.CustomerRepository;
import com.ecommerce.backend.dao.OrderRepository;
import com.ecommerce.backend.dao.ProductRepository;
import com.ecommerce.backend.dao.UserRepository;
import com.ecommerce.backend.dto.OrderRequest;
import com.ecommerce.backend.dto.ProductRequest;
import com.ecommerce.backend.entities.Address;
import com.ecommerce.backend.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<List<Order>> getAllOrder(){
        try{
            List<Order> orders = (List<Order>) orderRepository.findAll();
            if (orders.size() <= 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(orders));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<String> placeOrder(@RequestHeader(value = "Authorization") String authorizationHeader, OrderRequest orderRequest){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);
            List<ProductRequest> products = orderRequest.getProducts();
            Address address = orderRequest.getAddress();
            for(ProductRequest product : products){
                Order order = new Order();
                order.setCustomer(customer);
                Optional<Product> productOptional = productRepository.findById(product.getId());
                productOptional.ifPresent(product1 -> order.setProduct(product1));
                order.setAddress(orderRequest.getAddress());
                order.setOrderStatus("false");
                order.setOrderDate(Date.valueOf(LocalDate.now()));
                order.setQuantity(product.getQuantity());
                order.setComment(orderRequest.getComment());
                order.setDateOfDelivery(Date.valueOf(LocalDate.now().plusDays(7)));
                productOptional.ifPresent(product1 -> order.setTotalPrice((product1.getPrice() * product.getQuantity()) - ((product.getQuantity() * product1.getPrice())/100 * product1.getDiscount())));
                orderRepository.save(order);
            }
            return ResponseEntity.ok("Order Placed Successfully!");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        // Check if the Authorization header is not null and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token part by removing "Bearer " prefix
            return authorizationHeader.substring(7); // "Bearer ".length() == 7
        }
        return null; // Return null or handle accordingly if token extraction fails
    }

}

