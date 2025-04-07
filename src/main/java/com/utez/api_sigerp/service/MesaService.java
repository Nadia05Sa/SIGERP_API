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

    public Mesa save(Mesa mesa) {
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


}