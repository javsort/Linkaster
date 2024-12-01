package com.linkaster.messageHandler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.linkaster.messageHandler.model.group.GroupMessage;

/*
 * This is a repository interface to handle database actions for the GroupMessages.
 */
@Repository
public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long>{

    @Query("SELECT g FROM GroupMessage g WHERE g.groupChat.groupChatId = ?1")
    Iterable<GroupMessage> findByGroupChatId(long groupChatId);
    
}
