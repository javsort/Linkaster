package com.linkaster.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.linkaster.userService.model.Role;

/*
 * This is a repository interface to handle database actions for the Role entity.
 */
@Repository
public interface  RoleRepository extends JpaRepository<Role, Integer> {
    
    // Find a role by its name
    @Query("SELECT r FROM Role r WHERE r.role = :role")
    public Role findByRole(@Param("role") String role);
}
