package com.example.vehiculos.resource;

import com.example.vehiculos.dto.LoginRequest;
import com.example.vehiculos.dto.LoginResponse;
import com.example.vehiculos.service.AuthService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
// import org.eclipse.microprofile.openapi.annotations.responses.ApiResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Autenticación", description = "Endpoints para autenticación JWT")
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve un token JWT")
    // @ApiResponse(responseCode = "200", description = "Login exitoso",
    //         content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class)))
    // @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    // @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    public Response login(@Valid LoginRequest loginRequest) {
        if (authService.validateCredentials(loginRequest.username, loginRequest.password)) {
            String token = authService.generateToken(loginRequest.username);
            LoginResponse response = new LoginResponse(token, loginRequest.username, 3600); // 1 hora
            return Response.ok(response).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Credenciales inválidas\"}")
                    .build();
        }
    }
}
