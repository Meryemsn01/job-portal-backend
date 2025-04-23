package com.example.job_portal_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employers")
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String companyName;
    private String companyLogo;
    private String sector;
    private String location;
    private String website;
    private String description;

    // --- Constructors ---

    public Employer() {
    }

    public Employer(Long id, User user, String companyName, String companyLogo,
                    String sector, String location, String website, String description) {
        this.id = id;
        this.user = user;
        this.companyName = companyName;
        this.companyLogo = companyLogo;
        this.sector = sector;
        this.location = location;
        this.website = website;
        this.description = description;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // --- toString ---

    @Override
    public String toString() {
        return "Employer{" +
                "id=" + id +
                ", user=" + user +
                ", companyName='" + companyName + '\'' +
                ", companyLogo='" + companyLogo + '\'' +
                ", sector='" + sector + '\'' +
                ", location='" + location + '\'' +
                ", website='" + website + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
