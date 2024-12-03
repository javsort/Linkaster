package com.linkaster.feedbackService.service;

import org.springframework.stereotype.Service;

import com.linkaster.feedbackService.dto.FeedbackDTO;
import com.linkaster.feedbackService.model.Feedback;
import com.linkaster.feedbackService.repository.FeedbackRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Collections;


@Service
@Transactional
@Slf4j
public class FeedbackHandlerService {
    private final FeedbackRepository feedbackRepository;

    private final String log_header = "FeedbackHandlerService --- ";

    public FeedbackHandlerService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public void processFeedback(FeedbackDTO feedback) {
        try {

            log.info(log_header + "Processing feedback ...");


            // Create feedback from DTO
            Feedback newFeedback = new Feedback();
            newFeedback.setRecipientID(feedback.getRecipientID());
            newFeedback.setSenderID(feedback.getSenderID());
            newFeedback.setAnonymous(feedback.isAnonymous());
            newFeedback.setModuleID(feedback.getModuleID());
            newFeedback.setContents(feedback.getContents());

            feedbackRepository.save(newFeedback);
            log.info(log_header + "Feedback processed successfully!");
        } catch (Exception e) {
            log.info(log_header + "Error processing feedback: " + e.getMessage());
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
    /*
     * Insert methods here:
     */
}
