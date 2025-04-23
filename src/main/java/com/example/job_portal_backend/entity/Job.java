package com.example.job_portal_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private String category;
    private String contractType;
    private Double salary;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String status = "ACTIVE";
    private int experienceLevel;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private Employer employer;

    // --- Constructors ---

    public Job() {
    }

    public Job(Long id, String title, String description, String location, String category,
               String contractType, Double salary, LocalDateTime createdAt,
               String status, int experienceLevel, Employer employer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.category = category;
        this.contractType = contractType;
        this.salary = salary;
        this.createdAt = createdAt;
        this.status = status;
        this.experienceLevel = experienceLevel;
        this.employer = employer;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    // --- toString ---

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", category='" + category + '\'' +
                ", contractType='" + contractType + '\'' +
                ", salary=" + salary +
                ", createdAt=" + createdAt +
                ", status='" + status + '\'' +
                ", experienceLevel=" + experienceLevel +
                ", employer=" + employer +
                '}';
    }
}
