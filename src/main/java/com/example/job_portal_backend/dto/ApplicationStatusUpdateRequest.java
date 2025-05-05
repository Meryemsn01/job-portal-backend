package com.example.job_portal_backend.dto;

import com.example.job_portal_backend.entity.ApplicationStatus;

public class ApplicationStatusUpdateRequest {
    private Long applicationId;
    private ApplicationStatus newStatus;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public ApplicationStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(ApplicationStatus newStatus) {
        this.newStatus = newStatus;
    }
}
