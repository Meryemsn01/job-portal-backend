package com.example.job_portal_backend.service;

import org.springframework.stereotype.Service;

import com.example.job_portal_backend.dto.EmployerProfileRequest;
import com.example.job_portal_backend.entity.Employer;
import com.example.job_portal_backend.entity.User;
import com.example.job_portal_backend.repository.EmployerRepository;
import com.example.job_portal_backend.repository.UserRepository;

@Service
public class EmployerService {

    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;

    public EmployerService(UserRepository userRepository, EmployerRepository employerRepository) {
        this.userRepository = userRepository;
        this.employerRepository = employerRepository;
    }

    public Employer updateProfile(String email, EmployerProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employer employer = employerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        employer.setCompanyName(request.getCompanyName());
        employer.setCompanyLogo(request.getCompanyLogo());
        employer.setDescription(request.getDescription());
        employer.setLocation(request.getLocation());
        employer.setSector(request.getSector());
        employer.setWebsite(request.getWebsite());

        return employerRepository.save(employer);
    }
}
