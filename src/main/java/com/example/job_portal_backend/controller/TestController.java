package com.example.job_portal_backend.controller;

import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@Validated  // 🔥 Clé indispensable !
public class TestController {

    @GetMapping("/validation")
    public ResponseEntity<String> validateName(
        @RequestParam @Size(min = 3, message = "Le nom doit contenir au moins 3 caractères") String name) {
        return ResponseEntity.ok("Valide");
    }
}
