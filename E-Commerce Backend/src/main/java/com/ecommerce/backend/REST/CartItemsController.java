package com.ecommerce.backend.REST;

import com.ecommerce.backend.services.CartItemsService;
import com.ecommerce.backend.dto.CartItemRequest;
import com.ecommerce.backend.entities.CartItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartItemsController {
    @Autowired
    private CartItemsService cartItemsService;

    @GetMapping("/test")
    public String test() {
        return "This is test from Cart";
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/myCart")
    public ResponseEntity<List<CartItems>> myCart(@RequestHeader(value = "Authorization") String authorizationHeader) {
        return cartItemsService.getMyCart(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/addToCart")
    public ResponseEntity<CartItems> addToCart(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody CartItemRequest cartItemRequest) {
//        {
//            "product_id": 7,
//                "color": "Orange",
//                "size": "8",
//                "quantity": 1
//        }
        return cartItemsService.addToCart(authorizationHeader, cartItemRequest);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/removeItem/{cartItem_id}")
    public ResponseEntity<Map<String, String>> removeItem(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Long cartItem_id){
        return cartItemsService.removeCartItem(authorizationHeader,cartItem_id);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PatchMapping("/addQuantity/{cartItemId}")
    public ResponseEntity<Map<String, String>> addQuantity(@PathVariable("cartItemId") Long cartItemId, @RequestHeader(value = "Authorization") String authorizationHeader) {
        return cartItemsService.addQuantity(cartItemId, authorizationHeader);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PatchMapping("/substarctQuantity/{cartItemId}")
    public ResponseEntity<Map<String, String>> substarctQuantity(@PathVariable("cartItemId") Long cartItemId, @RequestHeader(value = "Authorization") String authorizationHeader) {
        return cartItemsService.substractQuantity(cartItemId,authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<CartItems>> getAllCartItems(){
        return cartItemsService.getAllCartItems();
    }
}