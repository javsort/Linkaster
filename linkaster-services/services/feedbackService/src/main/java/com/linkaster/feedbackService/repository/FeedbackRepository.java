package com.linkaster.feedbackService.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.linkaster.feedbackService.model.FeedbackModel;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackModel, Long> {
    
    // get feedback by instructor name only
    @Query("SELECT f FROM FeedbackModel f WHERE f.recipientName = :instructorName")
    List<FeedbackModel> getInstructorFeedback(@Param("instructorName") String instructorName);

    // get feedback by module name only
    @Query("SELECT f FROM FeedbackModel f WHERE f.recipientModule = :moduleName")
    List<FeedbackModel> getModuleFeedback(@Param("moduleName") String moduleName);

    //get feedback by module and instructor 
    @Query("SELECT f FROM FeedbackModel f WHERE f.recipientModule = :moduleName AND f.recipientName = :instructorName")
    List<FeedbackModel> getInstructorModuleFeedback(@Param("moduleName") String moduleName, @Param("instructorName") String instructorName);
}
