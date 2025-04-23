package com.example.job_portal_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.job_portal_backend.dto.ApplicationRequest;
import com.example.job_portal_backend.entity.Application;
import com.example.job_portal_backend.service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<Application> applyToJob(@RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(applicationService.applyToJob(request));
    }
}
