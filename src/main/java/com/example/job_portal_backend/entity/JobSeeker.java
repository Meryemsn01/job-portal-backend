package com.example.job_portal_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_seekers")
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String fullName;
    private String phone;
    private String skills;
    private String education;
    private String experience;
    private String cvFileUrl;

    // --- Constructors ---

    public JobSeeker() {
    }

    public JobSeeker(Long id, User user, String fullName, String phone, String skills,
                     String education, String experience, String cvFileUrl) {
        this.id = id;
        this.user = user;
        this.fullName = fullName;
        this.phone = phone;
        this.skills = skills;
        this.education = education;
        this.experience = experience;
        this.cvFileUrl = cvFileUrl;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCvFileUrl() {
        return cvFileUrl;
    }

    public void setCvFileUrl(String cvFileUrl) {
        this.cvFileUrl = cvFileUrl;
    }

    // --- toString ---

    @Override
    public String toString() {
        return "JobSeeker{" +
                "id=" + id +
                ", user=" + user +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", skills='" + skills + '\'' +
                ", education='" + education + '\'' +
                ", experience='" + experience + '\'' +
                ", cvFileUrl='" + cvFileUrl + '\'' +
                '}';
    }
}
