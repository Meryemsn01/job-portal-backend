package com.example.job_portal_backend.repository;

import com.example.job_portal_backend.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
}
