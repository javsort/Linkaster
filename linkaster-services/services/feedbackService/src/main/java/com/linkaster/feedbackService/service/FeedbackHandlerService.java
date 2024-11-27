package com.linkaster.feedbackService.service;

import com.linkaster.feedbackService.model.Feedback;
import com.linkaster.feedbackService.repository.FeedbackRepository;
// adds feedback to repository and sends feedback to instructor 
public class FeedbackHandlerService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackHandlerService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public void processFeedback(Feedback feedback) {
        try {
            feedbackRepository.save(feedback);
            System.out.println("Feedback processed successfully!");
        } catch (Exception e) {
            System.out.println("Error processing feedback: " + e.getMessage());
        }
    }
}
