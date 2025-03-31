package com.utez.api_sigerp.repository;

import com.utez.api_sigerp.model.Empleado;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends MongoRepository<Empleado, String> {
    Optional<Empleado> findByCorreoAndContrasena(String correo, String contrasena);
    Optional<Empleado> findByNombreIgnoreCase(String nombre);
}
