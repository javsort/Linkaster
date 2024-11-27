package com.linkaster.moduleManager.repository;

import com.linkaster.moduleManager.model.Announcement;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    
    // find announcement by user id
    public Iterable<Announcement> findByOwnerId(Long ownerId);
}  
