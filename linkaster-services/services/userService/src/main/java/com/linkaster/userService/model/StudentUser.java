package com.linkaster.userService.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;

/*
 * This class represents a student user.
 * It extends the User class.
 *
@Entity
@Table(name = "students")
public class StudentUser extends User {

    @Column(name = "studentId", unique = true)
    private String studentId;
    
    @Column(name = "course")
    private String course;

    @Column(name = "year")
    private Integer year;
    
    @ElementCollection(fetch= FetchType.EAGER)
    @Column(name = "modules")
    private List<Long> registered_modules;
}
*/