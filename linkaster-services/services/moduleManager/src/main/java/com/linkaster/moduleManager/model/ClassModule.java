package com.linkaster.moduleManager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "classModules")
public class ClassModule {

    @Column(name = "teacherId")
    private long teacherId;

    @Column(name = "teacherName")
    private String teacherName;
    
}
