package com.linkaster.feedbackService.service;

import org.springframework.stereotype.Service;
import com.linkaster.feedbackService.model.Feedback;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


// changes the stored id to 0, indicating anonymous student

@Service
@Transactional
@Slf4j
public class FeedbackAnonymizerService {
    public void anonymizeFeedback(Feedback feedback) {
        feedback.setSenderID(0);
    }
}
