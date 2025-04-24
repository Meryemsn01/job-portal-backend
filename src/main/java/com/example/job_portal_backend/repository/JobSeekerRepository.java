package com.example.job_portal_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.job_portal_backend.entity.JobSeeker;
import com.example.job_portal_backend.entity.User;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {
    Optional<JobSeeker> findByUser(User user);
    
}

