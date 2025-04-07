package com.utez.api_sigerp.repository;

import com.utez.api_sigerp.model.Venta;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para operaciones CRUD y consultas personalizadas de ventas.
 */
public interface VentaRepository extends MongoRepository<Venta, String> {
    // Obtiene ventas entre dos fechas, útil para estadísticas semanales
    List<Venta> findByFechaBetween(LocalDate inicio, LocalDate fin);
}
