package com.linkaster.notifHandler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkaster.notifHandler.model.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/*
 *  Title: NotificationsRepository.java
 *  Author: Berenger, Marlene
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    
    //find all notifications by receiver's id 
    @Query("SELECT n FROM Notification n where n.receiver = :id")
    public String getNotificationsByUserID(@Param("id") int id);
}