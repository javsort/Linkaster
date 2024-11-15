package com.linkaster.logicGateway.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        // Public endpoints
        if (request.getRequestURI().equals("/api/login") || request.getRequestURI().equals("/api/status") || request.getRequestURI().equals("/auth/login")){
            filterChain.doFilter(request, response);
            return;
        }
        
        // For all authenticated accesses
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            try {
                JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
                DecodedJWT decodedToken = verifier.verify(token);

                // add claims to the req attributes (opt, but would be [id, username, role])
                request.setAttribute("id", decodedToken.getClaim("id").asString());
                request.setAttribute("username", decodedToken.getClaim("username").asString());
                request.setAttribute("role", decodedToken.getClaim("role").asString());
            } catch (JWTVerificationException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing token");
            log.error("Missing token, unauthorized access for: '" + request.getRequestURI() + "'");
            return;
        }
        
        filterChain.doFilter(request, response);
    }
}
