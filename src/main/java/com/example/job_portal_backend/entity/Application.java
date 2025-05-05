package com.example.job_portal_backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime applicationDate = LocalDateTime.now();
    private String cvFileUrl;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeeker jobSeeker;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING; // valeur par défaut


    // --- Constructors ---

    public Application() {
    }

public Application(Long id, LocalDateTime applicationDate, ApplicationStatus status,
                   String cvFileUrl, Job job, JobSeeker jobSeeker) {
    this.id = id;
    this.applicationDate = applicationDate;
    this.status = status;
    this.cvFileUrl = cvFileUrl;
    this.job = job;
    this.jobSeeker = jobSeeker;
}

public ApplicationStatus getStatus() {
    return status;
}

public void setStatus(ApplicationStatus status) {
    this.status = status;
}


    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatusString() {
        return status.toString();
    }

    public String getCvFileUrl() {
        return cvFileUrl;
    }

    public void setCvFileUrl(String cvFileUrl) {
        this.cvFileUrl = cvFileUrl;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    // --- toString ---

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", applicationDate=" + applicationDate +
                ", status='" + status + '\'' +
                ", cvFileUrl='" + cvFileUrl + '\'' +
                ", job=" + job +
                ", jobSeeker=" + jobSeeker +
                '}';
    }
}
