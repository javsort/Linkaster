package com.linkaster.feedbackService.service;

import org.springframework.stereotype.Service;
import com.linkaster.feedbackService.model.Feedback;


// changes the stored id to 0, indicating anonymous student
@Service
public class FeedbackAnonymizerService {
    public void anonymizeFeedback(Feedback feedback) {
        feedback.setSenderID(0);
    }
}
