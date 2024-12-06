package com.linkaster.timetableService.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.linkaster.timetableService.model.Timetable;
import com.linkaster.timetableService.repository.EventsRepository;
import com.linkaster.timetableService.repository.TimetableRepository;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

/*
 *  Title: TimetableCoordinatorServiceTest.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Slf4j
class TimetableCoordinatorServiceTest {

    @Mock
    private TimetableRepository timetableRepository;

    @Mock
    private EventsRepository eventsRepository;

    @InjectMocks
    private TimetableCoordinatorService timetableCoordinatorService;

    private final String log_header = "TimetableCoordinatorService --- ";

    @BeforeEach
    void setUp() {
        log.info(log_header + "Setting up mocks for TimetableCoordinatorServiceTest...");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTimetable_SuccessfulCreation() {
        log.info(log_header + "Testing successful creation of the timetable after usercreation...");
        long userId = 1L;

        // Mock timetable does not exist for the user
        when(timetableRepository.timetableExistsByUserOwnerId(userId)).thenReturn(false);

        // Act: Call the createTimetable method
        log.info(log_header + "Calling createTimetable method...");
        ResponseEntity<Boolean> response = timetableCoordinatorService.createTimetable(userId);

        // Assert
        log.info(log_header + "Asserting results...");
        assertTrue(response.getBody());
        verify(timetableRepository, times(1)).save(any(Timetable.class));
        verify(timetableRepository, times(1)).timetableExistsByUserOwnerId(userId);
        
        log.info(log_header + "Test Successful!\n");
    }

    @Test
    void testCreateTimetable_AlreadyExists() {
        log.info(log_header + "Testing unsuccessful creation of the timetable after usercreation (timetable already exists)...");
        long userId = 1L;

        // Mock timetable already exists for the user
        when(timetableRepository.timetableExistsByUserOwnerId(userId)).thenReturn(true);

        // Act: Call the createTimetable method
        log.info(log_header + "Calling createTimetable method...");
        ResponseEntity<Boolean> response = timetableCoordinatorService.createTimetable(userId);

        // Assert
        log.info(log_header + "Asserting results...");
        assertFalse(response.getBody());
        verify(timetableRepository, never()).save(any(Timetable.class));
        verify(timetableRepository, times(1)).timetableExistsByUserOwnerId(userId);

        log.info(log_header + "Test Successful!\n");
    }
}
