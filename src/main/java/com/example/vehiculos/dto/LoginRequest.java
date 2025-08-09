package com.example.vehiculos.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "El username es obligatorio")
    public String username;

    @NotBlank(message = "La contraseña es obligatoria")
    public String password;

    // Default constructor
    public LoginRequest() {}

    // Constructor
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
