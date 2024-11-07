package com.linkaster.moduleManager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clubModules")
public class ClubModule extends Module {
    
    @Column(name = "studentId")
    private long studentId;

    @Column(name = "studentName")
    private String studeentName;
}
