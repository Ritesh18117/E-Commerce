package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.Address;
import com.ecommerce.backend.entities.Review;
import com.ecommerce.backend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/test")
    public String test(){
        return "This is test for Review request";
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<Review>> getAllReviews() {
        return reviewService.getAllReviews();
    }


    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @Transactional
    @GetMapping("/getReviewsByCustomerId")
    public ResponseEntity<List<Review>> getMyReviews(@RequestHeader(value = "Authorization") String authorizationHeader){
        return reviewService.getMyReviews(authorizationHeader);
    }

    @GetMapping("/getReviewByProductId/{productId}")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long productId){
        return reviewService.getProductReviews(productId);
    }


    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @Transactional
    @PostMapping("/addReview")
    public ResponseEntity<Review> addReview(@RequestBody Review review,@RequestHeader(value = "Authorization") String authorizationHeader){
        return reviewService.addReview(review,authorizationHeader);
    }


//    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @Transactional
    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") long id, @RequestHeader(value = "Authorization") String authorizationHeader){
        return reviewService.deleteReview(id, authorizationHeader);
    }



}
