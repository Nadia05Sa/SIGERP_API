
package com.utez.api_sigerp.service;

import com.utez.api_sigerp.model.Empleado;
import com.utez.api_sigerp.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public List<Empleado> getEmpleados() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> getEmpleadoById(String id) {
        return empleadoRepository.findById(id);
    }

    public Optional<Empleado> getEmpleadoByNombre(String nombre) {
        return empleadoRepository.findByNombreIgnoreCase(nombre);
    }

    public Optional<Empleado> authenticate(String correo, String contrasena) {
        return empleadoRepository.findByCorreoAndContrasena(correo, contrasena);
    }

    public Empleado updateEmpleado(String id, Empleado empleado) {
        return empleadoRepository.findById(id).map(existingEmpleado -> {
            if (empleado.getNombre() != null) existingEmpleado.setNombre(empleado.getNombre());
            if (empleado.getCorreo() != null) existingEmpleado.setCorreo(empleado.getCorreo());
            if (empleado.getContrasena() != null) existingEmpleado.setContrasena(empleado.getContrasena());
            if (empleado.getRol() != null) existingEmpleado.setRol(empleado.getRol());
            if (empleado.getFoto() != null) existingEmpleado.setFoto(empleado.getFoto());
            return empleadoRepository.save(existingEmpleado);
        }).orElse(null);
    }

    public void deleteEmpleado(String id) {
        empleadoRepository.deleteById(id);
    }
}