package com.example.job_portal_backend.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.job_portal_backend.dto.ApplicationRequest;
import com.example.job_portal_backend.dto.ApplicationStatusUpdateRequest;
import com.example.job_portal_backend.entity.Application;
import com.example.job_portal_backend.entity.ApplicationStatus;
import com.example.job_portal_backend.entity.Employer;
import com.example.job_portal_backend.entity.Job;
import com.example.job_portal_backend.entity.JobSeeker;
import com.example.job_portal_backend.entity.User;
import com.example.job_portal_backend.exception.ResourceNotFoundException;
import com.example.job_portal_backend.repository.ApplicationRepository;
import com.example.job_portal_backend.repository.EmployerRepository;
import com.example.job_portal_backend.repository.JobRepository;
import com.example.job_portal_backend.repository.JobSeekerRepository;
import com.example.job_portal_backend.repository.UserRepository;
import com.example.job_portal_backend.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final ApplicationRepository applicationRepository;
    private final EmployerRepository employerRepository;

    public ApplicationServiceImpl(UserRepository userRepository, JobRepository jobRepository,
                                  JobSeekerRepository jobSeekerRepository, ApplicationRepository applicationRepository,
                                  EmployerRepository employerRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.applicationRepository = applicationRepository;
        this.employerRepository = employerRepository;
    }

    @Override
    public Application applyToJob(ApplicationRequest request, String email) {
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        JobSeeker seeker = jobSeekerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Job seeker profile not found"));

        boolean alreadyApplied = applicationRepository.existsByJobAndJobSeeker(job, seeker);
        if (alreadyApplied) {
            throw new IllegalStateException("Already applied to this job.");
        }

        Application application = new Application();
        application.setJob(job);
        application.setJobSeeker(seeker);
        application.setCvFileUrl(request.getCvFileUrl());

        return applicationRepository.save(application);
    }

    @Override
    public Application updateApplicationStatus(Long applicationId, ApplicationStatus newStatus) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        application.setStatus(newStatus);
        return applicationRepository.save(application);
    }

    @Override
    public void updateStatus(ApplicationStatusUpdateRequest request) {
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidature introuvable"));

        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        Employer employer = employerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employeur non trouvé"));

        if (!application.getJob().getEmployer().getId().equals(employer.getId())) {
            throw new IllegalStateException("Vous n'avez pas l'autorisation de modifier cette candidature.");
        }

        application.setStatus(request.getNewStatus());
        applicationRepository.save(application);
    }
}
