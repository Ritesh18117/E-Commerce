package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.*;
import com.ecommerce.backend.entities.*;
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
    private CategoryRepository categoryRepository;
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

    public ResponseEntity<Product> updateProduct(@RequestHeader(value = "Authorization") String authorizationHeader,Product product){
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Seller seller = sellerRepository.findByUserId(userId);

            Optional<Product> existingProduct = productRepository.findById(product.getId());
            if (existingProduct.isPresent()) {
                Product oldProduct = existingProduct.get();
                oldProduct.setSeller(seller);
                oldProduct.getCategory().setId(product.getCategory().getId());
                oldProduct.setName(product.getName());
                oldProduct.setPrice(product.getPrice());
                oldProduct.setDiscount(product.getDiscount());
                oldProduct.setMargin(product.getMargin());
                oldProduct.setGender(product.getGender());
                oldProduct.setColor(product.getColor());
                oldProduct.setDescription(product.getDescription());
                oldProduct.setImageURL(product.getImageURL());
                oldProduct.setApprovalStatus("false");
                oldProduct.setImages(product.getImages());
                productRepository.save(oldProduct);
                return ResponseEntity.of(Optional.of(oldProduct));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
                product.setVerifiedBy(null);
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

    public ResponseEntity<List<Map<String, Object>>> findByCategories(Long category_id){
        try {
            List<ProductVariation> productVariations = (List<ProductVariation>) productVariationRepository.findAllByProductCategoryId(category_id);

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

    public ResponseEntity<List<Map<String, Object>>> findByProductId(Long product_id){
        try {
            List<ProductVariation> productVariations = (List<ProductVariation>) productVariationRepository.findAllByProductId(product_id);

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
                    product.get().setVerifiedBy(admin);
                    admin.addProduct(product.get().getId());
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
                    product.get().setVerifiedBy(admin);
                    admin.addProduct(product.get().getId());
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

    public ResponseEntity<List<Product>> getMyProductVerifyList(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Admin admin = adminRepository.findByUserId(userId);
            List<Product> verifiedProducts = new ArrayList<>();
            for(Long productId : admin.getVerifiedProduct()){
                Optional<Product> product = productRepository.findById(productId);
                product.get().setSeller(null);
                product.get().setCategory(null);
                product.get().setVerifiedBy(null);
                verifiedProducts.add(product.get());
            }
            return ResponseEntity.of(Optional.of(verifiedProducts));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String,String>> revokeProductVerify(@RequestHeader(value = "Authorization") String authorizationHeader, Long productId){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Admin admin = adminRepository.findByUserId(userId);
            Optional<Product> product = productRepository.findById(productId);
            if(product.isPresent()){
                product.get().setVerifiedBy(null);
                product.get().setApprovalStatus("false");
                admin.removeProduct(productId);
                productRepository.save(product.get());
                Map<String,String> output = new HashMap<>();
                output.put("Message","Successfully Revoked");
                return ResponseEntity.of(Optional.of(output));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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

    public ResponseEntity<List<Map<String, Object>>> search(String searchItem){
        try{
            List<String> categories = categoryRepository.findAllCategoryNames();
            searchItem = searchItem.substring(0, 1).toUpperCase() + searchItem.substring(1).toLowerCase();
            if(categories.contains(searchItem)){
                int index = categories.indexOf(searchItem);
                Category category = categoryRepository.findByCategoryName(categories.get(index));
                return this.findByCategories(category.getId());
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
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
