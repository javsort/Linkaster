package com.linkaster.logicGateway.service;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

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

    private final RestTemplate restTemplate = new RestTemplate();

    public String authenticateAndGenerateToken(String username, String password) {
        log.info("Authenticating user: '" + username + "', calling userAuthenticator service");

        // Call User Authenticator Service for auth process
        String pathToAuth = "http://user-service:8081/api/auth/authenticate";

        // Create request body
        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("username", username, "password", password));
        
        // Send request to UserAuthenticatorService
        ResponseEntity<Map> response = restTemplate.exchange(pathToAuth, HttpMethod.POST, request, Map.class);

        log.info("Response from userAuthenticator: " + response.getBody());

        // If the response is successful, generate JWT
        if (response.getStatusCode().is2xxSuccessful()) {
            // Get AuthUser info from response
            String resp_role = (String) response.getBody().get("role");
            String resp_id = (String) response.getBody().get("id");
            String resp_username = (String) response.getBody().get("username");


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
                    .withSubject(resp_username)
                    .withClaim("role", resp_role)
                    .withClaim("username", resp_username)
                    .withClaim("id", resp_id)
                    .withIssuedAt(new Date(System.currentTimeMillis()))             // Current time
                    .withExpiresAt(new Date(System.currentTimeMillis() + 360000))   // to 1 hour
                    .sign(Algorithm.HMAC256(jwtSecret));                            // Sign the JWT with the secret

        } else {
            throw new RuntimeException("The user requested access with invalid credentials. Response body: '" + response.getBody() + "'");
        }
    }
}