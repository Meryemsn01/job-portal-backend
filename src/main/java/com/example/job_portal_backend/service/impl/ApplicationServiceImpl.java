package com.example.job_portal_backend.service.impl;

import com.example.job_portal_backend.dto.ApplicationRequest;
import com.example.job_portal_backend.entity.*;
import com.example.job_portal_backend.repository.*;
import com.example.job_portal_backend.service.ApplicationService;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(UserRepository userRepository, JobRepository jobRepository,
                                  JobSeekerRepository jobSeekerRepository, ApplicationRepository applicationRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
public Application applyToJob(ApplicationRequest request, String email) {
    Job job = jobRepository.findById(request.getJobId())
            .orElseThrow(() -> new RuntimeException("Job not found"));

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    JobSeeker seeker = jobSeekerRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Job seeker profile not found"));

    boolean alreadyApplied = applicationRepository.existsByJobAndJobSeeker(job, seeker);
    if (alreadyApplied) {
        throw new RuntimeException("Already applied to this job.");
    }

    Application application = new Application();
    application.setJob(job);
    application.setJobSeeker(seeker);
    application.setCvFileUrl(request.getCvFileUrl());

    return applicationRepository.save(application);
}

}
