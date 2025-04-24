package com.example.job_portal_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.job_portal_backend.entity.Application;
import com.example.job_portal_backend.entity.Job;
import com.example.job_portal_backend.entity.JobSeeker;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByJob(Job job);
    List<Application> findByJobSeeker(JobSeeker jobSeeker);
    boolean existsByJobAndJobSeeker(Job job, JobSeeker jobSeeker);
    List<Application> findByJobIn(List<Job> jobs);

    

}
