package com.example.job_portal_backend.config;

import com.example.job_portal_backend.entity.Role;
import com.example.job_portal_backend.entity.User;
import com.example.job_portal_backend.repository.RoleRepository;
import com.example.job_portal_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class DataSeeder {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initRoles() {
        List<String> roles = List.of("JOB_SEEKER", "EMPLOYER", "ADMIN");

        for (String roleName : roles) {
            roleRepository.findByName(roleName).orElseGet(() -> {
                Role role = new Role();
                role.setName(roleName);
                return roleRepository.save(role);
            });
        }

        System.out.println("✅ Roles initialized in database");
    }

    @PostConstruct
    public void initAdmin() {
        Optional<User> adminExists = userRepository.findByEmail("admin@jobportal.com");

        if (adminExists.isEmpty()) {
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role ADMIN not found"));

            User admin = new User();
            admin.setEmail("admin@jobportal.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(adminRole);

            userRepository.save(admin);

            System.out.println("✅ Admin user created: admin@jobportal.com / admin123");
        }
    }
}
