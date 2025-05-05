package com.example.job_portal_backend.service;

import java.util.Arrays;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.job_portal_backend.dto.EmployerProfileRequest;
import com.example.job_portal_backend.dto.JobSeekerProfileSummary;
import com.example.job_portal_backend.entity.Application;
import com.example.job_portal_backend.entity.Employer;
import com.example.job_portal_backend.entity.JobSeeker;
import com.example.job_portal_backend.entity.User;
import com.example.job_portal_backend.repository.ApplicationRepository;
import com.example.job_portal_backend.repository.EmployerRepository;
import com.example.job_portal_backend.repository.UserRepository;



@Service
public class EmployerService {

    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;
    private final ApplicationRepository applicationRepository;

    public EmployerService(UserRepository userRepository,
                           EmployerRepository employerRepository,
                           ApplicationRepository applicationRepository) {
        this.userRepository = userRepository;
        this.employerRepository = employerRepository;
        this.applicationRepository = applicationRepository;
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

    public JobSeekerProfileSummary getCandidateProfileByApplicationId(Long applicationId) {
        Application app = applicationRepository.findById(applicationId)
    .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        String employerEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User employerUser = userRepository.findByEmail(employerEmail)
            .orElseThrow(() -> new RuntimeException("Employeur introuvable"));

        Employer employer = employerRepository.findByUserId(employerUser.getId())
            .orElseThrow(() -> new RuntimeException("Profil employeur introuvable"));

        // Vérifier si cette application appartient à un job de cet employeur
        if (!app.getJob().getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("Accès non autorisé à cette candidature");
        }


        JobSeeker js = app.getJobSeeker();

        JobSeekerProfileSummary dto = new JobSeekerProfileSummary();
        dto.setFullName(js.getFullName());
        dto.setEmail(js.getUser().getEmail());
        dto.setCvFileUrl(app.getCvFileUrl());

        dto.setSkills(Arrays.asList(js.getSkills().split(",")));

        dto.setExperiences(Arrays.asList(js.getExperience().split(",")));
        dto.setEducation(Arrays.asList(js.getEducation().split(",")));
        

        return dto;
    }
}
