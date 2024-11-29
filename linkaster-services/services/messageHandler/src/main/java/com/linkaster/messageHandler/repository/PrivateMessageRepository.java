package com.linkaster.messageHandler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.linkaster.messageHandler.model.p2p.PrivateMessage;

/*
 * This is a repository interface to handle database actions for the PrivateMessage entity.
 */
@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Object>{

    @Query("SELECT p FROM PrivateMessage p WHERE p.privateChat.privateChatId = ?1")
    Iterable<PrivateMessage> findByPrivateChatId(long messageId);
}
