package com.example.job_portal_backend.dto;

public class JobRequest {
    private String title;
    private String description;
    private String location;
    private String category;
    private String contractType;
    private int experienceLevel;
    private Long employerId; // pour lier l'employeur

    public JobRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    @Override
    public String toString() {
        return "JobRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", category='" + category + '\'' +
                ", contractType='" + contractType + '\'' +
                ", experienceLevel=" + experienceLevel +
                ", employerId=" + employerId +
                '}';
    }
}
