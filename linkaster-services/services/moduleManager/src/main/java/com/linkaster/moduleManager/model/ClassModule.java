package com.linkaster.moduleManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
@Builder
@Table(name = "class_modules")
public class ClassModule extends Module {

    @Column(name = "teacherId")
    private String teacherId;

    @Column(name = "teacherName")
    private String teacherName;
    
}
