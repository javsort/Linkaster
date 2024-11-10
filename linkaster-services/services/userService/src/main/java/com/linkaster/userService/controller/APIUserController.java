
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

public interface APIUserController {
    @GetMapping("")
    public String home();

    @PostMapping("/createUser/{roleName}")
    @ResponseStatus(HttpStatus.OK)
    public String createUser(@ModelAttribute User userInfo, @PathVariable String role);

    @DeleteMapping("/deleteUser")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestBody User userToDel);

    @PostMapping("/updateUser")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateUser(@RequestBody User userToUpdate);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getUser/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable Long id);

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers();

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getUsersByRole/{role}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsersByRole( @PathVariable String role);
    
}