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
    private String recipientId;
    private String senderId;
    private String anonymous;
    private String moduleId;
    private String contents;
}
