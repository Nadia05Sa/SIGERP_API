package com.utez.api_sigerp.service;

import com.utez.api_sigerp.model.Empleado;
import com.utez.api_sigerp.model.Mesa;
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
            if(empleado.getApellido()!= null) existingEmpleado.setApellido(empleado.getApellido());
            if (empleado.getCorreo() != null) existingEmpleado.setCorreo(empleado.getCorreo());
            if (empleado.getContrasena() != null) existingEmpleado.setContrasena(empleado.getContrasena());
            if (empleado.getFoto() != null) existingEmpleado.setFoto(empleado.getFoto());
            if (empleado.getMesas() != null) existingEmpleado.setMesas(empleado.getMesas());
            return empleadoRepository.save(existingEmpleado);
        }).orElse(null);
    }

    public void deleteEmpleado(String id) {
        empleadoRepository.deleteById(id);
    }

    public Optional<Empleado> actualizarEstadoEmpleado(String id, boolean estado) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(id);

        if (empleadoOptional.isPresent()) {
            Empleado empleado = empleadoOptional.get();
            empleado.setEstado(estado);
            return Optional.of(empleadoRepository.save(empleado));
        }

        return Optional.empty();
    }

    public Optional<String> getEmpleadoFotoById(String id) {
        return empleadoRepository.findById(id)
                .map(Empleado::getFoto);
    }
}