package com.linkaster.feedbackService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FeedbackDTO {
    private long id;
    private long recipientID;
    private long senderID;
    private boolean anonymous;
    private long moduleID;
    private String contents;

    // Getter and Setter for id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getter and Setter for recipientID
    public long getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(long recipientID) {
        this.recipientID = recipientID;
    }

    // Getter and Setter for senderID
    public long getSenderID() {
        return senderID;
    }

    public void setSenderID(long senderID) {
        this.senderID = senderID;
    }

    // Getter and Setter for anonymous
    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    // Getter and Setter for moduleID
    public long getModuleID() {
        return moduleID;
    }

    public void setModuleID(long moduleID) {
        this.moduleID = moduleID;
    }

    // Getter and Setter for contents
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
