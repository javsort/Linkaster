package com.linkaster.messageHandler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.linkaster.messageHandler.model.p2p.PrivateChat;


/*
 * This is a repository interface to handle all private chats.
 */

    /*
 *  Title: PrivateChatRepository.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Repository
public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long>{

    @Query("SELECT p FROM PrivateChat p WHERE (p.user1.userId = ?1 AND p.user2.userId = ?2) OR (p.user1.userId = ?2 AND p.user2.userId = ?1)")
    PrivateChat findByUser1AndUser2(long user1, long user2);

    @Query("SELECT p FROM PrivateChat p WHERE p.user1.userId = ?1 OR p.user2.userId = ?1")
    Iterable<PrivateChat> getUserChats(long userId);
    
}
