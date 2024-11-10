package com.linkaster.userService.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {

    // Let Constructor be the pair of keys
    public KeyPair keyGenerator() throws Exception {
        // Generate key pair
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);

        return keyGenerator.generateKeyPair();
    }

    public String generateToken(String username, RSAPrivateKey userPrivateKey) {
        // Generate token
        Algorithm algorithm = Algorithm.RSA256(null, userPrivateKey);
        return JWT.create()
            .withIssuer("auth0")                                          // Issuer
            .withClaim("username", username)                                // Add username
            .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))       // Make it expire in 1 hour
            .sign(algorithm);

    }

    public DecodedJWT decodeToken(String token, RSAPublicKey userPublicKey) {
        try {
            Algorithm algorithm = Algorithm.RSA256(userPublicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
            return verifier.verify(token);

        } catch (JWTVerificationException e) {
            log.error("Error decoding token: " + e.getMessage());
            return null;
        }
    }
}
