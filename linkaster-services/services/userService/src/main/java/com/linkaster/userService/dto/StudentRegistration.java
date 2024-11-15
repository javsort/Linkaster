package com.linkaster.userService.dto;

// Student registration -> what we expect to receive from logicGateway when a STUDENT Registers
public class StudentRegistration extends UserRegistration {
    private String studentId;
    
    private String year;

    private String studyProg;
}
