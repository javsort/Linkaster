package com.linkaster.feedbackService.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.linkaster.feedbackService.model.Feedback;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    // get feedback by instructor name only
    @Query("SELECT f FROM Feedback f WHERE f.recipientID = :instructorID")
    List<Feedback> getInstructorFeedback(@Param("instructorID") int instructorID);

    // get feedback by module name only
    @Query("SELECT f FROM Feedback f WHERE f.moduleID = :moduleID")
    List<Feedback> getModuleFeedback(@Param("moduleID") String moduleID);

    //get feedback by module and instructor 
    @Query("SELECT f FROM Feedback f WHERE f.moduleID = :moduleID AND f.recipientID = :instructorID")
    List<Feedback> getInstructorModuleFeedback(@Param("moduleID") String moduleID, @Param("instructorID") String instructorID);
}
