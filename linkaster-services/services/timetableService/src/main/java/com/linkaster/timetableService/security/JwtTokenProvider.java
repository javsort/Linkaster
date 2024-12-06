package com.linkaster.timetableService.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import lombok.extern.slf4j.Slf4j;

/*
 *  Title: JwtTokenProvider.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Component
@Slf4j
public class JwtTokenProvider {

    // Secret key for JWT verification -> defined at application.yml or docker-compose
    @Value("${jwt.secret}")
    private String secret;

    private final String log_header = "JwtTokenProvider --- ";

    public boolean validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            
            return true;    
        } catch (JWTVerificationException e) {

            log.error(log_header + "Error validating token: " + e.getMessage());
            return false;
        }
    }

    public DecodedJWT decodeToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            
            return jwt;    
        } catch (JWTVerificationException e) {

            log.error(log_header + "Error decoding token: " + e.getMessage());
            return null;
        }
    }

    public String getClaims(String token, String claim){
        DecodedJWT jwt = decodeToken(token);
        if(jwt != null){
            return jwt.getClaim(claim).asString();
        }
        return null;
    }

}
