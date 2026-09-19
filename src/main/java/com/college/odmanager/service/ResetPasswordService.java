package com.college.odmanager.service;

import com.college.odmanager.dto.ResetPasswordRequest;
import com.college.odmanager.model.User;
import com.college.odmanager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ResetPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void resetPassword(ResetPasswordRequest request) {

        // Find account using reset token
        User user = userRepository.findAll()
                .stream()
                .filter(u ->
                        request.getToken().equals(u.getResetToken())
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Invalid reset token")
                );

        // Check token expiry
        if (user.getResetTokenExpiry() == null ||
                user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {

            throw new RuntimeException("Reset token has expired");
        }

        // Encrypt new password
        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        // Clear reset token
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        // Save new password
        userRepository.save(user);
    }
}