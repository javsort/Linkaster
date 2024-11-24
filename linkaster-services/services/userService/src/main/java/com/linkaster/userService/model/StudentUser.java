package com.linkaster.userService.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/*
 * This class represents a student user.
 * It extends the User class.
 *
*/
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
@Getter
@Table(name = "students")
public class StudentUser extends User {

    @Column(name = "studentId", unique = true, nullable = false)
    private String studentId;
    
    @Column(name = "course")
    private String course;

    @Column(name = "year")
    private String year;
    
    @Column(name = "enrolled_modules_ids")
    private List<String> enrolledModulesIds;
}