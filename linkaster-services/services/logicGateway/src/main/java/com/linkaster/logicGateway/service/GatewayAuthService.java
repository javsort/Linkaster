package com.linkaster.logicGateway.service;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.linkaster.logicGateway.dto.UserLogin;
import com.linkaster.logicGateway.dto.UserRegistration;

import lombok.extern.slf4j.Slf4j;

/*
 * This class is responsible for authenticating users and generating JWT tokens.
 * Handles all access to the rest of the services
 */

@Service
@Slf4j
public class GatewayAuthService {

    // Private key for JWT signing
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${address.user.url}")
    private String userServiceUrl;
    
    private final RestTemplate restTemplate = new RestTemplate();

    private final String log_header = "GatewayAuthService --- ";

    public String authenticateAndGenerateToken(UserLogin incRequest, String userType) {

        String userEmail = incRequest.getUserEmail();
        String password = incRequest.getPassword();

        // Call User Authenticator Service for auth process
        String pathToAuth = userServiceUrl + "/api/auth/"+ userType + "/login";
        
        log.info(log_header + log_header + "Authenticating user: '" + userEmail + "', calling userAuthenticator service to: " + pathToAuth);

        // Create UserLogin dto to send back
        UserLogin loginRequest = new UserLogin(userEmail, password);

        // Create HTTP Req
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserLogin> request = new HttpEntity<>(loginRequest, headers);
        
        try {
            log.info(log_header + log_header + "Making request to: " + pathToAuth);
            ResponseEntity<Map> response = restTemplate.exchange(
                pathToAuth, 
                HttpMethod.POST, 
                request, 
                Map.class);
            log.info(log_header + "Response from user-service: " + response.getBody());
            
            // If the response is successful, generate JWT
            if (response.getStatusCode().is2xxSuccessful()) {
                // Get AuthUser info from response
                String resp_role = (String) response.getBody().get("role");
                String resp_id = (String) response.getBody().get("id");
                String resp_userEmail = (String) response.getBody().get("userEmail");


                // Generate JWT with the data from the response
                /*
                * JWT Structure: Header: { 
                *     "alg": "HS256", 
                *     "typ": "JWT" 
                * } 
                * Payload: { 
                *     "iss": "auth0", 
                *     "sub": "username", 
                *     "role": "role", 
                *     "id": "id", 
                *     "username": "username", 
                *     "iat": current time, 
                *     "exp": current time + 1 hour 
                * }
                */
                return JWT.create()
                        .withIssuer("auth0")
                        .withSubject(resp_userEmail)
                        .withClaim("role", resp_role)
                        .withClaim("userEmail", resp_userEmail)
                        .withClaim("id", resp_id)
                        .withIssuedAt(new Date(System.currentTimeMillis()))             // Current time
                        .withExpiresAt(new Date(System.currentTimeMillis() + 360000))   // to 1 hour
                        .sign(Algorithm.HMAC256(jwtSecret));                            // Sign the JWT with the secret

            } else {
                throw new RuntimeException("The user requested access with invalid credentials. Response body: '" + response.getBody() + "'");
            }
        
        } catch (RuntimeException e) {
            System.err.println("Error during call to user-service: " + e.getMessage());
            e.printStackTrace(); // Add stack trace for detailed debugging
            throw e;
        }   
    }

    public String registerAndGenerateToken(UserRegistration regRequest, String userType) throws Exception {
        String userEmail = regRequest.getUserEmail();
        String password = regRequest.getPassword();

        // Validate required fields
        if (userEmail == null || userEmail.isEmpty()) {
            log.error(log_header + "User email is missing");
            throw new IllegalArgumentException("User email is required");
        }
        if (password == null || password.isEmpty()) {
            log.error(log_header + "Password is missing");
            throw new IllegalArgumentException("Password is required");
        }

        // Call User Authenticator Service for auth process
        String pathToRegister = userServiceUrl + "/api/auth/"+ userType + "/register";
        
        log.info(log_header + "Registering user: '" + userEmail + "', calling userAuthenticator service to: " + pathToRegister);

        // Create UserRegistration dto to send back
        UserRegistration toRegister = new UserRegistration(
            regRequest.getName(),
            regRequest.getSurname(),
            regRequest.getUserEmail(),
            regRequest.getPassword(),
            regRequest.getStudentId(),
            regRequest.getYear(),
            regRequest.getCourse(),
            regRequest.getSubject()
        );

        // Create HTTP Req
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRegistration> request = new HttpEntity<>(toRegister, headers);
        
        try {
            log.info(log_header + "Making request to: " + pathToRegister);
            ResponseEntity<Map> response = restTemplate.exchange(pathToRegister, HttpMethod.POST, request, Map.class);
            log.info(log_header + "Response from user-service: " + response.getBody());
            
            // If the response is successful, generate JWT
            if (response.getStatusCode().is2xxSuccessful()) {
                // Get AuthUser info from response
                String resp_role = (String) response.getBody().get("role");
                String resp_id = (String) response.getBody().get("id");
                String resp_userEmail = (String) response.getBody().get("userEmail");

                // Generate JWT with the data from the response
                /*
                * JWT Structure: Header: { 
                *     "alg": "HS256", 
                *     "typ": "JWT" 
                * } 
                * Payload: { 
                *     "iss": "auth0", 
                *     "sub": "username", 
                *     "role": "role", 
                *     "id": "id", 
                *     "username": "username", 
                *     "iat": current time, 
                *     "exp": current time + 1 hour 
                * }
                */
                return JWT.create()
                        .withIssuer("auth0")
                        .withSubject(resp_userEmail)
                        .withClaim("role", resp_role)
                        .withClaim("userEmail", resp_userEmail)
                        .withClaim("id", resp_id)
                        .withIssuedAt(new Date(System.currentTimeMillis()))             // Current time
                        .withExpiresAt(new Date(System.currentTimeMillis() + 360000))   // to 1 hour
                        .sign(Algorithm.HMAC256(jwtSecret));  
            }
            else {
                throw new RuntimeException("The user requested access with invalid credentials. Response body: '" + response.getBody() + "'");
            }
        } catch (RuntimeException e) {
            System.err.println("Error during call to user-service: " + e.getMessage());
            e.printStackTrace(); // Add stack trace for detailed debugging
            throw e;
        }
    }
}