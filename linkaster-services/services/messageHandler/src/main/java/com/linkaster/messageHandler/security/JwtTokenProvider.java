package com.linkaster.messageHandler.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import lombok.extern.slf4j.Slf4j;


/*
 * This class is a filter that intercepts all requests and checks for a valid JWT token.
 */
@Component
@Slf4j
public class JwtTokenProvider {

    // Secret key for JWT verification -> defined at application.yml or docker-compose
    @Value("${jwt.secret}")
    private String secret;

    public boolean validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            
            return true;    
        } catch (JWTVerificationException e) {

            log.error("Error validating token: " + e.getMessage());
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

            log.error("Error decoding token: " + e.getMessage());
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
