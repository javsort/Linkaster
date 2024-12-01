package com.linkaster.feedbackService.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkaster.feedbackService.model.Feedback;

@RequestMapping("/api/feedback")
public interface APIFeedbackController {
    
    @PostMapping("/submit")
    public String handleFeedback(@RequestBody Feedback feedback);
}
