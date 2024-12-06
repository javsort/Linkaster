package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/*
 *  Title: TeacherRegistration.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeacherRegistration extends UserRegistration {
    // Teacher registration -> what we expect to receive from logicGateway when a TEACHER Registers
    
    private String subject;
}
