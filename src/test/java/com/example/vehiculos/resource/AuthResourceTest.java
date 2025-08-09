package com.example.vehiculos.resource;

import com.example.vehiculos.dto.LoginRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class AuthResourceTest {

    @Test
    void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest("admin", "secret");

        given()
            .contentType(ContentType.JSON)
            .body(loginRequest)
            .when().post("/auth/login")
            .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("username", is("admin"))
                .body("expiresIn", is(3600));
    }

    @Test
    void testLoginInvalidCredentials() {
        LoginRequest loginRequest = new LoginRequest("admin", "wrongpassword");

        given()
            .contentType(ContentType.JSON)
            .body(loginRequest)
            .when().post("/auth/login")
            .then()
                .statusCode(401)
                .body("error", is("Credenciales inválidas"));
    }

    @Test
    void testLoginInvalidUser() {
        LoginRequest loginRequest = new LoginRequest("nonexistent", "secret");

        given()
            .contentType(ContentType.JSON)
            .body(loginRequest)
            .when().post("/auth/login")
            .then()
                .statusCode(401)
                .body("error", is("Credenciales inválidas"));
    }

    @Test
    void testLoginValidationError() {
        LoginRequest loginRequest = new LoginRequest("", ""); // Empty fields

        given()
            .contentType(ContentType.JSON)
            .body(loginRequest)
            .when().post("/auth/login")
            .then()
                .statusCode(400);
    }
}
