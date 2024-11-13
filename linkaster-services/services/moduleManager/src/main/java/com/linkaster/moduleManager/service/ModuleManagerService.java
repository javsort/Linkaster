package com.linkaster.moduleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.repository.ModuleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ModuleManagerService {

    @Autowired
    private ModuleRepository moduleRepository;

    public List<Module> getAllModules() {
        log.info("Getting all modules");
        return moduleRepository.findAll();
    }

    public Module getModuleById(long id) {
        log.info("Getting module by ID: {}", id);
        return moduleRepository.findById(id).orElse(null);
    }

    public void deleteModule(long id) {
        log.info("Deleting module by ID: {}", id);
        moduleRepository.deleteById(id);
    }

    public void generateReport() {
        log.info("Generating report for all modules");
        // Logic to generate reports on module performance, teacher assignments, etc.
    }
}
