/*
 *  Title: InstructorFeedbackRequest.java
 *  Author: Berenger, Marlene
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
package com.linkaster.feedbackService.dto;

public class InstructorModuleFeedbackRequest {
    private int instructorID;
    private int moduleID;

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

}
