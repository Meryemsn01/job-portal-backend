package com.example.job_portal_backend.service.impl;

import org.springframework.stereotype.Service;

import com.example.job_portal_backend.dto.ApplicationRequest;
import com.example.job_portal_backend.entity.Application;
import com.example.job_portal_backend.entity.Job;
import com.example.job_portal_backend.entity.JobSeeker;
import com.example.job_portal_backend.repository.ApplicationRepository;
import com.example.job_portal_backend.repository.JobRepository;
import com.example.job_portal_backend.repository.JobSeekerRepository;
import com.example.job_portal_backend.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final JobSeekerRepository jobSeekerRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  JobRepository jobRepository,
                                  JobSeekerRepository jobSeekerRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @Override
    public Application applyToJob(ApplicationRequest request) {
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        JobSeeker seeker = jobSeekerRepository.findById(request.getJobSeekerId())
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));

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
