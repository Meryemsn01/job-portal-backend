package com.example.job_portal_backend.dto;

public class EmployerInfoResponse {
    private String companyName;
    private String sector;
    private String website;
    private String companyLogo;

    // Getters & Setters
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSector() {
        return sector;
    }
    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }
    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }
}
