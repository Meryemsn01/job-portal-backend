package com.example.job_portal_backend.controller;

import com.example.job_portal_backend.entity.Application;
import com.example.job_portal_backend.entity.Employer;
import com.example.job_portal_backend.entity.Job;
import com.example.job_portal_backend.repository.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.job_portal_backend.entity.User;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {


    private final EmployerRepository employerRepository; // ✅ Ajouté
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public EmployerController(UserRepository userRepository, JobRepository jobRepository, ApplicationRepository applicationRepository, EmployerRepository employerRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.employerRepository = employerRepository;
    }
    

    @GetMapping("/applications")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ResponseEntity<List<Application>> getReceivedApplications(Authentication auth) {
        String email = auth.getName();
    
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        Employer employer = employerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Employer not found"));
    
        List<Job> jobs = jobRepository.findByEmployerId(employer.getId());
    
        List<Application> applications = applicationRepository.findByJobIn(jobs);
    
        return ResponseEntity.ok(applications);
    }
    
    
}
