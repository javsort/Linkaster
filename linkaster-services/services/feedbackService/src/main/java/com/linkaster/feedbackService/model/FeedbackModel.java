package com.linkaster.feedbackService.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;

//feedback contains: id, recipientName, senderName, anonymous, recipientModule, and contents
public class FeedbackModel {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "recipientName")
    private String recipientName;

    @Column(name = "senderName")
    private String senderName;

    @Column(name = "anonymous")
    private boolean anonymous;

    @Column(name = "recipientModule")
    private String recipientModule;

    @Column(name = "contents")
    private String contents;

    public boolean getAnonymous(){
        return anonymous;
    }

    public void setSenderName(String newName){
        this.senderName = newName;
    }

    public String getSenderName() {
        return senderName;
    }
}