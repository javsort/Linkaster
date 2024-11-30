package com.linkaster.moduleManager.repository;

import com.linkaster.moduleManager.model.Announcement;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    
    // find announcement by user id
    public Iterable<Announcement> findByOwnerId(Long ownerId);

    // find announcement by module id
    public List<Announcement> findAllByModuleId(Long moduleId);
}  
