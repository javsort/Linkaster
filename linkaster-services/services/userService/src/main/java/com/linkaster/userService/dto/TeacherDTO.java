package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


/*
 *  Title: TeacherDTO.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TeacherDTO {
    // DTO to send teacher data to the client or between services
    private long id;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String module;
}
