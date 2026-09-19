package com.college.odmanager.controller;

import com.college.odmanager.dto.ForgotPasswordRequest;
import com.college.odmanager.dto.LoginRequest;
import com.college.odmanager.dto.LoginResponse;
import com.college.odmanager.dto.ResetPasswordRequest;
import com.college.odmanager.dto.SignupRequest;
import com.college.odmanager.dto.SignupResponse;

import com.college.odmanager.service.AuthService;
import com.college.odmanager.service.ForgotPasswordService;
import com.college.odmanager.service.LoginService;
import com.college.odmanager.service.ResetPasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://odmanagerfrontend.onrender.com"
})
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private ResetPasswordService resetPasswordService;


    // =========================
    // SIGNUP
    // =========================

    @PostMapping("/signup")
    public SignupResponse signup(
            @RequestBody SignupRequest request
    ) {

        return authService.signup(request);
    }


    // =========================
    // LOGIN
    // =========================

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {

        return loginService.login(request);
    }


    // =========================
    // FORGOT PASSWORD
    // =========================

    @PostMapping("/forgot-password")
    public Map<String, String> forgotPassword(
            @RequestBody ForgotPasswordRequest request
    ) {

        forgotPasswordService.sendResetLink(
                request.getRecoveryEmail()
        );

        return Map.of(
                "message",
                "Password reset link has been sent to your recovery email"
        );
    }


    // =========================
    // RESET PASSWORD
    // =========================

    @PostMapping("/reset-password")
    public Map<String, String> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {

        resetPasswordService.resetPassword(request);

        return Map.of(
                "message",
                "Password has been reset successfully"
        );
    }
}