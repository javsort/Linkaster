package com.linkaster.logicGateway.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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


/*
 * This class is a filter that intercepts all requests and checks for a valid JWT token.
 */
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    // Secret key for JWT verification -> defined at application.properties or docker-compose
    @Value("${jwt.secret}")
    private String secret;

    // Filter method
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Log the request
        log.info("Request: " + request.getRequestURI() + " Method: " + request.getMethod());
        log.info("Authorization Header: '" + request.getHeader("Authorization") + "'");


        // Public endpoints
        if (request.getRequestURI().equals("/api/login") || request.getRequestURI().equals("/api/status") || request.getRequestURI().equals("/auth/login")){
            log.info("Public endpoint accessed, no token required");
            filterChain.doFilter(request, response);
            return;
        }
        
        // For all authenticated accesses
        String authHeader = request.getHeader("Authorization");

        log.info("About to verify access token: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            log.info("Token found, verifying...");

            // Extract token from header
            String token = authHeader.substring(7);
            log.info("Token: " + token);

            // Verify token
            try {
                log.info("Verifying token...");
                JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
                DecodedJWT decodedToken = verifier.verify(token);

                log.info("Token verified: " + decodedToken);

                String id = decodedToken.getClaim("id").asString();
                String userEmail = decodedToken.getClaim("userEmail").asString();
                String role = "ROLE_" + decodedToken.getClaim("role").asString();

                // Add claims to the req attributes (opt, but would be [id, username, role])
                request.setAttribute("id", id);
                request.setAttribute("userEmail", userEmail);
                request.setAttribute("role", role);

                log.info("Request attributes set: \n id: " + id + "\n userEmail: " + userEmail + "\n role: " + role);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userEmail, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                log.info("UsernamePasswordAuthenticationToken set: " + authentication);

            } catch (JWTVerificationException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token at the request");
                return;
            }

        // If token is missing    
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing token");

            log.error("Missing token, unauthorized access for: '" + request + "' Headers:");
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                log.error(headerName + ": " + request.getHeader(headerName));
            }

            return;
        }
        
        // Continue with the request
        filterChain.doFilter(request, response);
    }
}
