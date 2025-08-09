package com.example.vehiculos.resource;

import com.example.vehiculos.model.Vehiculo;
import com.example.vehiculos.service.VehiculoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
// import org.eclipse.microprofile.openapi.annotations.responses.ApiResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/vehiculos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
// @RolesAllowed("user") // Temporarily disabled for testing
// @SecurityRequirement(name = "jwt") // Temporarily disabled for testing
@Tag(name = "Vehículos", description = "Gestión del catálogo de vehículos")
public class VehiculoResource {

    @Inject
    VehiculoService vehiculoService;

    @GET
    @Operation(summary = "Listar vehículos", description = "Obtiene una lista paginada de vehículos con filtros opcionales")
    // @ApiResponse(responseCode = "200", description = "Lista de vehículos obtenida exitosamente")
    // @ApiResponse(responseCode = "401", description = "No autorizado")
    public Response getVehiculos(
            @Parameter(description = "Número de página (empezando en 0)") @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Tamaño de página") @QueryParam("size") @DefaultValue("10") int size,
            @Parameter(description = "Campo para ordenar") @QueryParam("sortBy") @DefaultValue("id") String sortBy,
            @Parameter(description = "Filtro por marca") @QueryParam("marca") String marca,
            @Parameter(description = "Filtro por modelo") @QueryParam("modelo") String modelo) {
        
        List<Vehiculo> vehiculos = vehiculoService.findAll(page, size, sortBy, marca, modelo);
        long total = vehiculoService.count(marca, modelo);
        
        Map<String, Object> response = new HashMap<>();
        response.put("vehiculos", vehiculos);
        response.put("total", total);
        response.put("page", page);
        response.put("size", size);
        response.put("totalPages", (total + size - 1) / size);
        
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener vehículo por ID", description = "Obtiene un vehículo específico por su ID")
    // @ApiResponse(responseCode = "200", description = "Vehículo encontrado",
    //         content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vehiculo.class)))
    // @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    // @ApiResponse(responseCode = "401", description = "No autorizado")
    public Vehiculo getVehiculo(@Parameter(description = "ID del vehículo") @PathParam("id") Long id) {
        return vehiculoService.findById(id);
    }

    @POST
    @Operation(summary = "Crear vehículo", description = "Crea un nuevo vehículo en el catálogo")
    // @ApiResponse(responseCode = "201", description = "Vehículo creado exitosamente",
    //         content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vehiculo.class)))
    // @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    // @ApiResponse(responseCode = "401", description = "No autorizado")
    public Response createVehiculo(@Valid Vehiculo vehiculo) {
        Vehiculo nuevoVehiculo = vehiculoService.create(vehiculo);
        return Response.status(Response.Status.CREATED).entity(nuevoVehiculo).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar vehículo", description = "Actualiza un vehículo existente")
    // @ApiResponse(responseCode = "200", description = "Vehículo actualizado exitosamente",
    //         content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vehiculo.class)))
    // @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    // @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    // @ApiResponse(responseCode = "401", description = "No autorizado")
    public Vehiculo updateVehiculo(@Parameter(description = "ID del vehículo") @PathParam("id") Long id, 
                                   @Valid Vehiculo vehiculo) {
        return vehiculoService.update(id, vehiculo);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar vehículo", description = "Elimina un vehículo del catálogo")
    // @ApiResponse(responseCode = "204", description = "Vehículo eliminado exitosamente")
    // @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    // @ApiResponse(responseCode = "401", description = "No autorizado")
    public Response deleteVehiculo(@Parameter(description = "ID del vehículo") @PathParam("id") Long id) {
        vehiculoService.delete(id);
        return Response.noContent().build();
    }
}
