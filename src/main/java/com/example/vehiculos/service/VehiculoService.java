package com.example.vehiculos.service;

import com.example.vehiculos.model.Vehiculo;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class VehiculoService {

    public List<Vehiculo> findAll(int page, int size, String sortBy, String marca, String modelo) {
        Sort sort = Sort.by(sortBy != null ? sortBy : "id");
        
        String query = "1=1";
        if (marca != null && !marca.trim().isEmpty()) {
            query += " and lower(marca) like lower('%" + marca + "%')";
        }
        if (modelo != null && !modelo.trim().isEmpty()) {
            query += " and lower(modelo) like lower('%" + modelo + "%')";
        }
        
        return Vehiculo.find(query, sort).page(Page.of(page, size)).list();
    }

    public long count(String marca, String modelo) {
        String query = "1=1";
        if (marca != null && !marca.trim().isEmpty()) {
            query += " and lower(marca) like lower('%" + marca + "%')";
        }
        if (modelo != null && !modelo.trim().isEmpty()) {
            query += " and lower(modelo) like lower('%" + modelo + "%')";
        }
        
        return Vehiculo.count(query);
    }

    public Vehiculo findById(Long id) {
        Vehiculo vehiculo = Vehiculo.findById(id);
        if (vehiculo == null) {
            throw new NotFoundException("Vehículo no encontrado con ID: " + id);
        }
        return vehiculo;
    }

    @Transactional
    public Vehiculo create(Vehiculo vehiculo) {
        vehiculo.persist();
        return vehiculo;
    }

    @Transactional
    public Vehiculo update(Long id, Vehiculo vehiculoActualizado) {
        Vehiculo vehiculo = findById(id);
        
        vehiculo.marca = vehiculoActualizado.marca;
        vehiculo.modelo = vehiculoActualizado.modelo;
        vehiculo.anio = vehiculoActualizado.anio;
        vehiculo.precio = vehiculoActualizado.precio;
        vehiculo.color = vehiculoActualizado.color;
        vehiculo.transmision = vehiculoActualizado.transmision;
        vehiculo.combustible = vehiculoActualizado.combustible;
        vehiculo.puertas = vehiculoActualizado.puertas;
        
        return vehiculo;
    }

    @Transactional
    public void delete(Long id) {
        Vehiculo vehiculo = findById(id);
        vehiculo.delete();
    }
}
