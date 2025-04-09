package com.utez.api_sigerp.controller;

import com.utez.api_sigerp.model.Orden;
import com.utez.api_sigerp.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
