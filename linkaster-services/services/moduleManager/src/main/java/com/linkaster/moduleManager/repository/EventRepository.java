package com.linkaster.moduleManager.repository;

import com.linkaster.moduleManager.model.EventModel;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface EventRepository extends JpaRepository<EventModel, Long> {
    
}   