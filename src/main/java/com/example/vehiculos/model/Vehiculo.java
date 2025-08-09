package com.example.vehiculos.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehiculo")
public class Vehiculo extends PanacheEntity {

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no puede exceder 50 caracteres")
    @Column(nullable = false, length = 50)
    public String marca;

    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 50, message = "El modelo no puede exceder 50 caracteres")
    @Column(nullable = false, length = 50)
    public String modelo;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    @Max(value = 2030, message = "El año no puede ser mayor a 2030")
    @Column(nullable = false)
    public Integer anio;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 dígitos enteros y 2 decimales")
    @Column(nullable = false, precision = 12, scale = 2)
    public BigDecimal precio;

    @Size(max = 30, message = "El color no puede exceder 30 caracteres")
    @Column(length = 30)
    public String color;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    public Transmision transmision;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    public Combustible combustible;

    @Min(value = 1, message = "El número de puertas debe ser al menos 1")
    @Max(value = 10, message = "El número de puertas no puede ser mayor a 10")
    public Integer puertas;

    // Default constructor
    public Vehiculo() {}

    // Constructor with required fields
    public Vehiculo(String marca, String modelo, Integer anio, BigDecimal precio) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
    }
}
