package com.utez.api_sigerp.service;

import com.utez.api_sigerp.model.Mesa;
import com.utez.api_sigerp.repository.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaService {
    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    // Método para guardar la mesa con validación de nombre duplicado
    public Mesa save(Mesa mesa) {
        // Verificar si ya existe una mesa con el mismo nombre (ignorando mayúsculas/minúsculas)
        Optional<Mesa> mesaExistente = mesaRepository.findByNombreIgnoreCase(mesa.getNombre());

        if (mesaExistente.isPresent()) {
            // Si ya existe, lanzar una excepción o devolver un error
            throw new IllegalArgumentException("Ya existe una mesa con el nombre: " + mesa.getNombre());
        }

        return mesaRepository.save(mesa);
    }

    public List<Mesa> getMesas() {
        return mesaRepository.findAll();
    }

    public Optional<Mesa> getMesaByNombre(String nombre) {
        return mesaRepository.findByNombreIgnoreCase(nombre);
    }

    public Optional<Mesa> getMesaById(String id) {
        return mesaRepository.findById(id);
    }

    public Optional<Mesa> actualizarEstadoMesa(String id, boolean estado) {
        Optional<Mesa> mesaOptional = mesaRepository.findById(id);

        if (mesaOptional.isPresent()) {
            Mesa mesa = mesaOptional.get();
            mesa.setEstado(estado);
            return Optional.of(mesaRepository.save(mesa));
        }

        return Optional.empty();
    }

    public Optional<String> getMesaImageById(String id) {
        return mesaRepository.findById(id)
                .map(Mesa::getImagen);
    }

    public Optional<Mesa> updateMesa(String id, Mesa nuevaMesa) {
        Optional<Mesa> mesaExistente = mesaRepository.findById(id);
        if (mesaExistente.isPresent()) {
            Mesa mesa = mesaExistente.get();
            mesa.setCapacidad(nuevaMesa.getCapacidad());
            mesa.setEstado(nuevaMesa.isEstado());
            mesa.setImagen(nuevaMesa.getImagen());
            mesa.setNombre(nuevaMesa.getNombre());


            mesaRepository.save(mesa);
            return Optional.of(mesa);
        }
        return Optional.empty();
    }


    public Optional<?> getOrdenVinculada(String idMesa) {
        Optional<Mesa> mesaOpt = mesaRepository.findById(idMesa);

        if (mesaOpt.isPresent()) {
            Mesa mesa = mesaOpt.get();
            if (mesa.getOrden() != null) {
                return Optional.of(mesa.getOrdenVinculada());
            } else {
                return Optional.empty(); // No tiene orden
            }
        } else {
            return Optional.empty(); // Mesa no existe
        }
    }

}