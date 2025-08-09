package com.example.vehiculos.dto;

public class LoginResponse {

    public String token;
    public String username;
    public long expiresIn;

    // Default constructor
    public LoginResponse() {}

    // Constructor
    public LoginResponse(String token, String username, long expiresIn) {
        this.token = token;
        this.username = username;
        this.expiresIn = expiresIn;
    }
}
