package com.linkaster.moduleManager.repository;

import com.linkaster.moduleManager.model.Module;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    // Find module by module code
    @Query("SELECT m FROM Module m WHERE m.moduleCode = :moduleCode")
    Module findByModuleCode(@Param("moduleCode") String moduleCode);

    // Check if a module exists by module code
    boolean existsByModuleCode(String moduleCode);

    // Find modules by Student ID
    @Query("SELECT m FROM Module m WHERE :studentId MEMBER OF m.studentList")
    Iterable <Module> findAllByStudentId(@Param("studentId") Long studentId);

}

