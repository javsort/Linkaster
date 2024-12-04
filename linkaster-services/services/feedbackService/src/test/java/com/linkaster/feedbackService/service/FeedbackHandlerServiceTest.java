package com.linkaster.feedbackService.service;

import com.linkaster.feedbackService.model.Feedback;
import com.linkaster.feedbackService.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FeedbackHandlerServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackHandlerService feedbackHandlerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    // Check if feedback is saved to the repository
    @Test
    void testProcessFeedback() {
        Feedback feedback = new Feedback();
        feedbackHandlerService.processFeedback(feedback);
        verify(feedbackRepository, times(1)).save(feedback);
    }

    //check if you can get the feedback for a module
    @Test
    void testGetModuleFeedbacks() {
        int moduleId = 1;
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        List<Feedback> mockFeedbackList = Arrays.asList(feedback1, feedback2);

        when(feedbackRepository.getModuleFeedback(moduleId)).thenReturn(mockFeedbackList);

        List<Feedback> result = feedbackHandlerService.getModuleFeedbacks(moduleId);

        assertEquals(2, result.size());
        verify(feedbackRepository, times(1)).getModuleFeedback(moduleId);
    }
        //check if you can get the feedback for an instructor
    @Test
    void testGetInstructorFeedbacks() {
        int instructorId = 1;
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        List<Feedback> mockFeedbackList = Arrays.asList(feedback1, feedback2);

        when(feedbackRepository.getInstructorFeedback(instructorId)).thenReturn(mockFeedbackList);

        List<Feedback> result = feedbackHandlerService.getInstructorFeedbacks(instructorId);

        assertEquals(2, result.size());
        verify(feedbackRepository, times(1)).getInstructorFeedback(instructorId);
    }

    //check if you cn get feedbacks by instructor and module
    @Test
    void testGetInstructorModuleFeedbacks() {
        int instructorId = 1;
        int moduleId = 1;
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        List<Feedback> mockFeedbackList = Arrays.asList(feedback1, feedback2);

        when(feedbackRepository.getInstructorModuleFeedback(moduleId, instructorId)).thenReturn(mockFeedbackList);

        List<Feedback> result = feedbackHandlerService.getInstructorModuleFeedbacks(moduleId, instructorId);

        assertEquals(2, result.size());
        verify(feedbackRepository, times(1)).getInstructorModuleFeedback(moduleId, instructorId);
    }

    //check if you can get all feedbacks
    @Test
    void testGetAllFeedbacks() {
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        List<Feedback> mockFeedbackList = Arrays.asList(feedback1, feedback2);

        when(feedbackRepository.findAll()).thenReturn(mockFeedbackList);

        List<Feedback> result = feedbackHandlerService.getAllFeedbacks();

        assertEquals(2, result.size());
        verify(feedbackRepository, times(1)).findAll();
    }
}
