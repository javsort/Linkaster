package com.linkaster.userService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.linkaster.userService.model.User;

  /*
 *  Title: UserRepository.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*
     * This is a repository interface to handle database actions for the User entity.
     */

    // Find a user by its role
    @Query("SELECT u FROM User u WHERE u.role.role = :role")
    public List<User> findByRole(@Param("role") String role);

    // Find a user by its username
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmail(@Param("email") String email);

}
