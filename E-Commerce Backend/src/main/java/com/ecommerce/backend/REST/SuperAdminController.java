package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.Admin;
import com.ecommerce.backend.entities.Seller;
import com.ecommerce.backend.entities.SuperAdmin;
import com.ecommerce.backend.services.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superAdmin")
public class SuperAdminController {
    @Autowired
    private SuperAdminService superAdminService;
    @GetMapping("/test")
    public String test(){
        return superAdminService.test();
    }
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/getAllAdmin")
    public ResponseEntity<List<Admin>> getAllAdmin(@RequestHeader(value = "Authorization") String authorizationHeader){
        return superAdminService.getAllAdmin(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/myProfile")
    public ResponseEntity<SuperAdmin> myProfile(@RequestHeader(value = "Authorization") String authorizationHeader){
        return superAdminService.myProfile(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PatchMapping("/updateProfile")
    public ResponseEntity<SuperAdmin> updateProfile(@RequestBody SuperAdmin updatedSuperAdmin,@RequestHeader(value = "Authorization") String authorizationHeader){
        return superAdminService.updateSuperAdminProfile(updatedSuperAdmin,authorizationHeader);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/getInActiveAdmin")
    public ResponseEntity<List<Admin>> getAllInActiveAdmin(@RequestHeader(value = "Authorization") String authorizationHeader){
        return superAdminService.getAllInActiveAdmin(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/getActiveAdmin")
    public ResponseEntity<List<Admin>> getAllActiveAdmin(@RequestHeader(value = "Authorization") String authorizationHeader){
        return superAdminService.getAllActiveAdmin(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/makeAdminActive/{adminId}")
    public ResponseEntity<Admin> makeAdminActive(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable Long adminId){
        return superAdminService.makeAdminActive(authorizationHeader,adminId);
    }
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/makeAdminInActive/{adminId}")
    public ResponseEntity<Admin> makeAdminInActive(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable Long adminId){
        return superAdminService.makeAdminInActive(authorizationHeader,adminId);
    }
}
