package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.CustomerRepository;
import com.ecommerce.backend.dao.ReviewRepository;
import com.ecommerce.backend.dao.ProductRepository;
import com.ecommerce.backend.dao.UserRepository;
import com.ecommerce.backend.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;


    // Existing methods...

    public ResponseEntity<List<Review>> getAllReviews() {
        try {
            List<Review> reviews =(List<Review>) reviewRepository.findAll();
            if (reviews.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(reviews);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<List<Review>> getMyReviews(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Customer customer = customerRepository.findByUserId(userId);
            List<Review> reviews = reviewRepository.getReviewByCustomerId(customer.getId());

            return ResponseEntity.of(Optional.of(reviews));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<List<Review>> getProductReviews(Long productId){
        try{

//            Product product = productRepository.findByProductId(productId);
            List<Review> reviews = reviewRepository.getReviewByProductId(productId);

            return ResponseEntity.of(Optional.of(reviews));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Review> addReview( @RequestBody Review review, @RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = jwtService.extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            if( userId != null && Objects.equals(userRepository.findByUsername(username).getRole(), "ROLE_CUSTOMER")){
                Customer customer = customerRepository.findByUserId(userId);
                review.setCustomer(customer);
//                Product product = productRepository.findByProductId(productId);
//                review.setProduct(product);

            }
            reviewRepository.save(review);
            return ResponseEntity.of(Optional.of(review));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<String> deleteReview(long id,@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = jwtService.extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            if( userId != null && Objects.equals(userRepository.findByUsername(username).getRole(), "ROLE_CUSTOMER")){
                Customer customer = customerRepository.findByUserId(userId);
                Optional<Review> review = reviewRepository.findById(id);
                if(customer.getId() == review.get().getCustomer().getId()){
                    reviewRepository.deleteById(id);
                    return ResponseEntity.ok("Deleted Sucessfully!!");
                }
                else{
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }else {
                return ResponseEntity.of(Optional.of("Something Went Wrong!1"));
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}



