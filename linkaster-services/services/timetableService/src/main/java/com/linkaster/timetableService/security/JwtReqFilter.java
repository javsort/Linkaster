package com.linkaster.timetableService.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


/*
 *  Title: JwtReqFilter.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Component
@Slf4j
public class JwtReqFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final String log_header = "JwtReqFilter --- ";

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // Log the request
        ContentCachingRequestWrapper requestWrap = new ContentCachingRequestWrapper(request);

        log.info(log_header + "Request: " + requestWrap.getRequestURI() + " Method: " + requestWrap.getMethod());
        log.info(log_header + "Request received with the following headers: '" + requestWrap.getHeaderNames() + "'");
        log.info(log_header + "Authorization Header: '" + requestWrap.getHeader("Authorization") + "'");

        String authHeader = request.getHeader("Authorization");

        // Allow access to timetable service after user registration
        if(requestWrap.getRequestURI().matches("/api/timetable/(create|delete)/\\d+")){
            
            log.info(log_header + "Request is for creating or deleting a timetable, allowing access...");
            
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Validate token
            if (!jwtTokenProvider.validateToken(token)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                log.error(log_header + "Unable to validate token - it's invalid");
                return;
            }

            // Extract claims and log the received values
            String id = jwtTokenProvider.getClaims(token, "id");
            String userEmail = jwtTokenProvider.getClaims(token, "userEmail");
            String role = "ROLE_" + jwtTokenProvider.getClaims(token, "role");

            // Add claims to the req attributes (opt, but would be [id, username, role])
            requestWrap.setAttribute("id", id);
            requestWrap.setAttribute("userEmail", userEmail);
            requestWrap.setAttribute("role", role);

            log.info(log_header + "Found the following tokens: \nid: " + id + "\nuserEmail: " + userEmail + "\nrole: " + role);
            
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing header for authorization");
            
            log.error(log_header + "Unable to validate token - missing auth header");
            return;
        }

        // Keep going with request
        filterChain.doFilter(requestWrap, response);
    }
}
