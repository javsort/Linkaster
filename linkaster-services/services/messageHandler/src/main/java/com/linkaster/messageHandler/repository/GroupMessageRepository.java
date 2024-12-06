package com.linkaster.messageHandler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.linkaster.messageHandler.model.group.GroupMessage;

/*
 * This is a repository interface to handle database actions for the GroupMessages.
 */
/*
 *  Title: GroupMessageRepository.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Repository
public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long>{

    @Query("SELECT g FROM GroupMessage g WHERE g.groupChat.groupChatId = ?1")
    Iterable<GroupMessage> findByGroupChatId(long groupChatId);
    
}
