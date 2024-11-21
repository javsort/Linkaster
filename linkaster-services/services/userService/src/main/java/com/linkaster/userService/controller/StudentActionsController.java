package com.linkaster.userService.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.dto.UserRegistration;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/student")
public class StudentActionsController implements APIUserActionsController {
    
    @Override
    public String home() {
        return "Welcome Student!";
    }

    @Override
    public String register(UserRegistration userInfo, String role) {
        return "Student registration successful!";
    }


    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/joinModule/{module_code}")
    public String joinModule(@PathVariable("module_code") String module_code){
        return "Student joined module!";
    }
    
}
