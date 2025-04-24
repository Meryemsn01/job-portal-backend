package com.example.job_portal_backend.repository;

import com.example.job_portal_backend.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional<Employer> findByUserEmail(String email);
    Optional<Employer> findByUserId(Long userId);


}
