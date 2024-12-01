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
import org.springframework.web.util.ContentCachingRequestWrapper;

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

    // Secret key for JWT verification -> defined at application.yml or docker-compose
    @Value("${jwt.secret}")
    private String secret;

    private final String log_header = "JwtFilter --- ";

    // Filter method
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Wrap the request to read the body multiple times
        ContentCachingRequestWrapper requestWrap = new ContentCachingRequestWrapper(request);

        // Log the request
        log.info(log_header + "Request: " + requestWrap.getRequestURI() + " Method: " + requestWrap.getMethod());
        log.info(log_header + "Authorization Header: '" + requestWrap.getHeader("Authorization") + "'");
        
        // And its body
        if(requestWrap.getContentAsByteArray().length > 0){
            String reqBody = new String(requestWrap.getContentAsByteArray(), requestWrap.getCharacterEncoding());
            log.info(log_header + "Request body: " + reqBody);

        }

        // Public endpoints
        if (requestWrap.getRequestURI().equals("/api/login") 
            || requestWrap.getRequestURI().equals("/api/status") 
            || requestWrap.getRequestURI().equals("/api/auth/{user_type}/login")
            || requestWrap.getRequestURI().equals("/api/auth/admin/login")
            || requestWrap.getRequestURI().equals("/api/auth/student/login")
            || requestWrap.getRequestURI().equals("/api/auth/teacher/login")
            || requestWrap.getRequestURI().equals("/api/auth/admin/register")
            || requestWrap.getRequestURI().equals("/api/auth/student/register")
            || requestWrap.getRequestURI().equals("/api/auth/teacher/register")){
            log.info(log_header + "Public endpoint accessed, no token required");
            filterChain.doFilter(request, response);
            return;
        }
        
        // For all authenticated accesses
        String authHeader = requestWrap.getHeader("Authorization");

        log.info(log_header + "About to verify access token: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            log.info(log_header + "Token found, verifying...");

            // Extract token from header
            String token = authHeader.substring(7);
            log.info(log_header + "Token: " + token);

            // Verify token
            try {
                log.info(log_header + "Verifying token...");
                JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
                DecodedJWT decodedToken = verifier.verify(token);

                log.info(log_header + "Token verified: " + decodedToken);

                String id = decodedToken.getClaim("id").asString();
                String userEmail = decodedToken.getClaim("userEmail").asString();
                String role = "ROLE_" + decodedToken.getClaim("role").asString();

                // Add claims to the req attributes (opt, but would be [id, username, role])
                request.setAttribute("id", id);
                request.setAttribute("userEmail", userEmail);
                request.setAttribute("role", role);

                log.info(log_header + "Request attributes set: \n id: " + id + "\n userEmail: " + userEmail + "\n role: " + role);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userEmail, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info(log_header + "UsernamePasswordAuthenticationToken set: " + authentication);
                log.info(log_header + "Forwarding request to: " + request.getRequestURI());
                log.info(log_header + "Request body: " + new String(requestWrap.getContentAsByteArray(), requestWrap.getCharacterEncoding()));

            } catch (JWTVerificationException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token at the request");
                return;
            }

        // If token is missing    
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing token");

            log.error(log_header + "Missing token, unauthorized access for: '" + request + "' Headers:");
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                log.error(log_header + headerName + ": " + request.getHeader(headerName));
            }

            return;
        }
        
        log.info(log_header + "Request forwarded to: " + request.getRequestURI());
        
        // Continue with the request
        filterChain.doFilter(request, response);
    }
}
