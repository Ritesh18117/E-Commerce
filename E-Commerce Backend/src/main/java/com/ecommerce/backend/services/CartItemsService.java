package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.*;
import com.ecommerce.backend.dto.CartItemRequest;
import com.ecommerce.backend.entities.CartItems;
import com.ecommerce.backend.entities.Customer;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.entities.ProductVariation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
public class CartItemsService {
    @Autowired
    private CartItemsRepository cartItemsRepository;
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

    public ResponseEntity<List<CartItems>> getMyCart(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);
            List<CartItems> cartItems = cartItemsRepository.findAllByCustomerId(customer.getId());
            for(CartItems x : cartItems){
                x.setCustomer(null);
                x.getProduct().setSeller(null);
                x.getProductVariation().setProduct(null);
            }
            return ResponseEntity.of(Optional.of(cartItems));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<CartItems> addToCart(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody CartItemRequest cartItemRequest) {
        try{
            ProductVariation productVariation = productVariationRepository.findByProductIdAndSize(cartItemRequest.getProduct_id(),cartItemRequest.getSize());
            Optional<Product> product = productRepository.findById(productVariation.getProduct().getId());
            if(Objects.equals(product.get().getApprovalStatus(), "true") && productVariation != null) {
                String token = extractTokenFromHeader(authorizationHeader);
                String username = jwtService.extractUsername(token);
                Long userId = userRepository.findByUsername(username).getId();
                Customer customer = customerRepository.findByUserId(userId);

                CartItems cartItems = new CartItems();
                cartItems.setCustomer(customer);
                cartItems.setProduct(product.get());
                cartItems.setProductVariation(productVariation);
                cartItems.setQuantity(cartItemRequest.getQuantity());
                cartItems.setAddedDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                cartItemsRepository.save(cartItems);
                return ResponseEntity.of(Optional.of(cartItems));
            }
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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

    public ResponseEntity<Map<String, String>> removeCartItem(@RequestHeader(value = "Authorization") String authorizationHeader, Long cartItem_id) {
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);

            if (customer != null) {
                cartItemsRepository.deleteById(cartItem_id);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Item Removed Successfully!!");
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Or another appropriate HTTP status

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    // For testing it's here else move to AdminService
    public ResponseEntity<List<CartItems>> getAllCartItems(){
        try{
            List<CartItems> cartItems = (List<CartItems>) cartItemsRepository.findAll();
            return ResponseEntity.of(Optional.of(cartItems));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String, String>> addQuantity(Long cartItemId,@RequestHeader(value = "Authorization") String authorizationHeader){
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);

            Optional<CartItems> existingCartItemOptional = cartItemsRepository.findById(cartItemId);
            if (existingCartItemOptional.isPresent()) {
                if(customer.getId() == existingCartItemOptional.get().getCustomer().getId()) {
                    CartItems existingCartItem = existingCartItemOptional.get();
                    existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
                    // Save the updated cart item
                    cartItemsRepository.save(existingCartItem);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Updated Successfully!");
                    return ResponseEntity.ok(response);
                }
                else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Cart item not found");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String, String>> substractQuantity(Long cartItemId,@RequestHeader(value = "Authorization") String authorizationHeader){
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);

            Optional<CartItems> existingCartItemOptional = cartItemsRepository.findById(cartItemId);
            if (existingCartItemOptional.isPresent()) {
                if(customer.getId() == existingCartItemOptional.get().getCustomer().getId()) {
                    CartItems existingCartItem = existingCartItemOptional.get();
                    if (existingCartItem.getQuantity() > 1) {
                        existingCartItem.setQuantity(existingCartItem.getQuantity() - 1);
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                    }
                    // Save the updated cart item
                    cartItemsRepository.save(existingCartItem);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Updated Successfully!");
                    return ResponseEntity.ok(response);
                }
                else{
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Cart item not found");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
