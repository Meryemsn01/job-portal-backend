package com.example.job_portal_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/whoami")
    public ResponseEntity<String> whoAmI(Authentication authentication) {
        return ResponseEntity.ok("Email: " + authentication.getName() +
                " | Roles: " + authentication.getAuthorities());
    }
}
