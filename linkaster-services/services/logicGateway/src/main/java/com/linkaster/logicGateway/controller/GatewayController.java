package com.linkaster.logicGateway.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.linkaster.logicGateway.dto.UserLogin;
import com.linkaster.logicGateway.dto.UserRegistration;
import com.linkaster.logicGateway.service.GatewayAuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


/*
 * This class is the controller for the logicGateway service.
 * It handles all incoming requests to the gateway.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class GatewayController implements APIGatewayController {

    // INIT: Endpoints
    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.service.url:http://localhost:8081}")
    private String userServiceUrl;

    // END: Endpoints

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
    public ResponseEntity<?> login(@RequestBody UserLogin loginRequest, @PathVariable("user_type") String userType) {  

        String userEmail = loginRequest.getUserEmail();
        String password = loginRequest.getPassword();

        log.info("Received login request for user: " + userEmail + " with password: " + password);
        try {
            log.info("Authenticating user: " + userEmail + ", calling GatewayAuthService");

            String token = gatewayAuthService.authenticateAndGenerateToken(loginRequest, userType);

            log.info("User: '" + userEmail + "' authenticated, returning token");
            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            log.error("Error while authenticating user: '" + userEmail + "'. Exception string: " + e);
            return ResponseEntity.status(401).body("Invalid credentials");
            
        }
    }

    // Register User
    @Override
    public ResponseEntity<?> register(@RequestBody UserRegistration regRequest, @PathVariable("user_type") String userType) {
        String userEmail = regRequest.getUserEmail();
        String password = regRequest.getPassword();

        log.info("Received registration request for user: " + userEmail + " with password: " + password);
        try {
            log.info("Registering user: " + userEmail + ", calling GatewayAuthService");

            String token = gatewayAuthService.registerAndGenerateToken(regRequest, userType);

            log.info("User: '" + userEmail + "' registered, returning token");
            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            log.error("Error while registering user: '" + userEmail + "'. Exception string: " + e);
            return ResponseEntity.status(401).body("Invalid credentials");
            
        }
    }

    @GetMapping("/user/**")
    public ResponseEntity<?> forwardToUserService(HttpServletRequest request) {

        log.info("Forwarding request to userService: " + request.getRequestURI());
        String targetUrl = userServiceUrl + request.getRequestURI();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", request.getHeader("Authorization"));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        // Forward the request
        ResponseEntity<String> response = restTemplate.exchange(
            targetUrl,
            HttpMethod.valueOf(request.getMethod()),
            entity,
            String.class
        );

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

}
