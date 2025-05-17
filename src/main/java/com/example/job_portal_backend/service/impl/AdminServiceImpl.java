package com.example.job_portal_backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.job_portal_backend.dto.AdminUserDto;
import com.example.job_portal_backend.entity.User;
import com.example.job_portal_backend.exception.ResourceNotFoundException;
import com.example.job_portal_backend.repository.EmployerRepository;
import com.example.job_portal_backend.repository.JobSeekerRepository;
import com.example.job_portal_backend.repository.UserRepository;
import com.example.job_portal_backend.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;
    private final JobSeekerRepository jobSeekerRepository;

    public AdminServiceImpl(UserRepository userRepository, EmployerRepository employerRepository, JobSeekerRepository jobSeekerRepository) {
        this.userRepository = userRepository;
        this.employerRepository = employerRepository;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @Override
    public List<AdminUserDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(user -> new AdminUserDto(
                user.getId(),
                user.getEmail(),
                user.getRole().getName(),
                user.getCreatedAt()
            ))
            .toList();
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec ID " + id));

        // Supprimer les entités dépendantes
        if ("EMPLOYER".equals(user.getRole().getName())) {
            employerRepository.deleteByUser(user);
        } else if ("JOB_SEEKER".equals(user.getRole().getName())) {
            jobSeekerRepository.deleteByUser(user);
        }

        userRepository.delete(user);
    }
}
