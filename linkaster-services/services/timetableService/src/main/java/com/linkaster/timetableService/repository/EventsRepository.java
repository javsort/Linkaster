package com.linkaster.timetableService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkaster.timetableService.model.EventModel;

/*
 *  Title: EventsRepository.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Repository
public interface EventsRepository extends JpaRepository<EventModel, Long>  {
    
}
