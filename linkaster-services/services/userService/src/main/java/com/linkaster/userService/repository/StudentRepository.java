package com.linkaster.userService.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.linkaster.userService.model.StudentUser;

 /*
 *  Title: StudentRepository.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Repository
public interface StudentRepository extends JpaRepository<StudentUser, Long> {
    // Find a user by its role
    @Query("SELECT u FROM User u WHERE u.role.role = :role")
    public List<StudentUser> findByRole(@Param("role") String role);

    // Find a user by its username
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public StudentUser findByEmail(@Param("email") String email);
    
}