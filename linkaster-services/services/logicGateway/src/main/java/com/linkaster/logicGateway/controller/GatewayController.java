package com.linkaster.logicGateway.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> forwardToMessageService(HttpServletRequest request) {
        /*
         * SPECIAL ENDPOINT -> /message/establishSocket -> Returns a websocket, diff from regular answer
         */

        String uri = request.getRequestURI();

        // Essentially same behavior, will reduce once functionality is fully implemented
        if(uri.equals("/api/message/establishSocket")){
            log.info(log_header + "Establishing websocket connection through messaging service... ");
            String targetUrl = messageServiceUrl + request.getRequestURI();
            
            // Cycle through headers and add them to the new request
            HttpHeaders headers = new HttpHeaders();
            Collections.list(request.getHeaderNames())
                .forEach(headerName -> headers.add(headerName, request.getHeader(headerName)));
                headers.setContentType(MediaType.APPLICATION_JSON);

            // Get the request body
            String requestBody = null;
            try {
                requestBody = request.getReader().lines()
                    .reduce("", (accumulator, actual) -> accumulator + actual);
            } catch (IOException e) {
                log.error(log_header + "Error reading request body", e);
            }

            HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);
            log.info(log_header + "Request headers: " + headers);
            log.info(log_header + "Request body: " + requestBody);
            

            // Forward the request
            ResponseEntity<?> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.valueOf(request.getMethod()),
                entity,
                String.class
            );

            return new ResponseEntity<>(response.getBody(), response.getStatusCode());

        } else {
            log.info(log_header + "Forwarding request to messageService: " + request.getRequestURI());
            String targetUrl = messageServiceUrl + request.getRequestURI();
            
            HttpHeaders headers = new HttpHeaders();
            Collections.list(request.getHeaderNames())
                .forEach(headerName -> headers.add(headerName, request.getHeader(headerName)));
                headers.setContentType(MediaType.APPLICATION_JSON);

            // Get the request body
            String requestBody = null;
            try {
                requestBody = request.getReader().lines()
                    .reduce("", (accumulator, actual) -> accumulator + actual);
            } catch (IOException e) {
                log.error(log_header + "Error reading request body", e);
            }

            HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);
            log.info(log_header + "Request headers: " + headers);
            log.info(log_header + "Request body: " + requestBody);

            // Forward the request
            ResponseEntity<Map> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.valueOf(request.getMethod()),
                entity,
                Map.class
            );

            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        }
    }

    // Forward requests to userService
    // All paths going into /user/** will be forwarded to the moduleService
    @Override
    public ResponseEntity<?> forwardToUserService(HttpServletRequest request) {

        log.info(log_header + "Forwarding request to userService: " + request.getRequestURI());
        String targetUrl = userServiceUrl + request.getRequestURI();
        
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames())
            .forEach(headerName -> headers.add(headerName, request.getHeader(headerName)));
            headers.setContentType(MediaType.APPLICATION_JSON);

        // Get the request body
        String requestBody = null;
        try {
            requestBody = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);
        } catch (IOException e) {
            log.error(log_header + "Error reading request body", e);
        }

        HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);
        log.info(log_header + "Request headers: " + headers);
        log.info(log_header + "Request body: " + requestBody);

        // Forward the request
        ResponseEntity<Map> response = restTemplate.exchange(
            targetUrl,
            HttpMethod.valueOf(request.getMethod()),
            entity,
            Map.class
        );

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    // Forward requests to moduleService
    // All paths going into /module/** will be forwarded to the moduleService
    @Override
    public ResponseEntity<?> forwardToModuleService(HttpServletRequest request, @RequestBody String requestBody) {

        log.info(log_header + "Forwarding request to moduleService: " + request.getRequestURI());
        String targetUrl = moduleServiceUrl + request.getRequestURI();

        log.info(log_header + "Request before processing data: " + request);
        
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames())
            .forEach(headerName -> headers.add(headerName, request.getHeader(headerName)));
            headers.setContentType(MediaType.APPLICATION_JSON);

        log.info(log_header + "Request headers: " + headers);
        log.info(log_header + "Request body: " + requestBody);
        
        log.info(log_header + "Request after processing data: " + request);
        HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);

        log.info(log_header + "Forwarding request to moduleService: " + targetUrl);
            
        try {
            log.info(log_header + "Forwarding to module Service. The request has these qualities: \nUrl:" + " " + targetUrl + "\nMethod: " + request.getMethod() + "\nHeaders: " + headers + "\nBody: " + requestBody);
            // Forward the request to the Module Manager service
            ResponseEntity<String> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.valueOf(request.getMethod()),
                entity,
                String.class
            );
    
            // Log the response for debugging
            log.info("Response from Module Manager: Body= '" + response.getBody() +  "'', Status= " + response.getStatusCode());
    
            // Return the response back to the client
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    
        } catch (Exception e) {
            log.error("Error while forwarding request toW Module Manager", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while forwarding the request.");
        }
    }

}
