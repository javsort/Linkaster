package com.linkaster.feedbackService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping; // Ensure this import is present
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.linkaster.feedbackService.dto.FeedbackDTO;
import com.linkaster.feedbackService.dto.ModuleFeedbackRequest;
import com.linkaster.feedbackService.dto.InstructorFeedbackRequest;
import com.linkaster.feedbackService.dto.InstructorModuleFeedbackRequest;
import com.linkaster.feedbackService.model.Feedback;
import com.linkaster.feedbackService.service.FeedbackAnonymizerService;
import com.linkaster.feedbackService.service.FeedbackHandlerService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.linkaster.feedbackService.controller.FeedbackController;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.servlet.http.HttpServletRequest;



@Slf4j
@RestController
@RequestMapping("/api/feedback")
@SpringBootApplication
public class FeedbackController implements APIFeedbackController {

    private final FeedbackAnonymizerService feedbackAnonymizerService;
    private final FeedbackHandlerService feedbackHandlerService;

    private final String log_header = "FeedbackController --- ";

    @Autowired
    public FeedbackController(FeedbackAnonymizerService feedbackAnonymizerService, FeedbackHandlerService feedbackHandlerService) {
        this.feedbackAnonymizerService = feedbackAnonymizerService;
        this.feedbackHandlerService = feedbackHandlerService;
    }

    @PostMapping("/submitFeedback")
    public String handleFeedback(@RequestBody FeedbackDTO feedback) {
        log.info(log_header + "Received feedback: " + feedback.toString());

        if (feedback.isAnonymous()) {   //if feedback needs to be anonymized 
            log.info(log_header + "Feedback needs to be anonymized");
            feedbackAnonymizerService.anonymizeFeedback(feedback);
        }

        feedbackHandlerService.processFeedback(feedback);           // call process feedback
        log.info(log_header + "Feedback processed successfully!");
        return "Feedback processed successfully!";
    }

    @GetMapping("/getAllFeedbacks")
    public List<Feedback> getAllFeedbacks() {
        return feedbackHandlerService.getAllFeedbacks();
    }

    @GetMapping("/getModuleFeedbacks")
    public List<Feedback> getModuleFeedbacks(@RequestBody ModuleFeedbackRequest request) {
        return feedbackHandlerService.getModuleFeedbacks(request.getModuleID());
    }

    @GetMapping("/getInstructorFeedbacks")
    public List<Feedback> getInstructorFeedbacks(@RequestBody InstructorFeedbackRequest request) {
        return feedbackHandlerService.getInstructorFeedbacks(request.getInstructorID());
    }

    @GetMapping("/getInstructorModuleFeedbacks")
    public List<Feedback> getInstructorModuleFeedbacks(@RequestBody InstructorModuleFeedbackRequest request) {
        return feedbackHandlerService.getInstructorModuleFeedbacks(request.getModuleID(), request.getInstructorID());
    }
    /*
     * Insert method functionality
     * Method implementation of the ones delcared in APIFeedbackController.java
     */
}
