package com.example.job_portal_backend.dto;

public class JobSeekerProfileRequest {
    private String fullName;
    private String education;
    private String experience;
    private String phone;
    private String skills;
    private String cvFileUrl;

    public JobSeekerProfileRequest() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getCvFileUrl() {
        return cvFileUrl;
    }

    public void setCvFileUrl(String cvFileUrl) {
        this.cvFileUrl = cvFileUrl;
    }

    @Override
    public String toString() {
        return "JobSeekerProfileRequest{" +
                "fullName='" + fullName + '\'' +
                ", education='" + education + '\'' +
                ", experience='" + experience + '\'' +
                ", phone='" + phone + '\'' +
                ", skills='" + skills + '\'' +
                ", cvFileUrl='" + cvFileUrl + '\'' +
                '}';
    }
}
