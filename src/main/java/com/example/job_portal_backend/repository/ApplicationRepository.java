package com.example.job_portal_backend.repository;

import com.example.job_portal_backend.entity.Application;
import com.example.job_portal_backend.entity.Job;
import com.example.job_portal_backend.entity.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByJob(Job job);
    List<Application> findByJobSeeker(JobSeeker jobSeeker);
    boolean existsByJobAndJobSeeker(Job job, JobSeeker jobSeeker);

}
