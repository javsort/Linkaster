
package com.linkaster.userService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.linkaster.userService.model.User;

public interface APIUserController {
    @GetMapping("")
    public String home();

    @GetMapping("/createUser")
    public String createUser();

    @GetMapping("/deleteUser")
    public void deleteUser();

    @GetMapping("/updateUser")
    public boolean updateUser();

    @GetMapping("/getUser")
    public User getUser();

    @GetMapping("/getAllUsers")
    public User[] getAllUsers();

    @GetMapping("/getUsersByRole/{role}")
    public User[] getUsersByRole(@PathVariable String role);

    
}