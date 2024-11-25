package com.linkaster.userService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/user/student")    // This is the base path for the student actions -> EXCLUSIVE TO THIS CLASS SET (TeacherActionsController & StudentActionsController)
public class StudentActionsController implements APIUserActionsController {

    private final UserHandlerService userHandlerService;
    private final String log_header = "StudentActionsController --- ";

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

    @Override
    public String assignModuleManager(HttpServletRequest request) {
        String userEmail = request.getAttribute("userEmail").toString();
        log.info(log_header + "Request received to get assign ClubStudent to Module with email: '" + userEmail + "'");
        return userHandlerService.assignModuleManager(userEmail);   
    }

    /*
     * Student only requests
     */

    // Join a module based on module_code
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/joinModule/{module_code}")
    public String joinModule(@PathVariable("module_code") String module_code){
        return "Student joined module!";
    }

    // Get all the teachers of the student based on studentId - INTERSERVICE COMMUNICATION
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/getTeachers")
    public Iterable<TeacherDTO> getStudentTeachers(HttpServletRequest request){
        String userEmail = request.getAttribute("userEmail").toString();
        log.info(log_header + "Request received to get student's teachers with the following email: '" + userEmail + "'");
        return userHandlerService.getStudentTeachers(request);   
    }

    
}
