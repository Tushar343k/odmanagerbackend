package com.college.odmanager.service;

import com.college.odmanager.dto.SignupRequest;
import com.college.odmanager.dto.SignupResponse;
import com.college.odmanager.model.User;
import com.college.odmanager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SignupResponse signup(SignupRequest request) {

        // Check username
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Check role
        if (!request.getRole().equals("ADMIN") &&
                !request.getRole().equals("USER")) {

            throw new RuntimeException("Invalid role");
        }

        // Only one account for each role
        if (userRepository.existsByRole(request.getRole())) {
            throw new RuntimeException(
                    "An account for this role already exists"
            );
        }

        // Create User entity
        User user = new User();

        user.setUsername(request.getUsername());
        user.setRole(request.getRole());
        user.setRecoveryEmail(request.getRecoveryEmail());

        // Encrypt password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Save user
        User savedUser = userRepository.save(user);

        // Return safe response
        return new SignupResponse(
                "Signup successful",
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }
}