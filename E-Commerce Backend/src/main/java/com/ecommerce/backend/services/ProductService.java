package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.*;
import com.ecommerce.backend.entities.Admin;
import com.ecommerce.backend.entities.ProductVariation;
import com.ecommerce.backend.entities.Seller;
import com.ecommerce.backend.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;

@Component
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariationRepository productVariationRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    public ResponseEntity<Product> addProduct(@RequestBody Product product,@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Seller seller = sellerRepository.findByUserId(userId);
            product.setSeller(seller);
            product.setApprovalStatus("false");
            if(Objects.equals(seller.getApprovalStatus(), "true")){
                productRepository.save(product);
                product.setSeller(null);
                return ResponseEntity.of(Optional.of(product));
            }
            else{
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
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

    public ResponseEntity<List<Product>> myProducts(@RequestHeader(value = "Authorization") String authorizationHeader) {
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Seller seller = sellerRepository.findByUserId(userId);
            List<Product> products = productRepository.findBySeller_Id(seller.getId());
            for(Product product : products){
                product.setSeller(null);
            }
            return ResponseEntity.of(Optional.of(products));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Map<String, Object>>> approvedProducts(){
        try {
            List<ProductVariation> productVariations = (List<ProductVariation>) productVariationRepository.findAll();

            Map<String,List<String>> groupedData = new HashMap<>();
            for (ProductVariation pv : productVariations) {
                String key = String.valueOf(pv.getProduct().getId());

                if (!groupedData.containsKey(key)) {
                    groupedData.put(key, new ArrayList<>());
                }

                List<String> sizeQuanList = groupedData.get(key);
                sizeQuanList.add(pv.getSize() + ", " + pv.getQuantity());
            }

            List<Map<String, Object>> outputList = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : groupedData.entrySet()) {
                Map<String, Object> productGroup = new HashMap<>();
                Optional<Product> product = productRepository.findById(Long.valueOf(entry.getKey()));

                if(product.isPresent() && Objects.equals(product.get().getApprovalStatus(), "true")){
                    product.get().setSeller(null);
                    product.get().setMargin(null);
                    productGroup.put("product", product);
                    productGroup.put("size_quan", entry.getValue());

                    outputList.add(productGroup);
                }else{
                    continue;
                }
            }
            return ResponseEntity.of(Optional.of(outputList));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Product>> notApprovedProduct(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Admin admin = adminRepository.findByUserId(userId);
            if (admin != null){
                List<Product> products = productRepository.findAllByApprovalStatus("false");
                for (Product product : products){
                    product.setSeller(null);
                }
                return ResponseEntity.of(Optional.ofNullable(products));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String, String>> approveProduct(@RequestHeader(value = "Authorization") String authorizationHeader, Long productId){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Admin admin = adminRepository.findByUserId(userId);
            if(admin != null && Objects.equals(admin.getStatus(), "Active")){
                Optional<Product> product = productRepository.findById(productId);
                if(product.isPresent()){
                    product.get().setApprovalStatus("true");
                    productRepository.save(product.get());
                    Map<String, String> output = new HashMap<>();
                    output.put("message", "Successfully Approved!!");
                    return ResponseEntity.of(Optional.of(output));
                } else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String, String>> rejectProduct(@RequestHeader(value = "Authorization") String authorizationHeader, Long productId){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Admin admin = adminRepository.findByUserId(userId);
            if(admin != null && Objects.equals(admin.getStatus(), "Active")){
                Optional<Product> product = productRepository.findById(productId);
                if(product.isPresent()){
                    product.get().setApprovalStatus("rejected");
                    productRepository.save(product.get());
                    Map<String, String> output = new HashMap<>();
                    output.put("message", "Product Rejected!s!!");
                    return ResponseEntity.of(Optional.of(output));
                } else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //For testing here Otherwise move to Admin Service
    public ResponseEntity<List<Product>> getAllProduct(){
        try{
            List<Product> products = (List<Product>) productRepository.findAll();
            if(products.size() <= 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.of(Optional.of(products));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
