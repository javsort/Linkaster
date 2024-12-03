
package com.linkaster.userService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.linkaster.userService.dto.ProfileInfoDTO;
import com.linkaster.userService.dto.UserRegistration;
import com.linkaster.userService.model.User;

import jakarta.servlet.http.HttpServletRequest;

/*
 * This interface defines the API endpoints for the User Controller.
 */

@RequestMapping("/api/user")
public interface APIUserController {
    // Home endpoint
    @GetMapping("")
    public String home();

    // CreateUser endpoint -> ADMIN ACCESS ONLY
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create/{roleName}")
    @ResponseStatus(HttpStatus.OK)
    public String createUser(@ModelAttribute UserRegistration regRequest, @PathVariable String roleName);

    // DeleteUser endpoint
    @DeleteMapping("/delete/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable long user_id);

    // UpdateUser endpoint
    @PostMapping("/updateUser")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateUser(@RequestBody User userToUpdate);

    // GetUser endpoint
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getUser/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable Long id);

    // GetAllUsers endpoint
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers();

    // GetUsersByRole endpoint
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getUsersByRole/{role}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsersByRole( @PathVariable String role);

    // GetStudentProfile endpoint
    @GetMapping("/getStudentProfile")
    @ResponseStatus(HttpStatus.OK)
    public ProfileInfoDTO getStudentProfile(HttpServletRequest request);

    // GetTeacherProfile endpoint
    @GetMapping("/getTeacherProfile")
    @ResponseStatus(HttpStatus.OK)
    public ProfileInfoDTO getTeacherProfile(HttpServletRequest request);

    
}