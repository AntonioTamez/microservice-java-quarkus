package com.example.vehiculos.service;

import com.example.vehiculos.model.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AuthService {

    @ConfigProperty(name = "smallrye.jwt.sign.key")
    String jwtSigningKey;

    public String generateToken(String username) {
        Set<String> groups = new HashSet<>();
        groups.add("user");
        
        // Decode base64 key
        String decodedKey = new String(Base64.getDecoder().decode(jwtSigningKey));
        
        return Jwt.issuer("https://vehiculos-service.com")
                .upn(username)
                .groups(groups)
                .expiresAt(Instant.now().plus(Duration.ofHours(1)))
                .signWithSecret(decodedKey);
    }

    public boolean validateCredentials(String username, String password) {
        Usuario usuario = Usuario.findByUsername(username);
        if (usuario == null) {
            return false;
        }
        
        // Simple password validation - in production use BCrypt
        return "secret".equals(password);
    }
}
