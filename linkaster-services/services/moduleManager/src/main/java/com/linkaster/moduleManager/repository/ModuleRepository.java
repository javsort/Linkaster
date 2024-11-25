package com.linkaster.moduleManager.repository;

import com.linkaster.moduleManager.model.Module;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    
    // find module by code
    @Query("SELECT m FROM Module m WHERE m.moduleCode = :moduleCode")
    public Module findByCode(@Param("moduleCode") String moduleCode);

    // check if module exists by code
    boolean existsByCode(String code);
    
}   
