/*
 *  Title: ModuleManagerServiceTest.java
 *  Author: Marcos Gonzalez Fernandez
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */


package com.linkaster.moduleManager.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.linkaster.moduleManager.repository.ModuleRepository;

import jakarta.servlet.http.HttpServletRequest;

import com.linkaster.moduleManager.dto.ModuleCreate;
import com.linkaster.moduleManager.model.Module;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModuleManagerServiceTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private ModuleRepository moduleRepository;

    @InjectMocks
    private ModuleManagerService moduleService; // Assuming the service is called ModuleService

    private ModuleCreate moduleCreate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks
        moduleCreate = new ModuleCreate("class_module", "ExampleMock", "129u23"); // Example
        
    }

    @Test
    public void testCreateModule_ModuleAlreadyExists() {
        // Mock HttpServletRequest
        when(request.getAttribute("id")).thenReturn("1");
        when(request.getAttribute("userEmail"))
        .thenReturn("user@example.com");

        // Mock moduleRepository to return that the module already exists
        when(moduleRepository.existsByModuleCode(anyString()))
        .thenReturn(true);

        // Call the method
        Module createdModule = moduleService.
        createModule(request, moduleCreate, "teacher");

        // Verify that no module is created
        assertNull(createdModule);
        verify(moduleRepository, never()).save(any(Module.class)); // Verify save was not called
    }

    @Test
    public void testCreateModule_InvalidOwnerId() {
        // Mock HttpServletRequest with an invalid ID
        when(request.getAttribute("id")).thenReturn("invalidId");
        when(request.getAttribute("userEmail")).thenReturn("user@example.com");

        // Call the method
        Module createdModule = moduleService.createModule(request, moduleCreate, "teacher");

        // Verify that the method handles the exception gracefully
        assertNull(createdModule);
    }
}

