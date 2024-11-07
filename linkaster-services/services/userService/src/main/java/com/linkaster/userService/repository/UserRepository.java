package com.linkaster.userService.repository;

import com.linkaster.userService.model.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
