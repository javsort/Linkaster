package com.linkaster.feedbackService.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.annotation.Generated;
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
    private int FeedbackID;

    @Column(name = "recipientID")
    private int recipientID;

    @Column(name = "sender_id")
    private int senderID;

    @Column(name = "anonymous")
    private boolean anonymous;

    @Column(name = "moduleID")
    private long moduleID;

    @Column(name = "contents")
    private String contents;

    public boolean getAnonymous(){
        return anonymous;
    }

    public void setFeedbackID (int newFeedbackID) {
        this.FeedbackID = newFeedbackID;
    }

    public void setRecipientID (int newRecipientID) {
        this.recipientID = newRecipientID;
    }

    public void setSenderID(int newID){
        this.senderID = newID;
    }

    public void setAnonymous (boolean newAnonymous) {
        this.anonymous = newAnonymous;
    }

    public void setModuleID (int newModuleID) {
        this.moduleID = newModuleID;
    }

    public void setContents (String newContents) {
        this.contents = newContents;
    }

    public int getSenderID() {
        return senderID;
    }
}