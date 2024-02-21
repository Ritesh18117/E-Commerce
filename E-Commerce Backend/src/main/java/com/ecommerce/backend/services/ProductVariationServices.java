package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.ProductRepository;
import com.ecommerce.backend.dao.ProductVariationRepository;
import com.ecommerce.backend.dao.SellerRepository;
import com.ecommerce.backend.dao.UserRepository;
import com.ecommerce.backend.dto.ProductVariationRequest;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.entities.ProductVariation;
import com.ecommerce.backend.entities.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;

@Component
public class ProductVariationServices {
    @Autowired
    private ProductVariationRepository productVariationRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<List<ProductVariation>> getAllProductVariation(){
        try{
            List<ProductVariation> productVariations = (List<ProductVariation>) productVariationRepository.findAll();
            if(productVariations.size() <= 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            for(ProductVariation productVariation : productVariations){
                Long product_id = productVariation.getProduct().getId();
            }
            return ResponseEntity.of(Optional.of(productVariations));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    public ResponseEntity<ProductVariation> addProductVariation(@RequestHeader(value = "Authorization") String authorizationHeader,@RequestBody ProductVariation productVariation){
//        try{
//            String token = extractTokenFromHeader(authorizationHeader);
//            String username = jwtService.extractUsername(token);
//            Long userId = userRepository.findByUsername(username).getId();
//            Seller seller = sellerRepository.findByUserId(userId);
//            Optional<Product> product = productRepository.findById(productVariation.getProduct().getId());
//            if (Objects.equals(seller.getId(), product.get().getSeller().getId())){
//                if(Objects.equals(product.get().getApprovalStatus(), "true")){
//                    productVariationRepository.save(productVariation);
//                    return ResponseEntity.of(Optional.of(productVariation));
//                }
//                else {
//                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//                }
//            }
//            else{
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    public ResponseEntity<Map<String, Object>> addProductVariation(@RequestHeader(value = "Authorization") String authorizationHeader,@RequestBody ProductVariationRequest productVariationRequest){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Seller seller = sellerRepository.findByUserId(userId);
            Optional<Product> product = productRepository.findById(productVariationRequest.getProduct().getId());
            System.out.println(productVariationRequest.getSize_quant());

            if (Objects.equals(seller.getId(), product.get().getSeller().getId())){
                if(Objects.equals(product.get().getApprovalStatus(), "true")){
                    for(ArrayList<String> size_quant : productVariationRequest.getSize_quant()){
                        String size = size_quant.get(0);
                        String quantity = size_quant.get(1);
                        ProductVariation productVariation = new ProductVariation();
                        productVariation.setProduct(product.get());
                        productVariation.setSize(size);
                        productVariation.setQuantity(Integer.parseInt(quantity));
                        productVariationRepository.save(productVariation);
                    }
                    Map<String, Object> output = new HashMap<>();
                    output.put("message","Added Successfully!!");
                    return ResponseEntity.of(Optional.of(output));
                }
                else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Optional<ProductVariation>> updateProductVariation(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody ProductVariation updatedproductVariation){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            System.out.println(username);
            Long userId = userRepository.findByUsername(username).getId();
            Seller seller = sellerRepository.findByUserId(userId);
            Optional<Product> product = productRepository.findById(updatedproductVariation.getProduct().getId());
            if (seller.getId() == product.get().getSeller().getId()){
                if(Objects.equals(product.get().getApprovalStatus(), "true")){
                    Optional<ProductVariation> existingProductVariation = productVariationRepository.findById(updatedproductVariation.getId());
                    existingProductVariation.get().setQuantity(updatedproductVariation.getQuantity());
                    productVariationRepository.save(existingProductVariation.get());
                    return ResponseEntity.of(Optional.of(existingProductVariation));
                }
                else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<ProductVariation> getProductVariationById(Long id){
        try{
            Optional<ProductVariation> productVariation = productVariationRepository.findById(id);
            if(productVariation.isPresent()){
                return ResponseEntity.of(Optional.of(productVariation.get()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch(Exception e){
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
