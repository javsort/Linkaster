package com.linkaster.moduleManager.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class JwtReqFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Log the request
        log.info("Request: " + request.getRequestURI() + " Method: " + request.getMethod());
        log.info("Request received with the following headers: '" + request.getHeaderNames() + "'");
        log.info("Authorization Header: '" + request.getHeader("Authorization") + "'");

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Validate token
            if (!jwtTokenProvider.validateToken(token)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                log.error("Unable to validate token at userService - token is invalid");
                return;
            }

            // Extract claims and log the received values
            String id = jwtTokenProvider.getClaims(token, "id");
            String userEmail = jwtTokenProvider.getClaims(token, "userEmail");
            String role = "ROLE_" + jwtTokenProvider.getClaims(token, "role");

            // Add claims to the req attributes (opt, but would be [id, username, role])
            request.setAttribute("id", id);
            request.setAttribute("userEmail", userEmail);
            request.setAttribute("role", role);

            log.info("Found the following tokens: \nid: " + id + "\nuserEmail: " + userEmail + "\nrole: " + role);
            
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing header for authorization");
            
            log.error("Unable to validate token at userService - missing auth header");
            return;
        }

        // Keep going with request
        filterChain.doFilter(request, response);
    }
}
