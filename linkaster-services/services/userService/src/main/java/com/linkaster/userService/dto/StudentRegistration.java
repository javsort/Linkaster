package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 *  Title: StudentRegistration.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudentRegistration extends UserRegistration {
    // Student registration -> what we expect to receive from logicGateway when a STUDENT Registers
    
    private String studentId;
    
    private String year;

    private String studyProg;

    
}
