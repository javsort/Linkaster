package com.linkaster.notifHandler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkaster.notifHandler.model.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    
    //find all notifications by receiver's id 
    @Query("SELECT n FROM Notification n where n.receiver = :id")
    public String getNotificationsByUserID(@Param("id") int id);
}

// id, sender, timesent, contents, location, receiver