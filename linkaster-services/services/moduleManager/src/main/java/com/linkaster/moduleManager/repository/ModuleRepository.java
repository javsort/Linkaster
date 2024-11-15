package com.linkaster.moduleManager.repository;

import com.linkaster.moduleManager.model.Module;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    
    // find module by code
    Module findByCode(String code);
    
}   
