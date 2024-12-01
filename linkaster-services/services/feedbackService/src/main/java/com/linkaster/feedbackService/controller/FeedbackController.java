package com.linkaster.feedbackService.controller;

import com.linkaster.feedbackService.controller.FeedbackController;
import com.linkaster.feedbackService.dto.FeedbackDTO;
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

    private final String log_header = "FeedbackController --- ";

    @Autowired
    public FeedbackController(FeedbackAnonymizerService feedbackAnonymizerService, FeedbackHandlerService feedbackHandlerService) {
        this.feedbackAnonymizerService = feedbackAnonymizerService;
        this.feedbackHandlerService = feedbackHandlerService;
    }

    @Override
    public String handleFeedback(@RequestBody FeedbackDTO feedback) {
        log.info(log_header + "Received feedback: " + feedback.toString());

        if (feedback.getAnonymous().equals("True")) {   //if feedback needs to be anonymized 
            log.info(log_header + "Feedback needs to be anonymized");
            feedbackAnonymizerService.anonymizeFeedback(feedback);
        }

        feedbackHandlerService.processFeedback(feedback);           // call process feedback
        log.info(log_header + "Feedback processed successfully!");
        return "Feedback processed successfully!";
    }
}
