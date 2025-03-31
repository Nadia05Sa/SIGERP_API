package com.utez.api_sigerp.service;

import com.utez.api_sigerp.model.Resena;
import com.utez.api_sigerp.model.Empleado;
import com.utez.api_sigerp.model.Mesa;
import com.utez.api_sigerp.repository.ResenaRepository;
import com.utez.api_sigerp.repository.EmpleadoRepository;
import com.utez.api_sigerp.repository.MesaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {
    private final ResenaRepository resenaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final MesaRepository mesaRepository;

    public ResenaService(ResenaRepository resenaRepository, EmpleadoRepository empleadoRepository, MesaRepository mesaRepository) {
        this.resenaRepository = resenaRepository;
        this.empleadoRepository = empleadoRepository;
        this.mesaRepository = mesaRepository;
    }

    // Crear una nueva reseña
    public Resena save(Resena resena) {
        if (resena == null || resena.getEmpleado() == null || resena.getMesa() == null || resena.getPuntuacion() == null) {
            throw new IllegalArgumentException("❌ Error: La reseña debe tener un empleado, mesa y puntuación válidos.");
        }

        // Validar que el empleado y la mesa existen en la base de datos
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(resena.getEmpleado().getId());
        if (empleadoOpt.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: No se encontró el empleado con ID " + resena.getEmpleado().getId());
        }

        Optional<Mesa> mesaOpt = mesaRepository.findById(resena.getMesa().getId());
        if (mesaOpt.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: No se encontró la mesa con ID " + resena.getMesa().getId());
        }

        // Asignar las referencias válidas
        resena.setEmpleado(empleadoOpt.get());
        resena.setMesa(mesaOpt.get());

        System.out.println("💾 Guardando nueva reseña...");
        return resenaRepository.save(resena);
    }

    // Obtener todas las reseñas
    public List<Resena> getResenas() {
        System.out.println("📋 Obteniendo todas las reseñas...");
        List<Resena> resenas = resenaRepository.findAll();

        if (resenas.isEmpty()) {
            System.out.println("⚠️ Advertencia: No se encontraron reseñas.");
        } else {
            System.out.println("✅ Reseñas encontradas: " + resenas.size());
        }

        return resenas;
    }

    // Buscar reseñas por empleado
    public List<Resena> getResenasByEmpleadoId(String empleadoId) {
        if (empleadoId == null || empleadoId.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: El ID del empleado no puede estar vacío.");
        }

        System.out.println("🔍 Buscando reseñas para el empleado con ID: " + empleadoId);
        return resenaRepository.findByEmpleadoId(empleadoId);
    }

    // Buscar reseñas por mesa
    public List<Resena> getResenasByMesaId(String mesaId) {
        if (mesaId == null || mesaId.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: El ID de la mesa no puede estar vacío.");
        }

        System.out.println("🔍 Buscando reseñas para la mesa con ID: " + mesaId);
        return resenaRepository.findByMesaId(mesaId);
    }

    // Buscar una reseña por su ID
    public Optional<Resena> getResenaById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: El ID de la reseña no puede estar vacío.");
        }

        System.out.println("🔍 Buscando reseña con ID: " + id);
        Optional<Resena> resenaOpt = resenaRepository.findById(id);

        if (resenaOpt.isEmpty()) {
            System.out.println("⚠️ Advertencia: No se encontró la reseña con ID " + id);
        } else {
            System.out.println("✅ Reseña encontrada: " + resenaOpt.get());
        }

        return resenaOpt;
    }
}
