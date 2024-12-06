/*
 *  Title: FeedbackAnonymizerService.java
 *  Author: Berenger, Marlene
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
package com.linkaster.feedbackService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linkaster.feedbackService.model.Feedback;
import com.linkaster.feedbackService.repository.FeedbackRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkaster.feedbackService.model.Feedback;
import com.linkaster.feedbackService.repository.FeedbackRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class FeedbackAnonymizerService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackAnonymizerService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Changes the sender ID to 0 to indicate anonymous feedback.
     *
     * @param feedback The feedback object to anonymize.
     */
    public void anonymizeFeedback(Feedback feedback) {
        feedback.setSenderID(0);
        // Optionally save the feedback if modifications need to be persisted
        feedbackRepository.save(feedback);
        log.debug("Feedback anonymized and saved: {}", feedback);
    }
}
