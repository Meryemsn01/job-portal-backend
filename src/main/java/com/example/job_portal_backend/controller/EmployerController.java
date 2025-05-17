package com.example.job_portal_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.job_portal_backend.dto.ApplicationStatusUpdateRequest;
import com.example.job_portal_backend.dto.EmployerInfoResponse;
import com.example.job_portal_backend.dto.EmployerProfileRequest;
import com.example.job_portal_backend.dto.JobSeekerProfileSummary;
import com.example.job_portal_backend.entity.Application;
import com.example.job_portal_backend.entity.Employer;
import com.example.job_portal_backend.entity.Job;
import com.example.job_portal_backend.entity.User;
import com.example.job_portal_backend.exception.ResourceNotFoundException;
import com.example.job_portal_backend.repository.ApplicationRepository;
import com.example.job_portal_backend.repository.EmployerRepository;
import com.example.job_portal_backend.repository.JobRepository;
import com.example.job_portal_backend.repository.UserRepository;
import com.example.job_portal_backend.service.ApplicationService;
import com.example.job_portal_backend.service.EmployerService;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {


    private final EmployerRepository employerRepository; // ✅ Ajouté
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final EmployerService employerService;
    private final ApplicationService applicationService;

    public EmployerController(UserRepository userRepository,
                            JobRepository jobRepository,
                            ApplicationRepository applicationRepository,
                            EmployerRepository employerRepository,
                            EmployerService employerService,
                            ApplicationService applicationService) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.employerRepository = employerRepository;
        this.employerService = employerService;
        this.applicationService = applicationService; // ✅ Ajouté
    }

    

    @GetMapping("/applications")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    public ResponseEntity<List<Application>> getReceivedApplications(Authentication auth) {
        String email = auth.getName();
    
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    
        Employer employer = employerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employer not found"));
    
        List<Job> jobs = jobRepository.findByEmployerId(employer.getId());
    
        List<Application> applications = applicationRepository.findByJobIn(jobs);
    
        return ResponseEntity.ok(applications);
    }
    
    @GetMapping("/from-job/{jobId}")
public ResponseEntity<EmployerInfoResponse> getEmployerByJobId(@PathVariable Long jobId) {
    Job job = jobRepository.findById(jobId)
            .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

    Employer employer = job.getEmployer();
    if (employer == null) {
        throw new ResourceNotFoundException("Employer not found for this job");
    }

    EmployerInfoResponse response = new EmployerInfoResponse();
    response.setCompanyName(employer.getCompanyName());
    response.setSector(employer.getSector());
    response.setWebsite(employer.getWebsite());
    response.setCompanyLogo(employer.getCompanyLogo());

    return ResponseEntity.ok(response);
}

    // ✅ Mise à jour du profil entreprise
    @PutMapping("/profile")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Employer> updateProfile(@RequestBody EmployerProfileRequest request, Authentication auth) {
        String email = auth.getName(); // Récupère l'email du token JWT
        Employer updated = employerService.updateProfile(email, request);
        return ResponseEntity.ok(updated);
    }

        // Classe pour simplifier la réponse
        public record EmployerBasicInfo(String companyName, String sector, String website, String companyLogo) {}


        @PutMapping("/applications/status")
        @PreAuthorize("hasRole('EMPLOYER')")
        public ResponseEntity<?> updateApplicationStatus(@RequestBody ApplicationStatusUpdateRequest request) {
            applicationService.updateStatus(request);
            return ResponseEntity.ok("Statut mis à jour avec succès");
        }
        
        @GetMapping("/applications/{id}/candidate")
        @PreAuthorize("hasRole('EMPLOYER')")
        public ResponseEntity<JobSeekerProfileSummary> getCandidateDetails(@PathVariable Long id) {
            return ResponseEntity.ok(employerService.getCandidateProfileByApplicationId(id));
        }

    }

