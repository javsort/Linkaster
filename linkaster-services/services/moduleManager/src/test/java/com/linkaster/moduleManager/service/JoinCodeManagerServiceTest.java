package com.linkaster.moduleManager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.linkaster.moduleManager.dto.JoinModuleCreate;

import jakarta.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.linkaster.moduleManager.model.Module;
import com.linkaster.moduleManager.repository.ModuleRepository;


import java.util.ArrayList;
import java.util.List;

public class JoinCodeManagerServiceTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ModuleManagerService moduleService; 

    @InjectMocks
    private JoinCodeManagerService joinCodeManagerService; 

    private ModuleRepository moduleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
        moduleRepository = mock(ModuleRepository.class);

        joinCodeManagerService = new JoinCodeManagerService();
    }

    @Test
    public void testJoinModuleByCode_Success() {
    // Prepare test data
    MockitoAnnotations.openMocks(this);
    JoinModuleCreate joinModuleCreate = new JoinModuleCreate("moduleCode123");
    HttpServletRequest request = mock(HttpServletRequest.class);

    // Mock request attributes
    when(request.getAttribute("id")).thenReturn("1");

    // Prepare module for testing
    Module existingModule = Module.builder()
        .moduleId(1L)
        .moduleCode("moduleCode123")
        .moduleName("Test Module")
        .moduleOwnerId(2L)
        .moduleOwnerName("email@examle.com")
        .moduleOwnerType("teacher")
        .type("class_module")
        .studentList(new ArrayList<Long>())
        .build();

    // Mock repository methods
    when(moduleRepository.findByModuleCode("moduleCode123")).thenReturn(existingModule);
    when(moduleRepository.save(any(Module.class))).thenReturn(existingModule);

    // Call method
    boolean result = joinCodeManagerService.joinModuleByCode(joinModuleCreate, request);

    // Verify results
    assertTrue(result, "Join module should succeed");
    assertTrue(existingModule.getStudentList().contains(1L), "Student should be added to module");
    verify(moduleRepository).save(existingModule);
}

    
        
}
