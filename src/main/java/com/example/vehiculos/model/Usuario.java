package com.example.vehiculos.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario extends PanacheEntity {

    @NotBlank(message = "El username es obligatorio")
    @Size(max = 50, message = "El username no puede exceder 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    public String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(max = 255, message = "La contraseña no puede exceder 255 caracteres")
    @Column(nullable = false, length = 255)
    public String password;

    // Default constructor
    public Usuario() {}

    // Constructor
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Usuario findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
