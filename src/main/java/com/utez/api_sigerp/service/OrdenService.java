package com.utez.api_sigerp.service;

import com.utez.api_sigerp.model.Mesa;
import com.utez.api_sigerp.model.Orden;
import com.utez.api_sigerp.model.Producto;
import com.utez.api_sigerp.repository.MesaRepository;
import com.utez.api_sigerp.repository.OrdenRepository;
import com.utez.api_sigerp.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {

    @Autowired
    private  OrdenRepository ordenRepository;

    public Orden crearOrden(Orden orden){
        return ordenRepository.save(orden);
    }

    public List<Orden> obtenerOrdenes(){
        return ordenRepository.findAll();
    }

    public Optional<Orden> obtenerPorId(String id){
        return ordenRepository.findById(id);
    }


    public Orden actualizarEstado(String id, boolean nuevoEstado) throws Exception {
        Optional<Orden> ordenOptional = ordenRepository.findById(id);
        if (ordenOptional.isEmpty()) {
            throw new Exception("Orden no encontrada con id: " + id);
        }

        Orden orden = ordenOptional.get();
        orden.setEstado(nuevoEstado);
        return ordenRepository.save(orden);
    }

}
