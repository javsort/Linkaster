package com.linkaster.moduleManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


// DTO to send teacher data to the client or between services
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TeacherDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String module;
}
