package com.linkaster.messageHandler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkaster.messageHandler.model.PrivateMessage;

/*
 * This is a repository interface to handle database actions for the PrivateMessage entity.
 */
@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Object>{
    
}
