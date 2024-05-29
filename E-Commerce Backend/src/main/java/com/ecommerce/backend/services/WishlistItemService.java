package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.*;
import com.ecommerce.backend.dto.WishlistItemRequest;
import com.ecommerce.backend.entities.WishlistItem;
import com.ecommerce.backend.entities.Customer;
import com.ecommerce.backend.entities.Product;
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
public class WishlistItemService {
    @Autowired
    private WishlistItemRepository wishlistItemRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<List<WishlistItem>> getMyWishlist(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Customer customer = customerRepository.findByUserId(userId);
            List<WishlistItem> wishlistItems = wishlistItemRepository.findAllByCustomerId(customer.getId());
            for(WishlistItem x : wishlistItems){
                x.setCustomer(null);
                x.getProduct().setSeller(null);
            }
            return ResponseEntity.of(Optional.of(wishlistItems));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<WishlistItem> addToWishlist(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody WishlistItemRequest wishlistItemRequest) {
        try{
            Optional<Product> product = productRepository.findById(wishlistItemRequest.getProductId());
            if(product.isPresent() && Objects.equals(product.get().getApprovalStatus(), "true")) {
                Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
                Customer customer = customerRepository.findByUserId(userId);

                WishlistItem wishlistItem = new WishlistItem();
                wishlistItem.setCustomer(customer);
                wishlistItem.setProduct(product.get());
//                wishlistItem.setAddedDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                wishlistItemRepository.save(wishlistItem);
                return ResponseEntity.of(Optional.of(wishlistItem));
            }
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String, String>> removeWishlistItem(@RequestHeader(value = "Authorization") String authorizationHeader, Long wishlistItem_id) {
        try {
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Customer customer = customerRepository.findByUserId(userId);

            if (customer != null) {
                wishlistItemRepository.deleteById(wishlistItem_id);
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

    public ResponseEntity<List<WishlistItem>> getAllWishlistItems(){
        try{
            List<WishlistItem> wishlistItems = (List<WishlistItem>) wishlistItemRepository.findAll();
            return ResponseEntity.of(Optional.of(wishlistItems));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
