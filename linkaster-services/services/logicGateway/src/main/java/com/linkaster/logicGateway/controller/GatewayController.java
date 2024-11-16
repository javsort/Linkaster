package com.linkaster.logicGateway.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.logicGateway.service.GatewayAuthService;

import lombok.extern.slf4j.Slf4j;


/*
 * This class is the controller for the logicGateway service.
 * It handles all incoming requests to the gateway.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class GatewayController implements APIGatewayController {

    // Service for authenticating users and generating JWT tokens
    @Autowired
    private GatewayAuthService gatewayAuthService;

    @Override
    public String home(){
        return "Logic gateway Home";
    }

    @Override
    public String status(){
        return "All good";
    }

    // Login endpoint
    @Override
    public ResponseEntity<?> login(@RequestParam String userEmail, @RequestParam String password) {  

        log.info("Received login request for user: " + userEmail + " with password: " + password);
        try {
            log.info("Authenticating user: " + userEmail + ", calling GatewayAuthService");

            String token = gatewayAuthService.authenticateAndGenerateToken(userEmail, password);

            log.info("User: '" + userEmail + "' authenticated, returning token");
            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            log.error("Error while authenticating user: '" + userEmail + "'. Exception string: " + e);
            return ResponseEntity.status(401).body("Invalid credentials");
            
        }
    }


}
