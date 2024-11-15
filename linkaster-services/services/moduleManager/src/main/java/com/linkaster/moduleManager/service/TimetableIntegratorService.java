package com.linkaster.moduleManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.repository.ModuleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TimetableIntegratorService {

    @Autowired
    private ModuleRepository moduleRepository;

    public void updateTimetable(Module module) {
        log.info("Updating timetable for module {}", module);
        // Once the module is created or updated, send time, date, moduleName, and room to the timetable service


    }

    
}
