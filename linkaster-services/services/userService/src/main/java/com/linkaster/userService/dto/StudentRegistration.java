package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
// Student registration -> what we expect to receive from logicGateway when a STUDENT Registers
public class StudentRegistration extends UserRegistration {
    private String studentId;
    
    private String year;

    private String studyProg;
}
