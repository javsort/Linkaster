package com.linkaster.userService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.linkaster.userService.dto.UserRegistration;

import jakarta.servlet.http.HttpServletRequest;

public interface APIUserActionsController {

    @GetMapping("")
    public String home();

    @PostMapping("/{role}/register")
    public String register(@RequestBody UserRegistration userInfo, @PathVariable("role") String role);

    @GetMapping("/{role}/getProfile")
    public String getProfile();

    @PostMapping("/assignModuleManager")
    public String assignModuleManager(HttpServletRequest request);

    
}
