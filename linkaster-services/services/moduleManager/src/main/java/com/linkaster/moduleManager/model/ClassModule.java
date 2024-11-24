package com.linkaster.moduleManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
@Table(name = "class_modules")
public class ClassModule extends Module {

    @Column(name = "teacherId")
    private String teacherId;

    @Column(name = "teacherName")
    private String teacherName;
    
}
