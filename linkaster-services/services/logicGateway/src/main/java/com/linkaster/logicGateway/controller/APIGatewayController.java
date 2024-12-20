package com.linkaster.logicGateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.linkaster.logicGateway.dto.UserLogin;
import com.linkaster.logicGateway.dto.UserRegistration;

import jakarta.servlet.http.HttpServletRequest;

/*
 * This interface defines the API endpoints for the Gateway Controller.
 */
/*
 *  Title: APIGatewayController.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@RequestMapping("/api")
public interface APIGatewayController {
    @GetMapping("")
    public String home();
    
    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public String status();

    @PostMapping("/auth/{user_type}/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody UserLogin loginRequest, @PathVariable("user_type") String user_type);

    @PostMapping("/auth/{user_type}/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> register(@RequestBody UserRegistration regRequest, @PathVariable("user_type") String user_type);

    // Special access. Only one authorization to enabling websocket for messaging:
    @RequestMapping("/message/**")
    public ResponseEntity<?> forwardToMessageService(HttpServletRequest request, @RequestBody String requestBody);

    // Access to services through gateway
    @RequestMapping("/user/**")
    public ResponseEntity<?> forwardToUserService(HttpServletRequest request, @RequestBody String requestBody);

    @RequestMapping("/module/**")
    public ResponseEntity<?> forwardToModuleService(HttpServletRequest request, @RequestBody String requestBody); 

    @RequestMapping("/feedback/**")
    public ResponseEntity<?> forwardToFeedbackService(HttpServletRequest request, @RequestBody String requestBody);

    @RequestMapping("/timetable/**")
    public ResponseEntity<?> forwardToTimetableService(HttpServletRequest request, @RequestBody String requestBody);
}
