package com.linkaster.feedbackService;


import com.linkaster.feedbackService.controller.FeedbackController;
import com.linkaster.feedbackService.service.FeedbackAnonymizerService;
import com.linkaster.feedbackService.service.FeedbackHandlerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import lombok.extern.slf4j.Slf4j;
import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Slf4j
class FeedbackControllerTest {

    private FeedbackAnonymizerService FeedbackAnonymizerService;


    @Test
    void demoTestMethod() {
        assertTrue(true);
    }
}
