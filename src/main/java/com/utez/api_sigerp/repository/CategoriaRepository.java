package com.utez.api_sigerp.repository;

import com.utez.api_sigerp.model.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface CategoriaRepository extends MongoRepository<Categoria, String> {
    Optional<Categoria> findByNombreIgnoreCase(String nombre);
    Optional<String> findImageById(String id);
}
