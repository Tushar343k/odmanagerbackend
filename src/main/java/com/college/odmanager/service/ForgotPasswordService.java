package com.college.odmanager.service;

import com.college.odmanager.model.User;
import com.college.odmanager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetLink(String recoveryEmail) {

        // Find account by recovery email
        User user = userRepository.findAll()
                .stream()
                .filter(u -> u.getRecoveryEmail().equalsIgnoreCase(recoveryEmail))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "No account found with this recovery email"
                        )
                );

        // Generate secure random token
        String token = UUID.randomUUID().toString();

        // Save token and expiry
        user.setResetToken(token);
        user.setResetTokenExpiry(
                LocalDateTime.now().plusMinutes(15)
        );

        userRepository.save(user);

        // Reset link
        String resetLink =
                "https://odmanagerfrontend.onrender.com/reset-password?token="
                        + token;

        // Create email
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getRecoveryEmail());

        message.setSubject("OD Management System - Password Reset");

        message.setText(
                "Hello,\n\n"
                        + "A password reset request was made for your "
                        + user.getRole()
                        + " account.\n\n"
                        + "Click the link below to reset the password:\n\n"
                        + resetLink
                        + "\n\n"
                        + "This link will expire in 15 minutes.\n\n"
                        + "If you did not request this, you can ignore this email."
        );

        // Send email
        mailSender.send(message);
    }
}