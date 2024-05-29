package com.ecommerce.backend.REST;

import com.ecommerce.backend.services.WishlistItemService;
import com.ecommerce.backend.dto.WishlistItemRequest;
import com.ecommerce.backend.entities.WishlistItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistItemController {
    @Autowired
    private WishlistItemService wishlistItemService;

    @GetMapping("/test")
    public String test() {
        return "This is test from Wishlist";
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/myWishlist")
    public ResponseEntity<List<WishlistItem>> myWishlist(@RequestHeader(value = "Authorization") String authorizationHeader) {
        return wishlistItemService.getMyWishlist(authorizationHeader);
    }

//    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/addToWishlist")
    public ResponseEntity<WishlistItem> addToWishlist(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody WishlistItemRequest wishlistItemRequest) {
        return wishlistItemService.addToWishlist(authorizationHeader, wishlistItemRequest);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/removeItem/{wishlistItem_id}")
    public ResponseEntity<Map<String, String>> removeItem(@RequestHeader(value = "Authorization") String authorizationHeader, @PathVariable Long wishlistItem_id){
        return wishlistItemService.removeWishlistItem(authorizationHeader, wishlistItem_id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<WishlistItem>> getAllWishlistItems(){
        return wishlistItemService.getAllWishlistItems();
    }
}
