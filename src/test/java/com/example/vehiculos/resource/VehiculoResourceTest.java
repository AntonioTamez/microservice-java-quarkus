package com.example.vehiculos.resource;

import com.example.vehiculos.model.Vehiculo;
import com.example.vehiculos.model.Transmision;
import com.example.vehiculos.model.Combustible;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class VehiculoResourceTest {

    @BeforeEach
    @Transactional
    void setUp() {
        // Clean up any existing test data
        Vehiculo.deleteAll();
    }

    @Test
    @JwtSecurity(claims = {
        @Claim(key = "upn", value = "testuser"),
        @Claim(key = "groups", value = "[\"user\"]")
    })
    void testGetVehiculos() {
        given()
            .when().get("/vehiculos")
            .then()
                .statusCode(200)
                .body("vehiculos", notNullValue())
                .body("total", notNullValue())
                .body("page", is(0))
                .body("size", is(10));
    }

    @Test
    @JwtSecurity(claims = {
        @Claim(key = "upn", value = "testuser"),
        @Claim(key = "groups", value = "[\"user\"]")
    })
    void testCreateVehiculo() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", 2023, new BigDecimal("25000.00"));
        vehiculo.color = "Blanco";
        vehiculo.transmision = Transmision.AUTOMATICA;
        vehiculo.combustible = Combustible.GASOLINA;
        vehiculo.puertas = 4;

        given()
            .contentType(ContentType.JSON)
            .body(vehiculo)
            .when().post("/vehiculos")
            .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("marca", is("Toyota"))
                .body("modelo", is("Corolla"))
                .body("anio", is(2023))
                .body("precio", is(25000.00f));
    }

    @Test
    @JwtSecurity(claims = {
        @Claim(key = "upn", value = "testuser"),
        @Claim(key = "groups", value = "[\"user\"]")
    })
    @Transactional
    void testGetVehiculoById() {
        // Create a vehicle first
        Vehiculo vehiculo = new Vehiculo("Honda", "Civic", 2022, new BigDecimal("28000.00"));
        vehiculo.persist();

        given()
            .when().get("/vehiculos/{id}", vehiculo.id)
            .then()
                .statusCode(200)
                .body("id", is(vehiculo.id.intValue()))
                .body("marca", is("Honda"))
                .body("modelo", is("Civic"));
    }

    @Test
    @JwtSecurity(claims = {
        @Claim(key = "upn", value = "testuser"),
        @Claim(key = "groups", value = "[\"user\"]")
    })
    void testGetVehiculoByIdNotFound() {
        given()
            .when().get("/vehiculos/999")
            .then()
                .statusCode(404);
    }

    @Test
    @JwtSecurity(claims = {
        @Claim(key = "upn", value = "testuser"),
        @Claim(key = "groups", value = "[\"user\"]")
    })
    @Transactional
    void testUpdateVehiculo() {
        // Create a vehicle first
        Vehiculo vehiculo = new Vehiculo("Ford", "Focus", 2021, new BigDecimal("22000.00"));
        vehiculo.persist();

        Vehiculo actualizado = new Vehiculo("Ford", "Focus", 2022, new BigDecimal("24000.00"));
        actualizado.color = "Rojo";
        actualizado.transmision = Transmision.MANUAL;

        given()
            .contentType(ContentType.JSON)
            .body(actualizado)
            .when().put("/vehiculos/{id}", vehiculo.id)
            .then()
                .statusCode(200)
                .body("anio", is(2022))
                .body("precio", is(24000.00f))
                .body("color", is("Rojo"));
    }

    @Test
    @JwtSecurity(claims = {
        @Claim(key = "upn", value = "testuser"),
        @Claim(key = "groups", value = "[\"user\"]")
    })
    @Transactional
    void testDeleteVehiculo() {
        // Create a vehicle first
        Vehiculo vehiculo = new Vehiculo("BMW", "X3", 2023, new BigDecimal("45000.00"));
        vehiculo.persist();

        given()
            .when().delete("/vehiculos/{id}", vehiculo.id)
            .then()
                .statusCode(204);

        // Verify it's deleted
        given()
            .when().get("/vehiculos/{id}", vehiculo.id)
            .then()
                .statusCode(404);
    }

    @Test
    void testUnauthorizedAccess() {
        given()
            .when().get("/vehiculos")
            .then()
                .statusCode(401);
    }

    @Test
    @JwtSecurity(claims = {
        @Claim(key = "upn", value = "testuser"),
        @Claim(key = "groups", value = "[\"user\"]")
    })
    void testCreateVehiculoValidationError() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.marca = ""; // Invalid - empty
        vehiculo.modelo = "Test";
        vehiculo.anio = 1800; // Invalid - too old
        vehiculo.precio = new BigDecimal("-1000"); // Invalid - negative

        given()
            .contentType(ContentType.JSON)
            .body(vehiculo)
            .when().post("/vehiculos")
            .then()
                .statusCode(400);
    }
}
