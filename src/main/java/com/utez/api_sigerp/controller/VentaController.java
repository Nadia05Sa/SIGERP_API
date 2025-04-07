package com.utez.api_sigerp.controller;

import com.utez.api_sigerp.model.Venta;
import com.utez.api_sigerp.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que expone los endpoints REST para gestionar ventas.
 */
@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @GetMapping("/todas")
    public ResponseEntity<List<Venta>> obtenerTodasLasVentas() {
        return ResponseEntity.ok(ventaService.obtenerTodasLasVentas());
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<List<Venta>> obtenerEstadisticas() {
        return ResponseEntity.ok(ventaService.obtenerVentasPorSemana());
    }

    @PostMapping("/nueva")
    public ResponseEntity<Venta> agregarVenta(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.agregarVenta(venta));
    }
}