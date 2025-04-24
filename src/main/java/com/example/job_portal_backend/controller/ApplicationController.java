package com.example.job_portal_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.job_portal_backend.dto.ApplicationRequest;
import com.example.job_portal_backend.entity.Application;
import com.example.job_portal_backend.entity.Job;
import com.example.job_portal_backend.entity.JobSeeker;
import com.example.job_portal_backend.entity.User;
import com.example.job_portal_backend.repository.ApplicationRepository;
import com.example.job_portal_backend.repository.JobRepository;
import com.example.job_portal_backend.repository.JobSeekerRepository;
import com.example.job_portal_backend.repository.UserRepository;
import com.example.job_portal_backend.service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final ApplicationService applicationService;
    private final JobRepository jobRepository;
    

    public ApplicationController(
    ApplicationService applicationService,
    UserRepository userRepository,
    JobSeekerRepository jobSeekerRepository,
    ApplicationRepository applicationRepository,
    JobRepository jobRepository
) {
    this.applicationService = applicationService;
    this.userRepository = userRepository;
    this.jobSeekerRepository = jobSeekerRepository;
    this.applicationRepository = applicationRepository;
    this.jobRepository = jobRepository;
}


@PostMapping
@PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
public ResponseEntity<Application> applyToJob(@RequestBody ApplicationRequest request, Authentication auth) {
    String email = auth.getName();
    return ResponseEntity.ok(applicationService.applyToJob(request, email));
}


    @GetMapping("/my")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<List<Application>> getMyApplications(Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobSeeker jobSeeker = jobSeekerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Job Seeker profile not found"));

        List<Application> apps = applicationRepository.findByJobSeeker(jobSeeker);
        return ResponseEntity.ok(apps);
}
// ✅ NOUVELLE MÉTHODE POUR VÉRIFIER SI DÉJÀ POSTULÉ
    @GetMapping("/check")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<Boolean> hasAlreadyApplied(@RequestParam Long jobId, Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobSeeker jobSeeker = jobSeekerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        boolean alreadyApplied = applicationRepository.existsByJobAndJobSeeker(job, jobSeeker);
        return ResponseEntity.ok(alreadyApplied);
    }

}
