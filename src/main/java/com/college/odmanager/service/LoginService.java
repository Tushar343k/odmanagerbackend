package com.college.odmanager.service;

import com.college.odmanager.dto.LoginRequest;
import com.college.odmanager.dto.LoginResponse;
import com.college.odmanager.model.User;
import com.college.odmanager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        // Authenticate username and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Find authenticated user
        User user = userRepository.findByUsername(
                request.getUsername()
        ).orElseThrow(() ->
                new RuntimeException("User not found")
        );

        // Generate JWT
        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );

        // Return token + user information
        return new LoginResponse(
                token,
                user.getUsername(),
                user.getRole()
        );
    }
}