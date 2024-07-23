package com.rdev.userregistery.controller;

import com.rdev.userregistery.dto.ReqResp;
import com.rdev.userregistery.entity.RegistryUsers;
import com.rdev.userregistery.service.RegistryManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistryManagementController {



        @Autowired
        private RegistryManagementService registryManagementService ;

    // Endpoint to register a new user
        @PostMapping("/auth/register")
        public ResponseEntity<ReqResp> register(@RequestBody ReqResp reg){
            return ResponseEntity.ok(registryManagementService.register(reg));
        }

    // Endpoint to log in a user
        @PostMapping("/auth/login")
        public ResponseEntity<ReqResp> login(@RequestBody ReqResp req){
            return ResponseEntity.ok(registryManagementService.login(req));
        }

    // Endpoint to refresh the JWT token
        @PostMapping("/auth/refresh")
        public ResponseEntity<ReqResp> refreshToken(@RequestBody ReqResp req){
            return ResponseEntity.ok(registryManagementService.refreshToken(req));
        }

    // Endpoint to get all users (admin only)
        @GetMapping("/admin/get-all-users")
        public ResponseEntity<ReqResp> getAllUsers(){
            return ResponseEntity.ok(registryManagementService.getAllUsers());

        }
        // Endpoint to get user details by ID (admin only)
        @GetMapping("/admin/get-users/{userId}")
        public ResponseEntity<ReqResp> getUSerByID(@PathVariable Integer userId){
            return ResponseEntity.ok(registryManagementService.getUserById(userId));

        }
    

    // Endpoint to get the authenticated user's profile
        @GetMapping("/adminuser/get-profile")
        public ResponseEntity<ReqResp> getMyProfile(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            ReqResp response = registryManagementService.getMyInfo(email);
            return  ResponseEntity.status(response.getStatusCode()).body(response);
        }

    // Endpoint to delete a user by ID (admin only)
        @DeleteMapping("/admin/delete/{userId}")
        public ResponseEntity<ReqResp> deleteUSer(@PathVariable Integer userId){
            return ResponseEntity.ok(registryManagementService.deleteUserById(userId));
        }


    }
