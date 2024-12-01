package com.linkaster.feedbackService.controller;

import com.linkaster.feedbackService.controller.FeedbackController;
import com.linkaster.feedbackService.model.Feedback;
import com.linkaster.feedbackService.service.FeedbackAnonymizerService;
import com.linkaster.feedbackService.service.FeedbackHandlerService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
public class FeedbackController implements APIFeedbackController {

    private final FeedbackAnonymizerService feedbackAnonymizerService;
    private final FeedbackHandlerService feedbackHandlerService;

    @Autowired
    public FeedbackController(FeedbackAnonymizerService feedbackAnonymizerService, FeedbackHandlerService feedbackHandlerService) {
        this.feedbackAnonymizerService = feedbackAnonymizerService;
        this.feedbackHandlerService = feedbackHandlerService;
    }

    @Override
    public String handleFeedback(@RequestBody Feedback feedback) {
        if (feedback.getAnonymous() && feedback.getSenderID() != 0) {   //if feedback needs to be anonymized 
            feedbackAnonymizerService.anonymizeFeedback(feedback);
        }
        feedbackHandlerService.processFeedback(feedback);           // call process feedback
        return "Feedback processed successfully!";
    }
}
