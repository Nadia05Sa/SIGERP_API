package com.utez.api_sigerp.controller;

import com.utez.api_sigerp.model.Categoria;
import com.utez.api_sigerp.model.Mesa;
import com.utez.api_sigerp.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<Categoria> saveCategoria(@RequestBody Categoria categoria) {
        Categoria newCategoria = categoriaService.save(categoria);
        return ResponseEntity.ok(newCategoria);
    }

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<Categoria>> getCategorias() {
        List<Categoria> categorias = categoriaService.getCategorias();
        return categorias.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categorias);
    }

    // Buscar una categoría por nombre
    @GetMapping("/{nombre}")
    public ResponseEntity<Categoria> getCategoriaByNombre(@PathVariable String nombre) {
        Optional<Categoria> categoria = categoriaService.getCategoriaByNombre(nombre);
        return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar una categoría por ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable String id) {
        Optional<Categoria> categoria = categoriaService.getCategoriaById(id);
        return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<String> getCategoriaImage(@PathVariable String id) {
        Optional<String> imagen = categoriaService.getCategoriaImageById(id);
        return imagen.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Categoria> actualizarEstadoCategoria(@PathVariable String id, @RequestBody Map<String, Boolean> estado) {
        if (!estado.containsKey("estado")) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Categoria> categoriaActualizada = categoriaService.actualizarEstadoCategoria(id, estado.get("estado"));
        return categoriaActualizada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
