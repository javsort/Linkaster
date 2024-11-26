package com.linkaster.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AssignTeacher {
    private String teacherEmail;
    private String moduleId;
}
