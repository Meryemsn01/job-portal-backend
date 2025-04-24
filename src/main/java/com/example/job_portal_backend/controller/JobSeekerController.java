package com.example.job_portal_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.job_portal_backend.entity.JobSeeker;
import com.example.job_portal_backend.entity.User;
import com.example.job_portal_backend.repository.JobSeekerRepository;
import com.example.job_portal_backend.repository.UserRepository;

@RestController
@RequestMapping("/api/jobseekers")
public class JobSeekerController {

    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;

    public JobSeekerController(UserRepository userRepository, JobSeekerRepository jobSeekerRepository) {
        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @PutMapping("/profile")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<JobSeeker> updateProfile(@RequestBody JobSeeker updatedInfo, Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobSeeker jobSeeker = jobSeekerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Job Seeker profile not found"));

        jobSeeker.setFullName(updatedInfo.getFullName());
        jobSeeker.setPhone(updatedInfo.getPhone());
        jobSeeker.setEducation(updatedInfo.getEducation());
        jobSeeker.setExperience(updatedInfo.getExperience());
        jobSeeker.setSkills(updatedInfo.getSkills());
        jobSeeker.setCvFileUrl(updatedInfo.getCvFileUrl());

        return ResponseEntity.ok(jobSeekerRepository.save(jobSeeker));
    }
    


}
