package com.example.job_portal_backend.service;

import com.example.job_portal_backend.dto.ApplicationRequest;
import com.example.job_portal_backend.entity.Application;

public interface ApplicationService {
    Application applyToJob(ApplicationRequest request);
}

