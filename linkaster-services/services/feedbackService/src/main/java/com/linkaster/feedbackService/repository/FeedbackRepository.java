/*
 *  Title: FeedbackRepository.java
 *  Author: Berenger, Marlene
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
package com.linkaster.feedbackService.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.linkaster.feedbackService.model.Feedback;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

 @Query("SELECT f FROM Feedback f WHERE f.recipientID = :instructorID")
List<Feedback> getInstructorFeedback(@Param("instructorID") int instructorID);

@Query("SELECT f FROM Feedback f WHERE f.moduleID = :moduleID")
List<Feedback> getModuleFeedback(@Param("moduleID") int moduleID);

@Query("SELECT f FROM Feedback f WHERE f.moduleID = :moduleID AND f.recipientID = :instructorID")
List<Feedback> getInstructorModuleFeedback(@Param("moduleID") int moduleID, @Param("instructorID") int instructorID);

@Query("SELECT f FROM Feedback f")
List<Feedback> feedbackGetter();

}
