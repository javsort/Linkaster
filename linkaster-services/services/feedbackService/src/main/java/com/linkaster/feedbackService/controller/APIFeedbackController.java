/*
 *  Title: APIFeedbackController.java
 *  Author: Berenger, Marlene
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
package com.linkaster.feedbackService.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkaster.feedbackService.dto.FeedbackDTO;

@RequestMapping("/api/feedback")
public interface APIFeedbackController {
    
    @PostMapping("/submit")
    public String handleFeedback(@RequestBody FeedbackDTO feedback);
}
