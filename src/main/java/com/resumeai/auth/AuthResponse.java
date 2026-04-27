package com.resumeai.auth;

public class AuthResponse {
    private String token;
    private Long id;
    private String name;
    private String email;
    private String phone;

    public AuthResponse(String token, Long id, String name, String email, String phone) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getToken() { return token; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}