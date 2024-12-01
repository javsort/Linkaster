package com.linkaster.feedbackService.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

//feedback contains: id, recipientName, senderName, anonymous, recipientModule, and contents
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @Column(name = "feedbackID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long FeedbackID;

    @Column(name = "recipientID")
    private long recipientID;

    @Column(name = "senderID")
    private long senderID;

    @Column(name = "anonymous")
    private boolean anonymous;

    @Column(name = "moduleID")
    private long moduleID;

    @Column(name = "contents")
    private String contents;

    public boolean getAnonymous(){
        return anonymous;
    }

    public void setFeedbackID (long newFeedbackID) {
        this.FeedbackID = newFeedbackID;
    }

    public void setRecipientID (long newRecipientID) {
        this.recipientID = newRecipientID;
    }

    public void setSenderID(long newID){
        this.senderID = newID;
    }

    public void setAnonymous (boolean newAnonymous) {
        this.anonymous = newAnonymous;
    }

    public void setModuleID (long newModuleID) {
        this.moduleID = newModuleID;
    }

    public void setContents (String newContents) {
        this.contents = newContents;
    }

    public long getSenderID() {
        return senderID;
    }
}