package com.linkaster.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.linkaster.userService.model.Role;

 /*
 *  Title: RoleRepository.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Repository
public interface  RoleRepository extends JpaRepository<Role, Integer> {
    /*
     * This is a repository interface to handle database actions for the Role entity.
     */
    
    // Find a role by its name
    @Query("SELECT r FROM Role r WHERE r.role = :role")
    public Role findByRole(@Param("role") String role);
}
