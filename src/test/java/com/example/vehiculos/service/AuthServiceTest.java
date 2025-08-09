package com.example.vehiculos.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class AuthServiceTest {

    @Inject
    AuthService authService;

    @Test
    void testValidateCredentialsValid() {
        // When & Then
        assertTrue(authService.validateCredentials("admin", "secret"));
        assertTrue(authService.validateCredentials("user", "secret"));
    }

    @Test
    void testValidateCredentialsInvalid() {
        // When & Then
        assertFalse(authService.validateCredentials("admin", "wrong"));
        assertFalse(authService.validateCredentials("nonexistent", "secret"));
        assertFalse(authService.validateCredentials("admin", ""));
    }

    @Test
    void testGenerateToken() {
        // When
        String token = authService.generateToken("admin");

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));  // JWT tokens contain dots
    }
}
