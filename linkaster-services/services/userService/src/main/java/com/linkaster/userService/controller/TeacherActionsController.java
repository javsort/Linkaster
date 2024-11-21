package com.linkaster.userService.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.dto.UserRegistration;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/teacher")
public class TeacherActionsController implements APIUserActionsController {
    
    @Override
    public String home() {
        return "Welcome Teacher!";
    }

    @Override
    public String register(UserRegistration userInfo, String role) {
        return "Teacher registration successful!";
    }

    @Override
    public String getProfile() {

        return "Module created successfully!";
    }
    
}
