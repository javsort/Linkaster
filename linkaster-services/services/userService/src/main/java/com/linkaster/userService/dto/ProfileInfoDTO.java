package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


  /*
 *  Title: UserCustomizerService.java
 *  Author: Gonzalez Fernandez, Marcos 
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProfileInfoDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    //student only fiels
    private String studentId;
    private String instagram;
    private String linkedin;
    private String phone;
    private String year;

    //teacher only fields
    private String github;
       
}
