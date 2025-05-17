package com.example.job_portal_backend.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.job_portal_backend.entity.JobSeeker;
import com.example.job_portal_backend.entity.User;
import com.example.job_portal_backend.exception.ResourceNotFoundException;
import com.example.job_portal_backend.repository.JobSeekerRepository;
import com.example.job_portal_backend.repository.UserRepository;

@RestController
@RequestMapping("/api/jobseekers")
public class JobSeekerController {

    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;

    public JobSeekerController(UserRepository userRepository, JobSeekerRepository jobSeekerRepository) {
        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @PutMapping("/profile")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<JobSeeker> updateProfile(@RequestBody JobSeeker updatedInfo, Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        JobSeeker jobSeeker = jobSeekerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Job Seeker profile not found"));

        jobSeeker.setFullName(updatedInfo.getFullName());
        jobSeeker.setPhone(updatedInfo.getPhone());
        jobSeeker.setEducation(updatedInfo.getEducation());
        jobSeeker.setExperience(updatedInfo.getExperience());
        jobSeeker.setSkills(updatedInfo.getSkills());
        jobSeeker.setCvFileUrl(updatedInfo.getCvFileUrl());

        return ResponseEntity.ok(jobSeekerRepository.save(jobSeeker));
    }
    

    @PostMapping("/cv/upload")
    @PreAuthorize("hasAuthority('ROLE_JOB_SEEKER')")
    public ResponseEntity<String> uploadCv(@RequestParam("file") MultipartFile file, Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        JobSeeker jobSeeker = jobSeekerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Job Seeker not found"));

        try {
            // Nom unique
            String fileName = "cv_" + user.getId() + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Chemin
            String uploadDir = "uploads/cv/";
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) uploadPath.mkdirs();

            // Sauvegarde du fichier
            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, file.getBytes());

            // Met à jour le job seeker avec l'URL
            String fileUrl = "http://localhost:8080/" + uploadDir + fileName;
            jobSeeker.setCvFileUrl(fileUrl);
            jobSeekerRepository.save(jobSeeker);

            return ResponseEntity.ok("CV uploaded successfully: " + fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }


}
