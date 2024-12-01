package com.linkaster.feedbackService.service;

import org.springframework.stereotype.Service;

import com.linkaster.feedbackService.model.Feedback;
import com.linkaster.feedbackService.repository.FeedbackRepository;
// adds feedback to repository and sends feedback to instructor 

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
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
