package com.linkaster.userService.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.dto.AuthUser;
import com.linkaster.userService.dto.UserLogin;
import com.linkaster.userService.service.UserAuthenticatorService;

import lombok.extern.slf4j.Slf4j;

/*
 * This class is the controller for the UserAuthenticator service.
 * It handles all incoming requests to the service and authenticates users.
 * Called directly by logicGateway to verify user credentials
 */
@RestController
@Slf4j
@RequestMapping("/api/auth")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AuthenticationController implements APIAuthenticationController {

    // Service for authenticating users
    private final UserAuthenticatorService userAuthenticatorService;

    
    private final String log_header = "AuthenticationController: ";

    // Constructor
    @Autowired
    public AuthenticationController(UserAuthenticatorService userAuthenticatorService){
        this.userAuthenticatorService = userAuthenticatorService;
    }

    // Pinged by the Gateway to authenticate a user
    // Returns a response entity with the user's id, userEmail, and role
    @Override
    public ResponseEntity<?> authenticate(UserLogin loginRequest){

        String userEmail = loginRequest.getUserEmail();
        String password = loginRequest.getPassword();

        log.info(log_header + "Received ping to authenticate user: " + userEmail);

        // Authenticate user
        if(userAuthenticatorService.authenticateUser(userEmail, password)){
            Map<String, String> response = new HashMap<>();

            AuthUser authenticatedUser = userAuthenticatorService.getAuthenticatedUser(userEmail);

            response.put("message", "User authenticated");
            response.put("id", authenticatedUser.getId().toString());
            response.put("userEmail", authenticatedUser.getUserEmail());
            response.put("role", authenticatedUser.getRole());

            log.info(log_header + "User: '" + userEmail + "' authenticated");
            return ResponseEntity.ok(response);
        }

        // if authentication fails, return unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
