package com.linkaster.timetableService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkaster.timetableService.model.EventModel;

@Repository
public interface EventsRepository extends JpaRepository<EventModel, Long>  {
    
}
