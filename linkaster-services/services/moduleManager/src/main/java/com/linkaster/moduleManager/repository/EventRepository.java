/*package com.linkaster.moduleManager.repository;

import com.linkaster.moduleManager.model.EventModel;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


@Repository
public interface EventRepository extends JpaRepository<EventModel, Long> {
    
    // get events by module id
    public List<EventModel> findByModuleId(Long moduleId);

    // get events by userid
    public List<EventModel> findByOwnerId(Long ownerId);

}   */