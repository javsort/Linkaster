package com.linkaster.userService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.dto.AssignTeacher;
import com.linkaster.userService.dto.UserRegistration;
import com.linkaster.userService.service.UserHandlerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/teacher")     // This is the base path for the teacher actions -> EXCLUSIVE TO THIS CLASS SET (TeacherActionsController & StudentActionsController)
public class TeacherActionsController implements APIUserActionsController {
    
    @Autowired
    private final UserHandlerService userHandlerService;

    public TeacherActionsController(UserHandlerService userHandlerService) {
        this.userHandlerService = userHandlerService;
    }

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

    @Override
    public String assignModuleManager(HttpServletRequest request) {
        String userEmail = request.getAttribute("userEmail").toString();
        log.info("Request received to get assign Teacher to Module with email: '" + userEmail + "'");
        return userHandlerService.assignModuleManager(userEmail);   
    }

    
}
