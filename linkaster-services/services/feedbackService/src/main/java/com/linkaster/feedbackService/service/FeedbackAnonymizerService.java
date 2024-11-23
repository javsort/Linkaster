package com.linkaster.feedbackService.service;

import org.springframework.stereotype.Service;
import com.linkaster.feedbackService.model.FeedbackModel;


// changes the stored name to "anonymous"
@Service
public class FeedbackAnonymizerService {
    public void anonymizeFeedback(FeedbackModel feedback) {
        feedback.setSenderName("Anonymous");
    }
}
