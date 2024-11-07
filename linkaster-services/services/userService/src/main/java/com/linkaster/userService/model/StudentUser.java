package com.linkaster.userService.model;

import jakarta.persistence.*;
import java.util.Map;

@Entity
@Table(name = "students")
public class StudentUser extends User {

    @Column(name = "studentId", unique = true)
    private String studentId;
    
    @Column(name = "course")
    private String course;

    @Column(name = "year")
    private Integer year;

    @Column(name = "socialMedia")
    private Map<String, String> socialMedia;

    @Column(name = "modules")
    private Map<Integer, String> modules;
}
