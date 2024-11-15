package com.linkaster.logicGateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface APIGatewayController {
    @GetMapping("")
    public String home();
    
    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public String status();

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password);
}
