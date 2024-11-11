package com.linkaster.logicGateway.service;

import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.sql.Date;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.Value;

@Service
public class GatewayAuthService {

    private final RSAPrivateKey privateKey;


    public GatewayAuthService(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    private final RestTemplate restTemplate = new RestTemplate();

    public String authenticateAndGenerateToken(String username, String password) {

        // Call User Authenticator Service for auth process
        String pathToAuth = "/api/auth/authenticate";

        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("username", username, "password", password));
        
        ResponseEntity<Map> response = restTemplate.exchange(pathToAuth, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // Get Role from response
            String role = (String) response.getBody().get("role");

            // Generate JWT with the role
            return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(username)
                    .withClaim("role", role)
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 360000)) // 1 hour
                    .sign(Algorithm.RSA256(null, privateKey));    // Idk what this for, will check

        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}