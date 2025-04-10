package com.utez.api_sigerp.controller;

import com.utez.api_sigerp.model.Orden;
import com.utez.api_sigerp.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private  OrdenService ordenService;

    @PostMapping
    public ResponseEntity<Orden> crearOrden(@RequestBody Orden orden){
        Orden nueva = ordenService.crearOrden(orden);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping
    public ResponseEntity<List<Orden>> listarOrdenes(){
        return ResponseEntity.ok(ordenService.obtenerOrdenes());
    }

    @GetMapping("{id}")
    public ResponseEntity<Orden> obtenerOrdenById(@PathVariable String id){
        return ordenService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable String id) {
        try {
            Orden ordenActualizada = ordenService.actualizarEstado(id);
            return ResponseEntity.ok(ordenActualizada);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOrden(@PathVariable String id) {
        try {
            ordenService.eliminarOrden(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage()); // 400 Bad Request con mensaje
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Orden> actualizarOrden(
            @PathVariable String id,
            @RequestBody Orden ordenActualizada) {

        Orden orden = ordenService.actualizarOrden(id, ordenActualizada);
        return ResponseEntity.ok(orden);
    }

}
