package com.utez.api_sigerp.controller;

import com.utez.api_sigerp.model.Orden;
import com.utez.api_sigerp.service.OrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {
    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    // Crear una nueva orden
    @PostMapping
    public ResponseEntity<Orden> saveOrden(@RequestBody Orden orden) {
        Orden newOrden = ordenService.save(orden);
        return newOrden == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(newOrden);
    }

    // Obtener todas las órdenes
    @GetMapping
    public ResponseEntity<List<Orden>> getOrdenes() {
        List<Orden> ordenes = ordenService.getOrdenes();
        return ordenes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ordenes);
    }

    // Buscar órdenes por ID de mesa
    @GetMapping("/mesa/{mesaId}")
    public ResponseEntity<List<Orden>> getOrdenesByMesaId(@PathVariable String mesaId) {
        List<Orden> ordenes = ordenService.getOrdenesByMesaId(mesaId);
        return ordenes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(ordenes);
    }

    // Editar una orden
    @PutMapping("/{id}")
    public ResponseEntity<Orden> updateOrden(@PathVariable String id, @RequestBody Orden orden) {
        Orden ordenUpdated = ordenService.updateOrden(id, orden);
        return ordenUpdated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(ordenUpdated);
    }
}
