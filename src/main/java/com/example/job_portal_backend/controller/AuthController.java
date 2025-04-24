package com.example.job_portal_backend.controller;

import com.example.job_portal_backend.dto.AuthRequest;
import com.example.job_portal_backend.dto.AuthResponse;
import com.example.job_portal_backend.dto.SignupRequest;
import com.example.job_portal_backend.entity.*;
import com.example.job_portal_backend.repository.*;
import com.example.job_portal_backend.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmployerRepository employerRepository;

    public AuthController(UserRepository userRepository,
                          RoleRepository roleRepository,
                          JobSeekerRepository jobSeekerRepository,
                          EmployerRepository employerRepository, // 🔥 ici
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.employerRepository = employerRepository; // 🔥 ici
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
    

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
    
        Role role = roleRepository.findByName(request.getRole().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found"));
    
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
    
        User savedUser = userRepository.save(user);
    
        if (role.getName().equalsIgnoreCase("JOB_SEEKER")) {
            JobSeeker jobSeeker = new JobSeeker();
            jobSeeker.setUser(savedUser);
            jobSeekerRepository.save(jobSeeker);
        } else if (role.getName().equalsIgnoreCase("EMPLOYER")) { // ✅ ici
            Employer employer = new Employer();
            employer.setUser(savedUser);
            employerRepository.save(employer);
        }
    
        return ResponseEntity.ok("User registered successfully");
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().getName())
                .build();

        String token = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token, user.getRole().getName(), user.getEmail()));
    }
}
