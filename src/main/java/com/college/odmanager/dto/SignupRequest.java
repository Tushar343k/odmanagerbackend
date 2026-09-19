package com.college.odmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    private String username;

    private String password;

    private String role;

    private String recoveryEmail;
}