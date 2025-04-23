package com.example.job_portal_backend.service;

import com.example.job_portal_backend.dto.JobRequest;
import com.example.job_portal_backend.dto.JobResponse;

import java.util.List;

public interface JobService {
    List<JobResponse> getAllJobs();
    JobResponse getJobById(Long id);
    JobResponse createJob(JobRequest request);
    JobResponse updateJob(Long id, JobRequest request);
    void deleteJob(Long id);
    List<JobResponse> getJobsByEmployer(Long employerId);

}
