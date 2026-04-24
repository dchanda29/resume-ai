package com.resumeai.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public String register(RegistrationRequest registrationRequest) {
        // Implement user registration logic here
        // e.g., save user to database, hash password
        return "User registered: " + registrationRequest.getEmail();
    }

    public String login(LoginRequest loginRequest) {
        // Implement user login logic here
        // e.g., validate credentials, generate JWT token
        return "User logged in: " + loginRequest.getEmailOrPhone();
    }
}
