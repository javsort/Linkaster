package com.linkaster.messageHandler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkaster.messageHandler.model.GroupMessage;

/*
 * This is a repository interface to handle database actions for the GroupMessages.
 */
@Repository
public interface GroupMessageRepository extends JpaRepository<GroupMessage, Object>{
    
}
