package com.linkaster.messageHandler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkaster.messageHandler.message.p2p.PrivateChat;


/*
 * This is a repository interface to handle all private chats.
 */
@Repository
public interface PrivateChatRepository extends JpaRepository<PrivateChat, Object>{
    
}
