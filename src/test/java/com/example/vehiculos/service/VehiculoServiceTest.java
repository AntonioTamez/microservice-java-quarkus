package com.example.vehiculos.service;

import com.example.vehiculos.model.Vehiculo;
import com.example.vehiculos.model.Transmision;
import com.example.vehiculos.model.Combustible;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class VehiculoServiceTest {

    @Inject
    VehiculoService vehiculoService;

    @BeforeEach
    @Transactional
    void setUp() {
        // Clean up any existing test data
        Vehiculo.deleteAll();
    }

    @Test
    @Transactional
    void testCreateVehiculo() {
        // Given
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", 2023, new BigDecimal("25000.00"));
        vehiculo.color = "Blanco";
        vehiculo.transmision = Transmision.AUTOMATICA;
        vehiculo.combustible = Combustible.GASOLINA;
        vehiculo.puertas = 4;

        // When
        Vehiculo resultado = vehiculoService.create(vehiculo);

        // Then
        assertNotNull(resultado);
        assertNotNull(resultado.id);
        assertEquals("Toyota", resultado.marca);
        assertEquals("Corolla", resultado.modelo);
        assertEquals(2023, resultado.anio);
        assertEquals(new BigDecimal("25000.00"), resultado.precio);
    }

    @Test
    @Transactional
    void testFindById() {
        // Given
        Vehiculo vehiculo = new Vehiculo("Honda", "Civic", 2022, new BigDecimal("28000.00"));
        vehiculoService.create(vehiculo);

        // When
        Vehiculo encontrado = vehiculoService.findById(vehiculo.id);

        // Then
        assertNotNull(encontrado);
        assertEquals("Honda", encontrado.marca);
        assertEquals("Civic", encontrado.modelo);
    }

    @Test
    void testFindByIdNotFound() {
        // When & Then
        assertThrows(NotFoundException.class, () -> {
            vehiculoService.findById(999L);
        });
    }

    @Test
    @Transactional
    void testUpdateVehiculo() {
        // Given
        Vehiculo vehiculo = new Vehiculo("Ford", "Focus", 2021, new BigDecimal("22000.00"));
        vehiculoService.create(vehiculo);

        Vehiculo actualizado = new Vehiculo("Ford", "Focus", 2022, new BigDecimal("24000.00"));
        actualizado.color = "Rojo";
        actualizado.transmision = Transmision.MANUAL;

        // When
        Vehiculo resultado = vehiculoService.update(vehiculo.id, actualizado);

        // Then
        assertEquals(2022, resultado.anio);
        assertEquals(new BigDecimal("24000.00"), resultado.precio);
        assertEquals("Rojo", resultado.color);
        assertEquals(Transmision.MANUAL, resultado.transmision);
    }

    @Test
    @Transactional
    void testDeleteVehiculo() {
        // Given
        Vehiculo vehiculo = new Vehiculo("BMW", "X3", 2023, new BigDecimal("45000.00"));
        vehiculoService.create(vehiculo);
        Long id = vehiculo.id;

        // When
        vehiculoService.delete(id);

        // Then
        assertThrows(NotFoundException.class, () -> {
            vehiculoService.findById(id);
        });
    }

    @Test
    @Transactional
    void testFindAll() {
        // Given
        vehiculoService.create(new Vehiculo("Toyota", "Corolla", 2023, new BigDecimal("25000.00")));
        vehiculoService.create(new Vehiculo("Honda", "Civic", 2022, new BigDecimal("28000.00")));

        // When
        List<Vehiculo> vehiculos = vehiculoService.findAll(0, 10, "id", null, null);

        // Then
        assertEquals(2, vehiculos.size());
    }

    @Test
    @Transactional
    void testFindAllWithFilter() {
        // Given
        vehiculoService.create(new Vehiculo("Toyota", "Corolla", 2023, new BigDecimal("25000.00")));
        vehiculoService.create(new Vehiculo("Honda", "Civic", 2022, new BigDecimal("28000.00")));

        // When
        List<Vehiculo> vehiculos = vehiculoService.findAll(0, 10, "id", "Toyota", null);

        // Then
        assertEquals(1, vehiculos.size());
        assertEquals("Toyota", vehiculos.get(0).marca);
    }
}
