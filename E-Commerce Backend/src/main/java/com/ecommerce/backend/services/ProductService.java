package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.*;
import com.ecommerce.backend.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public ResponseEntity<Product> addProduct(@RequestHeader(value = "Authorization") String authorizationHeader,
                                              MultipartHttpServletRequest request) {
        try {
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Seller seller = sellerRepository.findByUserId(userId);
            if (seller == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if (!Objects.equals(seller.getApprovalStatus(), "true")) {
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
            }

            Product product = new Product();
            product.setSeller(seller);
            product.setApprovalStatus("false");
            product.setName(request.getParameter("name"));
            product.setPrice(Double.parseDouble(request.getParameter("price")));
            product.setDiscount(Double.parseDouble(request.getParameter("discount")));
            product.setMargin(Double.parseDouble(request.getParameter("margin")));
            product.setGender(request.getParameter("gender"));
            product.setColor(request.getParameter("color"));
            product.setDescription(request.getParameter("description"));
            product.setAddedDate(Date.valueOf(LocalDate.now()));

            Optional<Category> category = categoryRepository.findById(Long.valueOf(request.getParameter("category")));
            if(category.isPresent()){
                product.setCategory(category.get());
            }

            MultipartFile image = request.getFile("image");
            if(image != null) {
                product.setImage(convertToBase64(image));
            }

            List<String> base64Images = new ArrayList<>();
            System.out.println(request.getFiles("images"));
            List<MultipartFile> imageFiles = request.getFiles("images");
            System.out.println(imageFiles);
            for (MultipartFile file : imageFiles) {
                base64Images.add(convertToBase64(file));
            }
            product.setImages(base64Images);

            productRepository.save(product);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Product> updateProduct(String authorizationHeader, MultipartHttpServletRequest request) throws IOException {
        Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
        Seller seller = sellerRepository.findByUserId(userId);

        Long productId = Long.valueOf(request.getParameter("id"));

        Optional<Product> existingProductOptional = productRepository.findById(productId);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();

            if (!Objects.equals(existingProduct.getSeller().getId(), seller.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            existingProduct.setSeller(seller);
            existingProduct.getCategory().setId(Long.parseLong(request.getParameter("category")));
            existingProduct.setName(request.getParameter("name"));
            existingProduct.setPrice(Double.parseDouble(request.getParameter("price")));
            existingProduct.setDiscount(Double.parseDouble(request.getParameter("discount")));
            existingProduct.setMargin(Double.parseDouble(request.getParameter("margin")));
            existingProduct.setGender(request.getParameter("gender"));
            existingProduct.setColor(request.getParameter("color"));
            existingProduct.setAddedDate(Date.valueOf(LocalDate.now()));
            existingProduct.setDescription(request.getParameter("description"));

            MultipartFile image = request.getFile("image");
            if(image != null) {
                existingProduct.setImage(convertToBase64(image));
            }

            List<MultipartFile> imageFiles = request.getFiles("images");
            if (imageFiles != null && !imageFiles.isEmpty()) {
                List<String> base64Images = imageFiles.stream()
                        .map(this::convertToBase64)
                        .collect(Collectors.toList());
                existingProduct.setImages(base64Images);
            }

            existingProduct.setApprovalStatus("false");
            existingProduct.setVerifiedBy(null);

            Optional<Admin> admin = adminRepository.findById(seller.getVerifiedBy().getId());
            if(admin.isPresent()){
                admin.get().removeProduct(existingProduct.getId());
                adminRepository.save(admin.get());
            }

            Product savedProduct = productRepository.save(existingProduct);
            return ResponseEntity.ok(savedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    public ResponseEntity<List<Product>> myProducts(@RequestHeader(value = "Authorization") String authorizationHeader) {
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
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

    public ResponseEntity<List<Map<String, Object>>> approvedProducts(int count){
        try {
            List<Long> productsId = productRepository.findPageBreakProduct(count,count-1);

            List<ProductVariation> productVariations = new ArrayList<ProductVariation>();

            for(Long productId : productsId){
                productVariations.addAll(productVariationRepository.findAllByProductId(productId));
            }

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
//                    product.get().setMargin(null);
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
//                    product.get().setMargin(null);
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
//                    product.get().setMargin(null);
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
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
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
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
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
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
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
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            List<Product> verifiedProducts = new ArrayList<>();
            for(Long productId : admin.getVerifiedProducts()){
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
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            Optional<Product> product = productRepository.findById(productId);
            if(product.isPresent()){
                product.get().setVerifiedBy(null);
                product.get().setApprovalStatus("false");
                admin.removeProduct(productId);
                adminRepository.save(admin);
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
            for(Product product : products){
                product.setVerifiedBy(null);
                product.setSeller(null);
            }
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

    public ResponseEntity<List<Product>> searchByColor(String searchItem){
        try{
            //List<String> categories = categoryRepository.findAllCategoryNames();
            searchItem = searchItem.substring(0, 1).toLowerCase() + searchItem.substring(1).toLowerCase();
            List<Product> products = productRepository.findAllByColor(searchItem);
            System.out.println(products);
            System.out.println(searchItem);
            if(!products.isEmpty()){
                return ResponseEntity.of(Optional.of(products));
            }else {
                for(Product product : products){
                    product.setVerifiedBy(null);
                    product.setSeller(null);
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Product>> getAllMyRejectedProducts(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            if(Objects.equals(admin,null)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else {
                List<Product> products = productRepository.findAllByApprovalStatusAndVerifiedById("rejected",admin.getId());
                if(products.isEmpty()){
                   return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                } else{
                    for(Product product : products){
                        product.setVerifiedBy(null);
                        product.setSeller(null);
                    }
                    return ResponseEntity.of(Optional.of(products));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    private String convertToBase64(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


//    public void updateImages() {
//        List<Product> products = (List<Product>) productRepository.findAll(); // Fetch products from the database
//
//        System.out.println("Hello");
//        for (Product product : products) {
//            try {
//                String imageUrl = product.getImageURL();
//                String base64Image = downloadAndConvertToBase64(imageUrl);
//                product.setImage(base64Image);
//
//                List<String> imageUrls = product.getImgs();
//                List<String> base64Images = new ArrayList<>();
//                for (String url : imageUrls) {
//                    try {
//                        String base64 = downloadAndConvertToBase64(url);
//                        base64Images.add(base64);
//                    } catch (Exception e) {
//                        // Log or handle the error as needed
//                        System.err.println("Error downloading image from URL: " + url);
//                        e.printStackTrace();
//                    }
//                    System.out.println("Helloe Inside For");
//                }
//                product.setImages(base64Images);
//                System.out.println("Hello Inside");
//                productRepository.save(product); // Save updated product to the database
//            } catch (Exception e) {
//                // Log or handle the error as needed
//                System.err.println("Error processing product: " + product.getId());
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private String downloadAndConvertToBase64(String imageUrl) {
//        // Download image from URL
//        byte[] imageBytes = this.restTemplate().getForObject(imageUrl, byte[].class);
//
//        // Convert image to Base64
//        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//
//        return base64Image;
//    }
//
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

}


