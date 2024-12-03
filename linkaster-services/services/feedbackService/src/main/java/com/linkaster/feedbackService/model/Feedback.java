package com.linkaster.feedbackService.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

/*
 * This class represents a student user.
 * It extends the User class.
 *
*/
@Entity
@Table(name = "feedbacks")
//feedback contains: id, recipientName, senderName, anonymous, recipientModule, and contents
public class Feedback {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "feedback_recipient_id")
    private long recipientID;
    
    @Column(name = "feedback_sender_id")
    private long senderID;
    
    @Column(name = "feedback_anonymous")
    private boolean anonymous;
    
    @Column(name = "feedback_recipient_module")
    private long moduleID;
    
    @Column(name = "feedback_contents")
    private String contents;
    

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public boolean getAnonymous(){
        return anonymous;
    }

    public void setAnonymous (boolean anonymous) {
        this.anonymous = anonymous;
    }

    public long getRecipientID() {
        return recipientID;
    }

    public void setRecipientID (long recipientID) {
        this.recipientID = recipientID;
    }

    public long getSenderID() {
        return senderID;
    }

    public void setSenderID(long senderID){
        this.senderID = senderID;
    }

    public long getModuleID() {
        return moduleID;
    }

    public void setModuleID (long moduleID) {
        this.moduleID = moduleID;
    }

    public String getContents() {
        return contents;
    }
    
    public void setContents (String contents) {
        this.contents = contents;
    }
}