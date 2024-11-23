package com.linkaster.userService.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.dto.AssignTeacher;
import com.linkaster.userService.dto.UserRegistration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/teacher")     // This is the base path for the teacher actions -> EXCLUSIVE TO THIS CLASS SET (TeacherActionsController & StudentActionsController)
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

    @PreAuthorize("hasRole('ADMINTEACHER')")
    @PostMapping("/assignTeacher")
    public String assignTeachToClass(@RequestBody AssignTeacher assignedTeacher){
        return "Assigned Teacher successfully";
    }
    
}
