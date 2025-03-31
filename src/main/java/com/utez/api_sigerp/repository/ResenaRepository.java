package com.utez.api_sigerp.repository;

import com.utez.api_sigerp.model.Resena;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResenaRepository extends MongoRepository<Resena, String> {
    List<Resena> findByEmpleadoId(String empleadoId);
    List<Resena> findByMesaId(String mesaId);
}
