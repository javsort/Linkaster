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
    private int recipientID;
    private int senderID;
    private boolean anonymous;
    private int moduleID;
    private String contents;
}
