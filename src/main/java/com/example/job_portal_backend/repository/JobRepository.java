package com.example.job_portal_backend.repository;

import com.example.job_portal_backend.entity.Job;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.example.job_portal_backend.entity.User;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByTitleContainingIgnoreCase(String keyword);
    List<Job> findByEmployerId(Long employerId);
    List<Job> findByEmployer(User employer);

}


