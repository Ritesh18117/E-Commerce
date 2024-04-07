package com.ecommerce.backend.services;

import com.ecommerce.backend.dao.AdminRepository;
import com.ecommerce.backend.dao.SuperAdminRepository;
import com.ecommerce.backend.entities.Admin;
import com.ecommerce.backend.entities.Seller;
import com.ecommerce.backend.entities.SuperAdmin;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class SuperAdminService {

    @Autowired
    private SuperAdminRepository superAdminRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AdminRepository adminRepository;

    public String test(){
        return "This is test from Super Admin Service";
    }

    public ResponseEntity<SuperAdmin> addSuperAdmin(SuperAdmin superAdmin){
        try{
            superAdminRepository.save(superAdmin);
            System.out.println("Super Admin Saved");
            return ResponseEntity.of(Optional.of(superAdmin));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Admin>> getAllAdmin(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            SuperAdmin superAdmin = superAdminRepository.findByUserId(userId);
            System.out.println(superAdmin);
            if(Objects.equals(superAdmin,null)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
                List<Admin> admins = (List<Admin>) adminRepository.findAll();
                for(Admin admin : admins){
                    admin.getUser().setPassword(null);
                    admin.setVerifiedProduct(null);
                    admin.setVerifiedSeller(null);
                }
                return ResponseEntity.of(Optional.of(admins));
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<SuperAdmin> myProfile(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            SuperAdmin superAdmin = superAdminRepository.findByUserId(userId);
            if(Objects.equals(superAdmin,null)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                superAdmin.getUser().setPassword(null);
                return ResponseEntity.of(Optional.of(superAdmin));
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<SuperAdmin> updateSuperAdminProfile(@RequestBody SuperAdmin updateSuperAdmin, @RequestHeader(value = "Authorization") String authorizationHeader){
        try {
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            SuperAdmin existingSuperAdmin = superAdminRepository.findByUserId(userId);

            updateSuperAdmin.setUser(existingSuperAdmin.getUser());

            BeanUtils.copyProperties(updateSuperAdmin, existingSuperAdmin, "id");
            SuperAdmin savedSuperAdmin = superAdminRepository.save(existingSuperAdmin);

            savedSuperAdmin.getUser().setPassword(null);
            return ResponseEntity.of(Optional.of(savedSuperAdmin));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Admin> makeAdminActive(@RequestHeader(value = "Authorization") String authorizationHeader, Long adminId){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            SuperAdmin superAdmin = superAdminRepository.findByUserId(userId);
            if(Objects.equals(superAdmin,null)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else{
                Optional<Admin> admin = adminRepository.findById(adminId);
                admin.get().setStatus("Active");
                adminRepository.save(admin.get());
                admin.get().getUser().setPassword(null);
                admin.get().setVerifiedSeller(null);
                admin.get().setVerifiedProduct(null);
                return ResponseEntity.of(Optional.of(admin.get()));
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Admin> makeAdminInActive(@RequestHeader(value = "Authorization") String authorizationHeader, Long adminId){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            SuperAdmin superAdmin = superAdminRepository.findByUserId(userId);
            if(Objects.equals(superAdmin,null)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else{
                Optional<Admin> admin = adminRepository.findById(adminId);
                admin.get().setStatus("inActive");
                adminRepository.save(admin.get());
                admin.get().getUser().setPassword(null);
                admin.get().setVerifiedSeller(null);
                admin.get().setVerifiedProduct(null);
                return ResponseEntity.of(Optional.of(admin.get()));
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Admin>> getAllInActiveAdmin(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            SuperAdmin superAdmin = superAdminRepository.findByUserId(userId);
            if(Objects.equals(superAdmin,null)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else{
                List<Admin> admins = (List<Admin>) adminRepository.findAllByStatus("inActive");
                if(admins.isEmpty()){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
                else{
                    for(Admin admin : admins){
                        admin.getUser().setPassword(null);
                        admin.setVerifiedSeller(null);
                        admin.setVerifiedProduct(null);
                    }
                    return ResponseEntity.of(Optional.of(admins));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Admin>> getAllActiveAdmin(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            Long userId = jwtService.extractUserIdFromHeader(authorizationHeader);
            SuperAdmin superAdmin = superAdminRepository.findByUserId(userId);
            if(Objects.equals(superAdmin,null)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else{
                List<Admin> admins = (List<Admin>) adminRepository.findAllByStatus("Active");
                if(admins.isEmpty()){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
                else{
                    for(Admin admin : admins){
                        admin.getUser().setPassword(null);
                        admin.setVerifiedSeller(null);
                        admin.setVerifiedProduct(null);
                    }
                    return ResponseEntity.of(Optional.of(admins));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
