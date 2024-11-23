package com.linkaster.feedbackService.controller;

import com.linkaster.feedbackService.controller.FeedbackController;
import com.linkaster.feedbackService.model.FeedbackModel;
import com.linkaster.feedbackService.service.FeedbackAnonymizerService;
import com.linkaster.feedbackService.service.FeedbackHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackAnonymizerService feedbackAnonymizerService;
    private final FeedbackHandlerService feedbackHandlerService;

    @Autowired
    public FeedbackController(FeedbackAnonymizerService feedbackAnonymizerService, FeedbackHandlerService feedbackHandlerService) {
        this.feedbackAnonymizerService = feedbackAnonymizerService;
        this.feedbackHandlerService = feedbackHandlerService;
    }

    @PostMapping("/submit")
    public String handleFeedback(@RequestBody FeedbackModel feedback) {
        if (feedback.getAnonymous() && feedback.getSenderName() != "Anonymous") {   //if feedback needs to be anonymized 
            feedbackAnonymizerService.anonymizeFeedback(feedback);
        }
        feedbackHandlerService.processFeedback(feedback);
        return "Feedback processed successfully!";
    }
}
