package com.linkaster.userService.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

/*
 * This class represents a student user.
 * It extends the User class.
 *
*/
@Entity
@Table(name = "students")
public class StudentUser extends User {

    @Column(name = "studentId", unique = true, nullable = false)
    private String studentId;
    
    @Column(name = "course")
    private String course;

    @Column(name = "year")
    private Integer year;
    
    @ElementCollection(fetch= FetchType.EAGER)
    @CollectionTable(name = "student_user_registered_modules", joinColumns = @JoinColumn(name = "student_user_id"))        // FOR NOW, I WILL USE A SEPARATE TABLE TO STORE THE MODULES
    @Column(name = "module_name")
    private List<String> registeredModules;
}