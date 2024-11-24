package com.linkaster.userService.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/*
 * This class represents a teacher user.
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Table(name = "teachers")
public class TeacherUser extends User {

    
    @Column(name = "subject")
    private String subject;

    
    @Column(name = "teaching_modules_ids")
    private List<String> teachingModulesIds;

}
