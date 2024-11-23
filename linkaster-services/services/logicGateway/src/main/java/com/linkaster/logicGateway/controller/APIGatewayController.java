package com.linkaster.logicGateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.linkaster.logicGateway.dto.UserLogin;
import com.linkaster.logicGateway.dto.UserRegistration;

/*
 * This interface defines the API endpoints for the Gateway Controller.
 */
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

}
