package com.example.job_portal_backend.dto;

import java.time.LocalDateTime;

public class AdminUserDto {
    private Long id;
    private String email;
    private String role;
    private LocalDateTime createdAt;

    public AdminUserDto(Long id, String email, String role, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters uniquement si besoin
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
