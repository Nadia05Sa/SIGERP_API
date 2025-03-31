package com.utez.api_sigerp.repository;

import com.utez.api_sigerp.model.Mesa;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface MesaRepository extends MongoRepository<Mesa, String> {
    Optional<Mesa> findByNombreIgnoreCase(String nombre);
    Optional<String> findImageById(String id);
}