package com.example.job_portal_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.job_portal_backend.entity.Employer;
import com.example.job_portal_backend.entity.User;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional<Employer> findByUserEmail(String email);
    Optional<Employer> findByUserId(Long userId);
    void deleteByUser(User user);


}
