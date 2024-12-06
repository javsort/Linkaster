/*
 *  Title: ModuleRepository.java
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */


package com.linkaster.moduleManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.linkaster.moduleManager.model.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    // Find module by module code
    @Query("SELECT m FROM Module m WHERE m.moduleCode = :moduleCode")
    Module findByModuleCode(@Param("moduleCode") String moduleCode);

    // Check if a module exists by module code
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Module m WHERE m.moduleCode = :moduleCode")
    boolean existsByModuleCode(@Param("moduleCode") String moduleCode);

    // Find modules by Student ID
    @Query("SELECT m FROM Module m WHERE :studentId MEMBER OF m.studentList")
    Iterable <Module> findAllByStudentId(@Param("studentId") Long studentId);

}

