package com.example.job_portal_backend.service;

import java.util.List;

import com.example.job_portal_backend.dto.JobRequest;
import com.example.job_portal_backend.dto.JobResponse;

public interface JobService {
    List<JobResponse> getAllJobs();
    JobResponse getJobById(Long id);
    JobResponse createJob(JobRequest request);
    JobResponse updateJob(Long id, JobRequest request);
    void deleteJob(Long id);
    List<JobResponse> getJobsByEmployer(Long employerId);
    
    List<JobResponse> searchJobs(String keyword, String location, String category, String contractType, Integer experienceLevel);


}
