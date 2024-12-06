/*
 *  Title: Module.java
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */


package com.linkaster.moduleManager.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "modules")
public class Module {

    @Id
    @Column(name = "module_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;

    @Column(name = "module_owner_id", unique = true, nullable = false)
    private Long moduleOwnerId;

    @Column(name = "module_owner_name", unique = true, nullable = false)
    private String moduleOwnerName;

    @Column(name = "module_owner_type", unique = true, nullable = false)
    private String moduleOwnerType;

    @Column(name = "module_name", unique = true, nullable = false)
    private String moduleName;

    @Column(name = "module_code", unique = true, nullable = false)
    private String moduleCode;    

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "module_student_list", joinColumns = @JoinColumn(name = "module_id"))
    @Column(name = "student_id")
    private List<Long> studentList = new ArrayList<>();

    @Column(name = "module_type")
    private String type;
}
