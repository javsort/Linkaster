package com.linkaster.userService.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.linkaster.userService.model.TeacherUser;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherUser, Long> {
    // Find a user by its role
    @Query("SELECT u FROM User u WHERE u.role.role = :role")
    public List<TeacherUser> findByRole(@Param("role") String role);

    // Find a user by its username
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public TeacherUser findByEmail(@Param("email") String email);
    
}