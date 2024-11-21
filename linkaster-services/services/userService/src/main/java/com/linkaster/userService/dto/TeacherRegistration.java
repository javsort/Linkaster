package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


// Teacher registration -> what we expect to receive from logicGateway when a TEACHER Registers
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeacherRegistration extends UserRegistration {

    private String teacherId;  
}
