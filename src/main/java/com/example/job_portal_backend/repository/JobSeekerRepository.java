package com.example.job_portal_backend.repository;

import com.example.job_portal_backend.entity.JobSeeker;
import com.example.job_portal_backend.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {
    Optional<JobSeeker> findByUser(User user);

}
