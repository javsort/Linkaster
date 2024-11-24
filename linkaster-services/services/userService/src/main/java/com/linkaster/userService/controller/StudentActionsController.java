package com.linkaster.userService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkaster.userService.dto.TeacherDTO;
import com.linkaster.userService.dto.UserRegistration;
import com.linkaster.userService.service.UserHandlerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/student")    // This is the base path for the student actions -> EXCLUSIVE TO THIS CLASS SET (TeacherActionsController & StudentActionsController)
public class StudentActionsController implements APIUserActionsController {

    private final UserHandlerService userHandlerService;

    @Autowired
    public StudentActionsController(UserHandlerService userHandlerService) {
        this.userHandlerService = userHandlerService;
    }

    
    @Override
    public String home() {
        return "Welcome Student!";
    }

    @Override
    public String register(UserRegistration userInfo, String role) {
        return "Student registration successful!";
    }

    @Override
    public String getProfile() {
        return "Student profile retrieved!";
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/joinModule/{module_code}")
    public String joinModule(@PathVariable("module_code") String module_code){
        return "Student joined module!";
    }

    // Get all the teachers of the student based on studentId
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/getTeachers")
    public Iterable<TeacherDTO> getStudentTeachers(HttpServletRequest request){

        return userHandlerService.getStudentTeachers(request.getAttribute("userEmail").toString());
    }

    
}
