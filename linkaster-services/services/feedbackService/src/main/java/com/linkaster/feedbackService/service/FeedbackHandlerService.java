package com.linkaster.feedbackService.service;

import org.springframework.stereotype.Service;

import com.linkaster.feedbackService.dto.FeedbackDTO;
import com.linkaster.feedbackService.model.Feedback;
import com.linkaster.feedbackService.repository.FeedbackRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

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

            boolean anon = false;
            if(feedback.getAnonymous().equals("True")){
                anon = true;
            }

            // Create feedback from DTO
            Feedback newFeedback = new Feedback();
            newFeedback.setRecipientID(Long.parseLong(feedback.getRecipientId()));
            newFeedback.setSenderID(Long.parseLong(feedback.getSenderId()));
            newFeedback.setAnonymous(anon);
            newFeedback.setModuleID(Long.parseLong(feedback.getModuleId()));
            newFeedback.setContents(feedback.getContents());

        

            feedbackRepository.save(newFeedback);
            log.info(log_header + "Feedback processed successfully!");
        } catch (Exception e) {
            log.info(log_header + "Error processing feedback: " + e.getMessage());
        }
    }

    /*
     * Insert methods here:
     */
}
