package com.linkaster.feedbackService.service;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.feedbackService.model.Feedback;
import com.linkaster.feedbackService.repository.FeedbackRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
// adds feedback to repository and sends feedback to instructor 
@Service
@Transactional
@Slf4j
public class FeedbackHandlerService {
    private final FeedbackRepository feedbackRepository;
    @Autowired
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

    public List<Feedback> getModuleFeedbacks(int moduleID) {
        try {
            return feedbackRepository.getModuleFeedback(moduleID); 
        } catch (Exception e) {
            System.out.println("Error getting module feedbacks: " + e.getMessage());
            return Collections.emptyList(); 
        }
    }

    public List<Feedback> getInstructorFeedbacks(int instructorID) {
        try {
            return feedbackRepository.getInstructorFeedback(instructorID); 
        } catch (Exception e) {
            System.out.println("Error getting instructor feedbacks: " + e.getMessage());
            return Collections.emptyList(); 
        }
    }

    public List<Feedback> getInstructorModuleFeedbacks(int moduleID, int instructorID) {
        try {
            return feedbackRepository.getInstructorModuleFeedback(moduleID, instructorID); 
        } catch (Exception e) {
            System.out.println("Error getting all feedbacks: " + e.getMessage());
            return Collections.emptyList(); 
        }
    }
    
    public List<Feedback> getAllFeedbacks() {
        try {
            return feedbackRepository.findAll(); 
        } catch (Exception e) {
            System.out.println("Error getting all feedbacks: " + e.getMessage());
            return Collections.emptyList(); 
        }
    }
}
