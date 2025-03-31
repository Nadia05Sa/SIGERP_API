package com.utez.api_sigerp.controller;

import com.utez.api_sigerp.model.Resena;
import com.utez.api_sigerp.service.ResenaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resena")
public class ResenaController {
    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    // Crear una nueva reseña
    @PostMapping
    public ResponseEntity<Resena> saveResena(@RequestBody Resena resena) {
        Resena newResena = resenaService.save(resena);
        return ResponseEntity.ok(newResena);
    }

    // Obtener todas las reseñas
    @GetMapping
    public ResponseEntity<List<Resena>> getResenas() {
        List<Resena> resenas = resenaService.getResenas();
        return resenas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resenas);
    }

    // Buscar reseñas por empleado
    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<Resena>> getResenasByEmpleadoId(@PathVariable String empleadoId) {
        List<Resena> resenas = resenaService.getResenasByEmpleadoId(empleadoId);
        return resenas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resenas);
    }

    // Buscar reseñas por mesa
    @GetMapping("/mesa/{mesaId}")
    public ResponseEntity<List<Resena>> getResenasByMesaId(@PathVariable String mesaId) {
        List<Resena> resenas = resenaService.getResenasByMesaId(mesaId);
        return resenas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resenas);
    }

    // Buscar una reseña por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Resena> getResenaById(@PathVariable String id) {
        Optional<Resena> resena = resenaService.getResenaById(id);
        return resena.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
