package com.example.job_portal_backend.dto;

public class ApplicationRequest {
    private Long jobId;
    private String cvFileUrl; // optionnel pour l’instant

    public ApplicationRequest() {
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getCvFileUrl() {
        return cvFileUrl;
    }

    public void setCvFileUrl(String cvFileUrl) {
        this.cvFileUrl = cvFileUrl;
    }

    @Override
    public String toString() {
        return "ApplicationRequest{" +
                "jobId=" + jobId +
                ", cvFileUrl='" + cvFileUrl + '\'' +
                '}';
    }
}
