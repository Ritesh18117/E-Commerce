package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.*;
import com.ecommerce.backend.dto.OrderRequest;
import com.ecommerce.backend.dto.ProductRequest;
import com.ecommerce.backend.entities.Address;
import com.ecommerce.backend.entities.Customer;
import com.ecommerce.backend.entities.Order;
import com.ecommerce.backend.entities.ProductVariation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private ProductVariationRepository productVariationRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;

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

    @Transactional
    public ResponseEntity<Map<String,String>> placeOrder(@RequestHeader(value = "Authorization") String authorizationHeader, OrderRequest orderRequest){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);
            List<ProductRequest> productVariations = orderRequest.getProductVariations();
            Address address = orderRequest.getAddress();
            for(ProductRequest productRequest : productVariations){
                Order order = new Order();
                order.setCustomer(customer); b
                Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productRequest.getId());
                productVariationOptional.ifPresent(productVariation -> order.setProductVariation(productVariation));
                order.setAddress(orderRequest.getAddress());
                order.setOrderStatus("false");
                order.setOrderDate(Date.valueOf(LocalDate.now()));
                order.setQuantity(productRequest.getQuantity());
                order.setComment(orderRequest.getComment());
                order.setDateOfDelivery(Date.valueOf(LocalDate.now().plusDays(7)));
                productVariationOptional.ifPresent(productVariation -> order.setTotalPrice((productVariationOptional.get().getProduct().getPrice() * productRequest.getQuantity()) - ((productRequest.getQuantity() * productVariationOptional.get().getProduct().getPrice())/100 * productVariationOptional.get().getProduct().getDiscount())));
                orderRepository.save(order);
                productVariationOptional.get().setQuantity(productVariationOptional.get().getQuantity() - productRequest.getQuantity());
                cartItemsRepository.deleteByCustomerIdAndProductVariationId(customer.getId(), productVariationOptional.get().getId());
            }
            Map<String,String> output = new HashMap<>();
            output.put("Message","Order Placed Succcessfully!!!");
            return ResponseEntity.of(Optional.of(output));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<List<Order>> myOrders(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);
            List<Order> orders = (List<Order>) orderRepository.findAll();
            if(!orders.isEmpty()){
                for(Order order : orders){
                    order.getCustomer().setUser(null);
                    order.getProductVariation().getProduct().setSeller(null);
                    order.getProductVariation().getProduct().setVerifiedBy(null);
                }
                return ResponseEntity.of(Optional.of(orders));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
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

