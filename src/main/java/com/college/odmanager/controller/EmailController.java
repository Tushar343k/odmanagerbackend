package com.college.odmanager.controller;

import com.college.odmanager.dto.EmailRequest;
import com.college.odmanager.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "http://localhost:3000")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {

        try {

            emailService.sendTestMail(request.getEmail());

            return ResponseEntity.ok("Email sent successfully!");

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body("Failed to send email: " + e.getMessage());
        }
    }
}