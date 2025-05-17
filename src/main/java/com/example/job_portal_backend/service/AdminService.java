package com.example.job_portal_backend.service;

import java.util.List;
import com.example.job_portal_backend.dto.AdminUserDto;

public interface AdminService {
    List<AdminUserDto> getAllUsers();
    void deleteUserById(Long id);
}
