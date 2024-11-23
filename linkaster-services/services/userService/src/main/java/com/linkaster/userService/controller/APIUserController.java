
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
import org.springframework.web.bind.annotation.ResponseStatus;

import com.linkaster.userService.model.User;

/*
 * This interface defines the API endpoints for the User Controller.
 */
public interface APIUserController {
    // Home endpoint
    @GetMapping("")
    public String home();

    // CreateUser endpoint
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createUser/{roleName}")
    @ResponseStatus(HttpStatus.OK)
    public String createUser(@ModelAttribute User userInfo, @PathVariable String role);

    // DeleteUser endpoint
    @DeleteMapping("/deleteUser")
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
    
}