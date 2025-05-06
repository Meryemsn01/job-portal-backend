package com.example.job_portal_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.job_portal_backend.dto.JobRequest;
import com.example.job_portal_backend.dto.JobResponse;
import com.example.job_portal_backend.service.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<JobResponse> createJob(@RequestBody JobRequest request) {
        return ResponseEntity.ok(jobService.createJob(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long id, @RequestBody JobRequest request) {
        return ResponseEntity.ok(jobService.updateJob(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employer/{employerId}")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ResponseEntity<List<JobResponse>> getJobsByEmployer(@PathVariable Long employerId) {
        return ResponseEntity.ok(jobService.getJobsByEmployer(employerId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobResponse>> searchJobs(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String location,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String contractType,
        @RequestParam(required = false) Integer experienceLevel
    ) {
        return ResponseEntity.ok(jobService.searchJobs(keyword, location, category, contractType, experienceLevel));
    }

}
