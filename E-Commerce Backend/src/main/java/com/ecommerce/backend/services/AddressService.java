package com.ecommerce.backend.services;

import com.ecommerce.backend.entities.Customer;
import com.ecommerce.backend.dao.AddressRepository;
import com.ecommerce.backend.dao.CustomerRepository;
import com.ecommerce.backend.dao.UserRepository;
import com.ecommerce.backend.entities.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class    AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JwtService jwtService;

    //For Testing it's here afterward move to AdminService
    public ResponseEntity<List<Address>> getAllAddress(){
        try{
            List<Address> addresses = (List<Address>) addressRepository.findAll();
            if(addresses.size() <= 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(addresses));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<List<Address>> myAddresses(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);
            List<Address> addresses = addressRepository.getAddressesByCustomerId(customer.getId());
            for(Address a : addresses){
                a.setCustomer(null);
            }
            return ResponseEntity.of(Optional.of(addresses));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Address> addAddress(@RequestBody Address address,@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            System.out.println(address.getName());
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            if( userId != null && Objects.equals(userRepository.findByUsername(username).getRole(), "ROLE_CUSTOMER")){
                Customer customer = customerRepository.findByUserId(userId);
                System.out.println(address.getCity());
                System.out.println(address.getCustomer());
                address.setCustomer(customer);
                System.out.println(address.getCustomer());
            }
            addressRepository.save(address);
            return ResponseEntity.of(Optional.of(address));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<String> deleteAddress(long id,@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            if( userId != null && Objects.equals(userRepository.findByUsername(username).getRole(), "ROLE_CUSTOMER")){
                Customer customer = customerRepository.findByUserId(userId);
                Optional<Address> address = addressRepository.findById(id);
                if(customer.getId() == address.get().getCustomer().getId()){
                    addressRepository.deleteById(id);
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
    private String extractTokenFromHeader(String authorizationHeader) {
        // Check if the Authorization header is not null and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token part by removing "Bearer " prefix
            return authorizationHeader.substring(7); // "Bearer ".length() == 7
        }
        return null; // Return null or handle accordingly if token extraction fails
    }
}
