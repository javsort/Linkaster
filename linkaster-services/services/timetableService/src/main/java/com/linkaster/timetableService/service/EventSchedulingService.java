package com.linkaster.timetableService.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.timetableService.dto.EventSeedDTO;
import com.linkaster.timetableService.model.EventModel;
import com.linkaster.timetableService.model.Timetable;
import com.linkaster.timetableService.repository.EventsRepository;
import com.linkaster.timetableService.repository.TimetableRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/*
 *  Title: EventSchedulingService.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
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
        log.info(log_header + "Creating event and sprouting for the following module:" + eventSeedDTO.getModuleId() + " ...");
        // get specs to generate the event for
        int repsToGen = Integer.parseInt(eventSeedDTO.getRepsToGen());
        String interval = eventSeedDTO.getInterval();
        List<Long> userIdsInModule = eventSeedDTO.getUserIdsInModule();

        Date startDate = eventSeedDTO.getStartDate();

        // then make the event list
        List<EventModel> events = new ArrayList<>();

        // Generate recurring events
        for (Long userId : userIdsInModule) {
            Timetable timetable = timetableRepository.findByUserOwnerId(userId);

            if (timetable != null) {
                log.info(log_header + "Generating events for user ID: " + userId);

                for (int i = 0; i < repsToGen; i++) {
                    // Calculate the date for the recurring event
                    Date eventDate = getWeeks(startDate, i * 7);

                    // Create a new event
                    EventModel event = EventModel.builder()
                            .moduleId(eventSeedDTO.getModuleId())
                            .name(eventSeedDTO.getName())
                            .startTime(eventSeedDTO.getStartTime())
                            .endTime(eventSeedDTO.getEndTime())
                            .date(eventDate)
                            .room(eventSeedDTO.getRoom())
                            .ownerId(eventSeedDTO.getOwnerId())
                            .timetable(timetable) // Associate the event with the user's timetable
                            .build();

                    // Save the event
                    eventsRepository.save(event);
                }

            } else {
                log.warn(log_header + "Timetable not found for user ID: " + userId);
            }
        }

    }

    private Date getWeeks(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return new Date(calendar.getTimeInMillis());
    }
}
