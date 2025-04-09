package com.utez.api_sigerp.repository;

import com.utez.api_sigerp.model.Categoria;
import com.utez.api_sigerp.model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends MongoRepository<Producto, String> {
    Optional<Producto> findByNombreIgnoreCase(String nombre); // Método más eficiente
    List<Producto> findByCategoriasContaining(Categoria categoria);
}
