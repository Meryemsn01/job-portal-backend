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
import com.example.job_portal_backend.exception.ResourceNotFoundException;
import com.example.job_portal_backend.exception.UnauthorizedActionException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        Employer employer = employerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Profil employeur non trouvé pour l'utilisateur ID : " + user.getId()));

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
                .orElseThrow(() -> new ResourceNotFoundException("Candidature introuvable avec l'ID : " + applicationId));

        String employerEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User employerUser = userRepository.findByEmail(employerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Employeur non trouvé avec l'email : " + employerEmail));

        Employer employer = employerRepository.findByUserId(employerUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Profil employeur introuvable pour l'utilisateur ID : " + employerUser.getId()));

        if (!app.getJob().getEmployer().getId().equals(employer.getId())) {
            throw new UnauthorizedActionException("Vous n'êtes pas autorisé à accéder à cette candidature.");
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
