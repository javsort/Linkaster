package com.linkaster.timetableService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.linkaster.timetableService.model.Timetable;
import com.linkaster.timetableService.repository.EventsRepository;
import com.linkaster.timetableService.repository.TimetableRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TimetableCoordinatorService {
    
    private final TimetableRepository timetableRepository;
    private final EventsRepository eventsRepository;

    private final String log_header = "TimetableCoordinatorService --- ";

    @Autowired
    public TimetableCoordinatorService(TimetableRepository timetableRepository, EventsRepository eventsRepository) {
        this.timetableRepository = timetableRepository;
        this.eventsRepository = eventsRepository;
    }

    public ResponseEntity<Boolean> createTimetable(long userId) {
        Long userIdToCreate = userId;

        // Check repo if timetable with userId exists
        if(timetableRepository.timetableExistsByUserOwnerId(userIdToCreate)) {
            log.info(log_header + "Timetable already exists for user with id: " + userIdToCreate + ". No action taken.");
            return ResponseEntity.ok(false);
        }
        
        // Create timetable
        Timetable newTimetable = new Timetable();
        newTimetable.setUserOwnerId(userIdToCreate);
        timetableRepository.save(newTimetable);
        log.info(log_header + "Timetable created for user with id: " + userIdToCreate);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<Boolean> deleteTimetable(long userId) {
        Long userIdToDelete = userId;

        // Check repo if timetable with userId exists
        if(!timetableRepository.timetableExistsByUserOwnerId(userIdToDelete)) {
            log.info(log_header + "Timetable does not exist for user with id: " + userIdToDelete + ". No action taken.");
            return ResponseEntity.ok(false);
        }

        // Delete timetable
        Timetable timetableToDelete = timetableRepository.findByUserOwnerId(userIdToDelete);
        timetableRepository.delete(timetableToDelete);
        log.info(log_header + "Timetable deleted for user with id: " + userIdToDelete);
        return ResponseEntity.ok(true);
    }

    // Get timetable by userId
    public Timetable getTimetableByUserId(long userId) {
        return timetableRepository.findByUserOwnerId(userId);
    }

}
