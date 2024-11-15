package com.linkaster.userService.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.dto.AuthUser;
import com.linkaster.userService.service.UserAuthenticatorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthenticationController implements APIAuthenticationController {


    private final UserAuthenticatorService userAuthenticatorService;

    @Autowired
    public AuthenticationController(UserAuthenticatorService userAuthenticatorService){
        this.userAuthenticatorService = userAuthenticatorService;
    }

    // Pinged by the Gateway to authenticate a user
    @Override
    public ResponseEntity<?> authenticate(String username, String password){
        log.info("Received ping to authenticate user: " + username);

        if(userAuthenticatorService.authenticateUser(username, password)){
            Map<String, String> response = new HashMap<>();

            AuthUser authenticatedUser = userAuthenticatorService.getAuthenticatedUser(username);

            response.put("message", "User authenticated");
            response.put("id", authenticatedUser.getId().toString());
            response.put("username", authenticatedUser.getUsername());
            response.put("role", authenticatedUser.getRole());
            return ResponseEntity.ok(response);
        }



        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
