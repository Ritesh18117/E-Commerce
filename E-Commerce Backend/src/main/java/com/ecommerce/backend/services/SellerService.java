package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.AdminRepository;
import com.ecommerce.backend.dao.SellerRepository;
import com.ecommerce.backend.dao.UserRepository;
import com.ecommerce.backend.entities.Admin;
import com.ecommerce.backend.entities.Seller;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDate;
import java.util.*;

@Component
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AdminRepository adminRepository;

    // It is here for testing else move to AdminService
    public ResponseEntity<List<Seller>> getAllSeller(){
        try{
            List<Seller> list = (List<Seller>) sellerRepository.findAll();
            if(list.size() <= 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(list));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public ResponseEntity<Seller> newSeller(Seller seller){
        try{
            sellerRepository.save(seller);
            return ResponseEntity.of(Optional.of(seller));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Optional<Seller>> getSellerById(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Seller seller =sellerRepository.findByUserId(userId);
            seller.getUser().setPassword(null);
            seller.setVerifiedBy(null);
            return ResponseEntity.of(Optional.of(Optional.of(seller)));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Seller> updateSellerProfile(@RequestBody Seller updatedSeller,@RequestHeader(value = "Authorization") String authorizationHeader){
        try {
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Seller existingSeller = sellerRepository.findByUserId(userId);

            String existingApprovalStatus = existingSeller.getApprovalStatus();
            updatedSeller.setApprovalStatus(existingApprovalStatus);

            updatedSeller.setUser(existingSeller.getUser());

            BeanUtils.copyProperties(updatedSeller, existingSeller, "id");
            Seller savedSeller = sellerRepository.save(existingSeller);

            savedSeller.getUser().setPassword(null);
            return ResponseEntity.of(Optional.of(savedSeller));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Seller>> notApprovedSellers(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);

            if(admin != null && Objects.equals(admin.getStatus(), "Active")){
                List<Seller> sellers = sellerRepository.findAllByApprovalStatus("false");
                for(Seller seller : sellers){
                    seller.setUser(null);
                }
                return ResponseEntity.of(Optional.of(sellers));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Seller>> approvedSellers(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);

            if(admin != null){
                List<Seller> sellers = sellerRepository.findAllByApprovalStatus("true");
                for (Seller seller : sellers){
                    seller.setUser(null);
                }
                return ResponseEntity.of(Optional.of(sellers));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Seller>> rejectedSeller(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);

            if(admin != null){
                List<Seller> sellers = sellerRepository.findAllByApprovalStatus("rejected");
                for(Seller seller : sellers){
                    seller.setUser(null);
                }
                return ResponseEntity.of(Optional.of(sellers));
            } else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String,String>> approveSeller(@RequestHeader(value = "Authorization") String authorizationHeader,Long sellerId){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            if(admin != null && Objects.equals(admin.getStatus(), "Active")){
                Optional<Seller> seller = sellerRepository.findById(sellerId);
                seller.get().setApprovalStatus("true");
                seller.get().setStatusChangeDate(LocalDate.now());
                seller.get().setVerifiedBy(admin);
                admin.addSeller(seller.get().getId());
                sellerRepository.save(seller.get());
                Map<String, String> op = new HashMap<>();
                op.put("Message", "Successfully Approved Seller!!!");
                return ResponseEntity.of(Optional.of(op));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String,String>> rejectSeller(@RequestHeader(value = "Authorization") String authorizationHeader,Long sellerId){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            if(admin != null && Objects.equals(admin.getStatus(), "Active")){
                Optional<Seller> seller = sellerRepository.findById(sellerId);
                seller.get().setApprovalStatus("rejected");
                seller.get().setStatusChangeDate(LocalDate.now());
                seller.get().setVerifiedBy(admin);
                admin.addSeller(seller.get().getId());
                sellerRepository.save(seller.get());
                Map<String, String> op = new HashMap<>();
                op.put("Message", "Seller Rejected!!!");
                return ResponseEntity.of(Optional.of(op));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Seller>> getMySellerVerifyList(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            List<Seller> verifiedSellers = new ArrayList<>();
            for(Long sellerId : admin.getVerifiedSeller()){
                Optional<Seller> seller = sellerRepository.findById(sellerId);
                seller.get().setUser(null);
                seller.get().setVerifiedBy(null);
                verifiedSellers.add(seller.get());
            }
            return ResponseEntity.of(Optional.of(verifiedSellers));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String,String>> revokeSellerVerify(@RequestHeader(value = "Authorization") String authorizationHeader, Long sellerId){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            Optional<Seller> seller = sellerRepository.findById(sellerId);
            if(seller.isPresent()){
                seller.get().setVerifiedBy(null);
                seller.get().setApprovalStatus("false");
                seller.get().setStatusChangeDate(LocalDate.now());
                admin.removeSeller(sellerId);
                sellerRepository.save(seller.get());
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

    public ResponseEntity<Seller> getSellerByGST(@RequestHeader(value = "Authorization") String authorizationHeader,String gst){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            if(admin != null){
                Seller seller = sellerRepository.findByGstNumber(gst);
                if(seller != null){
                    seller.setVerifiedBy(null);
                    seller.setUser(null);
                    return ResponseEntity.of(Optional.of(seller));
                } else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Seller> getSellerByLicenceNumber(@RequestHeader(value = "Authorization") String authorizationHeader,String licenceNumber){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            if(admin != null){
                Seller seller = sellerRepository.findByLicenceNumber(licenceNumber);
                if(seller != null){
                    seller.setUser(null);
                    seller.setVerifiedBy(null);
                    return ResponseEntity.of(Optional.of(seller));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Seller> getSellerBySellerId(@RequestHeader(value = "Authorization") String authorizationHeader,Long id){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            if(admin != null){
                Optional<Seller> seller = sellerRepository.findById(id);
                if(seller.isPresent()){
                    seller.get().setUser(null);
                    seller.get().setVerifiedBy(null);
                    return ResponseEntity.of(Optional.of(seller.get()));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Seller> getSellerByCompanyName(@RequestHeader(value = "Authorization") String authorizationHeader,String name){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            Admin admin = adminRepository.findByUserId(userId);
            if(admin != null){
                Seller seller = sellerRepository.findByCompanyName(name);
                if(seller != null){
                    seller.setUser(null);
                    seller.setVerifiedBy(null);
                    return ResponseEntity.of(Optional.of(seller));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}