package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/*
 *  Title: AuthUser.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthUser {
    // This is a user that has been authenticated -> Necessary info for JWT 
    
    private Long id;

    private String userEmail;

    private String role; 
}
