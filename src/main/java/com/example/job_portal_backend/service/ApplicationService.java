package com.example.job_portal_backend.service;

import com.example.job_portal_backend.dto.ApplicationRequest;
import com.example.job_portal_backend.dto.ApplicationStatusUpdateRequest;
import com.example.job_portal_backend.entity.Application;
import com.example.job_portal_backend.entity.ApplicationStatus;

public interface ApplicationService {
    Application applyToJob(ApplicationRequest request, String email);

    // ✅ Nouvelle méthode pour mettre à jour le statut
    Application updateApplicationStatus(Long applicationId, ApplicationStatus newStatus);

    void updateStatus(ApplicationStatusUpdateRequest request);

}
