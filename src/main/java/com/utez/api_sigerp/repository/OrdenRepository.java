package com.utez.api_sigerp.repository;

import com.utez.api_sigerp.model.Orden;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrdenRepository extends MongoRepository<Orden, String> {
    List<Orden> findByMesa_Id(String mesaId);
}
