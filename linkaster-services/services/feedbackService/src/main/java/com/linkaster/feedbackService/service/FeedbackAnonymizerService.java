package com.linkaster.feedbackService.service;

import org.springframework.stereotype.Service;

import com.linkaster.feedbackService.dto.FeedbackDTO;
import com.linkaster.feedbackService.model.Feedback;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


// changes the stored id to 0, indicating anonymous student

@Service
@Transactional
@Slf4j
public class FeedbackAnonymizerService {

    private final String log_header = "FeedbackAnonymizerService --- ";

    public void anonymizeFeedback(FeedbackDTO feedback) {
        log.info(log_header + "Anonymizing feedback ...");
        feedback.setSenderId("0");
        log.info(log_header + "Feedback anonymized successfully!");
    }
}
