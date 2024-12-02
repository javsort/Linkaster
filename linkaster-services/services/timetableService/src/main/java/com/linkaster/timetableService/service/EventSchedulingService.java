package com.linkaster.timetableService.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.timetableService.dto.EventSeedDTO;
import com.linkaster.timetableService.repository.EventsRepository;
import com.linkaster.timetableService.repository.TimetableRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EventSchedulingService {
    
    private final TimetableRepository timetableRepository;
    private final EventsRepository eventsRepository;
    
    private final String log_header = "EventSchedulingService --- ";

    @Autowired
    public EventSchedulingService(TimetableRepository timetableRepository, EventsRepository eventsRepository) {
        this.timetableRepository = timetableRepository;
        this.eventsRepository = eventsRepository;
    }

    public void sproutEvents(EventSeedDTO eventSeedDTO){
        log.info(log_header + "Sprouting events...");
    }
}
