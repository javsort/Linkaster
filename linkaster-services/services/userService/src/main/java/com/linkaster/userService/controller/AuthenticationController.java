package com.linkaster.userService.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.dto.AuthUser;
import com.linkaster.userService.dto.UserLogin;
import com.linkaster.userService.dto.UserRegistration;
import com.linkaster.userService.service.UserAuthenticatorService;

import lombok.extern.slf4j.Slf4j;

/*
 * This class is the controller for the UserAuthenticator service.
 * It handles all incoming requests to the service and authenticates users.
 * Called directly by logicGateway to verify user credentials
 */
@Slf4j
@RestController
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
    public ResponseEntity<?> authenticate(UserLogin loginRequest, String user_type) {
        String userEmail = loginRequest.getUserEmail();
        String password = loginRequest.getPassword();

        log.info(log_header + "Received ping to authenticate the " + user_type + ": " + userEmail);

        // If user is admin or test user, skip authentication
        if(userEmail.equals("student@example.com") 
        || userEmail.equals("admin@example.com") 
        || userEmail.equals("teacher@example.com")
        || userEmail.equals("adminteacher@example.com")
        || userEmail.equals("user1@example.com")
        || userEmail.equals("user2@example.com")
        || userEmail.equals("user3@example.com")
        || userEmail.equals("user4@example.com")
        || userEmail.equals("user5@example.com")
        || userEmail.equals("user6@example.com")
        ){
            Map<String, String> response = new HashMap<>();

            AuthUser adminUser = userAuthenticatorService.getAuthenticatedUser(userEmail);

            response.put("message", "Admin authenticated");
            response.put("id", adminUser.getId().toString());
            response.put("userEmail", adminUser.getUserEmail());
            response.put("role", adminUser.getRole());

            log.info(log_header + "Admin: '" + userEmail + "' authenticated");
            return ResponseEntity.ok(response);
        }

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

    // Pinged by the Gateway to register a user
    // Returns a response entity with the user's id, userEmail, and role
    @Override
    public ResponseEntity<?> register(UserRegistration regRequest, String user_type) {
        String userEmail = regRequest.getUserEmail();

        log.info(log_header + "Received ping to register the " + user_type + ": " + userEmail);

        // Register user
        if(userAuthenticatorService.registerUser(regRequest, user_type)){
            Map<String, String> response = new HashMap<>();

            AuthUser registeredUser = userAuthenticatorService.getAuthenticatedUser(userEmail);

            response.put("message", "User registered");
            response.put("id", registeredUser.getId().toString());
            response.put("userEmail", registeredUser.getUserEmail());
            response.put("role", registeredUser.getRole());

            log.info(log_header + "User: '" + userEmail + "' registered");
            return ResponseEntity.ok(response);
        }

        // if registration fails, return bad request
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
    }
}
