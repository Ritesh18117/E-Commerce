package com.ecommerce.backend.REST;

import com.ecommerce.backend.entities.AuthRequest;
import com.ecommerce.backend.entities.User;
import com.ecommerce.backend.services.UserService;
import com.ecommerce.backend.dao.UserRepository;
import com.ecommerce.backend.dto.NewUserRequest;
import com.ecommerce.backend.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/test")
    public String test(){
        return "This is test for User request";
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@Valid @RequestBody NewUserRequest user) {
        return userService.addUser(user);
    }

    @PostMapping("/generateToken")
    public Map<String, String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        System.out.println(authRequest);
        User user = userRepository.findByUsername(authRequest.getUsername());
        if(user != null){
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            System.out.println(authentication.isAuthenticated());
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getUsername());
                Map<String, String> response = new HashMap<>();
                response.put("role",user.getRole());
                response.put("token", token);
                return response;
            } else {
                throw new UsernameNotFoundException("invalid user request !");
            }
        }else {
            throw new UsernameNotFoundException("invalid User!!");
        }
    }
    // It is here for testing
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll(){
        return userService.getAllUser();
    }

    @GetMapping("/getUsername")
    public ResponseEntity<Map<String,String>> getUsername(@RequestHeader(value = "Authorization") String authorizationHeader){
        return userService.getUsername(authorizationHeader);
    }
}
