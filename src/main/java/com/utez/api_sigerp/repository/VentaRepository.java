package com.utez.api_sigerp.repository;

import com.utez.api_sigerp.model.Venta;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface VentaRepository extends MongoRepository<Venta, String> {
    List<Venta> findByFechaBetween(LocalDate inicio, LocalDate fin);
}

