package com.linkaster.messageHandler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.linkaster.messageHandler.model.group.GroupChat;

/*
 * This is a repository interface to handle all group chats.
 */
@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, Long>{
    
    @Query("SELECT g FROM GroupChat g JOIN g.groupMembers m WHERE KEY(m) = ?1")
    Iterable<GroupChat> getGroupChats4User(long userId);
    
}
