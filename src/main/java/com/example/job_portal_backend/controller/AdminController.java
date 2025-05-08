package com.example.job_portal_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.job_portal_backend.dto.AdminUserDto;
import com.example.job_portal_backend.entity.User;
import com.example.job_portal_backend.repository.EmployerRepository;
import com.example.job_portal_backend.repository.JobSeekerRepository;
import com.example.job_portal_backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;
    private final JobSeekerRepository jobSeekerRepository;

    public AdminController(UserRepository userRepository, EmployerRepository employerRepository, JobSeekerRepository jobSeekerRepository) {
        this.userRepository = userRepository;
        this.employerRepository = employerRepository;
        this.jobSeekerRepository = jobSeekerRepository;
    }

@GetMapping("/users")
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

@DeleteMapping("/users/{id}")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Transactional
public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    Optional<User> userOpt = userRepository.findById(id);
    if (userOpt.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    User user = userOpt.get();

    // Supprimer le profil dépendant (Employer ou JobSeeker)
    if ("EMPLOYER".equals(user.getRole().getName())) {
        employerRepository.deleteByUser(user);
    } else if ("JOB_SEEKER".equals(user.getRole().getName())) {
        jobSeekerRepository.deleteByUser(user);
    }

    userRepository.delete(user);
    return ResponseEntity.noContent().build();
}

}
