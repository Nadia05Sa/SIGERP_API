package com.utez.api_sigerp.controller;

import com.utez.api_sigerp.model.Mesa;
import com.utez.api_sigerp.service.MesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {
    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @PostMapping
    public ResponseEntity<Mesa> saveMesa(@RequestBody Mesa mesa) {
        Mesa newMesa = mesaService.save(mesa);
        return ResponseEntity.ok(newMesa);
    }

    @GetMapping
    public ResponseEntity<List<Mesa>> getMesas() {
        List<Mesa> mesas = mesaService.getMesas();
        return mesas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(mesas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> getMesaById(@PathVariable String id) {
        Optional<Mesa> mesa = mesaService.getMesaById(id);
        return mesa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Mesa> actualizarEstadoMesa(@PathVariable String id, @RequestBody Map<String, Boolean> estado) {
        if (!estado.containsKey("estado")) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Mesa> mesaActualizada = mesaService.actualizarEstadoMesa(id, estado.get("estado"));
        return mesaActualizada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<String> getMesaImage(@PathVariable String id) {
        Optional<String> imagen = mesaService.getMesaImageById(id);
        return imagen.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Mesa> updateMesa(@PathVariable String id, @RequestBody Mesa mesa) {
        Optional<Mesa> mesaActualizada = mesaService.updateMesa(id, mesa);
        return mesaActualizada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}/orden")
    public ResponseEntity<?> getOrdenVinculada(@PathVariable String id) {
        return mesaService.getMesaById(id)
                .map(mesa -> {
                    if (mesa.getOrden() != null) {
                        return ResponseEntity.ok(mesa.getOrdenVinculada());
                    } else {
                        return ResponseEntity.status(404)
                                .body("La mesa no tiene una orden vinculada.");
                    }
                })
                .orElseGet(() -> ResponseEntity.status(404)
                        .body("No se encontró la mesa con ID: " + id));
    }

}