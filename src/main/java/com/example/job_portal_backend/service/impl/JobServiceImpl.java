package com.example.job_portal_backend.service.impl;

import com.example.job_portal_backend.dto.JobRequest;
import com.example.job_portal_backend.dto.JobResponse;
import com.example.job_portal_backend.entity.Employer;
import com.example.job_portal_backend.entity.Job;
import com.example.job_portal_backend.repository.EmployerRepository;
import com.example.job_portal_backend.repository.JobRepository;
import com.example.job_portal_backend.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;

    public JobServiceImpl(JobRepository jobRepository, EmployerRepository employerRepository) {
        this.jobRepository = jobRepository;
        this.employerRepository = employerRepository;
    }

    @Override
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return mapToResponse(job);
    }

    @Override
    public JobResponse createJob(JobRequest request) {
        Employer employer = employerRepository.findById(request.getEmployerId())
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setCategory(request.getCategory());
        job.setContractType(request.getContractType());
        job.setExperienceLevel(request.getExperienceLevel());
        job.setEmployer(employer);

        return mapToResponse(jobRepository.save(job));
    }

    @Override
    public JobResponse updateJob(Long id, JobRequest request) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setCategory(request.getCategory());
        job.setContractType(request.getContractType());
        job.setExperienceLevel(request.getExperienceLevel());

        return mapToResponse(jobRepository.save(job));
    }

    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public List<JobResponse> getJobsByEmployer(Long employerId) {
        return jobRepository.findByEmployerId(employerId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private JobResponse mapToResponse(Job job) {
        JobResponse response = new JobResponse();
        response.setId(job.getId());
        response.setTitle(job.getTitle());
        response.setDescription(job.getDescription());
        response.setLocation(job.getLocation());
        response.setCategory(job.getCategory());
        response.setContractType(job.getContractType());
        response.setExperienceLevel(job.getExperienceLevel());
        response.setEmployerName(job.getEmployer().getCompanyName());
        return response;
    }
}
