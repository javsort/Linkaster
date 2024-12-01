package com.linkaster.logicGateway.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
@Slf4j
public class GatewayController implements APIGatewayController {

    // INIT: Endpoints
    private RestTemplate restTemplate;

    @Value("${address.user.url}")
    private String userServiceUrl;

    @Value("${address.module.url}")
    private String moduleServiceUrl;

    @Value("${address.message.url}")
    private String messageServiceUrl;

    @Value("${address.feedback.url}")
    private String feedbackServiceUrl;

    private final String log_header = "GatewayController --- ";

    // END: Endpoints

    // Service for authenticating users and generating JWT tokens
    private final GatewayAuthService gatewayAuthService;

    @Autowired
    public GatewayController(GatewayAuthService gatewayAuthService, RestTemplate restTemplate) {
        this.gatewayAuthService = gatewayAuthService;
        this.restTemplate = restTemplate;
    }

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

        log.info(log_header + "Received login request for user: " + userEmail + " with password: " + password);
        try {
            log.info(log_header + "Authenticating user: " + userEmail + ", calling GatewayAuthService");

            String token = gatewayAuthService.authenticateAndGenerateToken(loginRequest, userType);

            log.info(log_header + "User: '" + userEmail + "' authenticated, returning token");
            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            log.error(log_header + "Error while authenticating user: '" + userEmail + "'. Exception string: " + e);
            return ResponseEntity.status(401).body("Invalid credentials");
            
        }
    }

    // Register User
    @Override
    public ResponseEntity<?> register(@RequestBody UserRegistration regRequest, @PathVariable("user_type") String userType) {
        String userEmail = regRequest.getUserEmail();
        String password = regRequest.getPassword();

        log.info(log_header + "Received registration request for user: " + userEmail + " with password: " + password);
        try {
            log.info(log_header + "Registering user: " + userEmail + ", calling GatewayAuthService");

            String token = gatewayAuthService.registerAndGenerateToken(regRequest, userType);

            log.info(log_header + "User: '" + userEmail + "' registered, returning token");
            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            log.error(log_header + "Error while registering user: '" + userEmail + "'. Exception string: " + e);
            return ResponseEntity.status(401).body("Invalid credentials");
            
        }
    }


    // Forward requests to messageService
    // All paths going into /message/** will be forwarded to the messageService
    @Override
    public ResponseEntity<?> forwardToMessageService(HttpServletRequest request, @RequestBody(required=false) String requestBody) {
        log.info(log_header + "Forwarding request to MessagingService: " + request);

        /*
         * SPECIAL ENDPOINT -> /message/establishSocket -> Returns a websocket, diff from regular answer
        */
        if(request.getRequestURI().equals("/api/message/establishSocket")) {
            log.info(log_header + "Establishing websocket connection through messaging service... ");
        }

        String targetUrl = messageServiceUrl + request.getRequestURI();
        return travelGate(request, targetUrl, requestBody, "User Handler Service");
    }

    // Forward requests to userService
    // All paths going into /user/** will be forwarded to the moduleService
    @Override
    public ResponseEntity<?> forwardToUserService(HttpServletRequest request, @RequestBody(required=false) String requestBody) {

        log.info(log_header + "Forwarding request to userService: " + request.getRequestURI());
        String targetUrl = userServiceUrl + request.getRequestURI();

        
        return travelGate(request, targetUrl, requestBody, "User Handler Service");
    }

    // Forward requests to moduleService
    // All paths going into /module/** will be forwarded to the moduleService
    @Override
    public ResponseEntity<?> forwardToModuleService(HttpServletRequest request, @RequestBody(required=false) String requestBody) {

        log.info(log_header + "Forwarding request to moduleService: " + request.getRequestURI());
        String targetUrl = moduleServiceUrl + request.getRequestURI();

        return travelGate(request, targetUrl, requestBody, "Module Manager Service");
    }

    // Forward requests to feedbackService
    // All paths going into /feedback/** will be forwarded to the feedbackService
    @Override
    public ResponseEntity<?> forwardToFeedbackService(HttpServletRequest request, @RequestBody(required=false) String requestBody) {

        log.info(log_header + "Forwarding request to feedbackService: " + request.getRequestURI());
        String targetUrl = feedbackServiceUrl + request.getRequestURI();

        return travelGate(request, targetUrl, requestBody, "Feedback Service");
    }

    public ResponseEntity<?> travelGate(HttpServletRequest request, String targetUrl, String requestBody, String destinationService) {
        log.info(log_header + "Request before processing data: " + request);

        HttpHeaders headers = getHeaders(request);
        HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);

        log.info(log_header + "Request headers: " + headers);
        log.info(log_header + "Request body: " + requestBody);

        log.info(log_header + "Forwarding request to '" + destinationService + "': " + targetUrl);
        
        // Forward the request
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.valueOf(request.getMethod()),
                entity,
                String.class
            );
    
            log.info("Response from '"+ destinationService +"'' : Body= '" + response.getBody() +  "'', Status= " + response.getStatusCode());
    
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            
        } catch (Exception e) {
            log.error("Error while forwarding request to '" + destinationService + "'", e);
            return ResponseEntity.status(500).body("An error occurred while forwarding the request.");
        }
    }

    // Helper function to get headers from the request
    public HttpHeaders getHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames())
            .forEach(headerName -> headers.add(headerName, request.getHeader(headerName)));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
