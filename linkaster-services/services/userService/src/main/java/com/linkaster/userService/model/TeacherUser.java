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
 * This class represents a teacher user.
 */

@Entity
@Table(name = "teachers")
public class TeacherUser extends User {

    @ElementCollection(fetch= FetchType.EAGER)
    @CollectionTable(name = "teacher_user_modules", joinColumns = @JoinColumn(name = "teacher_user_id"))        // FOR NOW, I WILL USE A SEPARATE TABLE TO STORE THE MODULES
    @Column(name = "module_name")
    private List<String> teachingModules;
}
