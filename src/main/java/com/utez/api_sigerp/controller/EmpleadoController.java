package com.utez.api_sigerp.controller;

import com.utez.api_sigerp.model.Empleado;
import com.utez.api_sigerp.model.Mesa;
import com.utez.api_sigerp.service.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping
    public ResponseEntity<Empleado> saveEmpleado(@RequestBody Empleado empleado) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.save(empleado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Empleado>> getEmpleados() {
        List<Empleado> empleados = empleadoService.getEmpleados();
        return empleados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(empleados);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable String id) {
        return empleadoService.getEmpleadoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/mesas")
    public ResponseEntity<List<Mesa>> getMesasByEmpleadoId(@PathVariable String id) {
        return empleadoService.getMesasByEmpleadoId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/login/{id}")
    public ResponseEntity<Empleado> login(@RequestBody Empleado empleado) {
        return empleadoService.authenticate(empleado.getCorreo(), empleado.getContrasena())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable String id, @RequestBody Empleado empleado) {
        Empleado empleadoUpdated = empleadoService.updateEmpleado(id, empleado);
        return empleadoUpdated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(empleadoUpdated);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable String id) {
        try {
            empleadoService.deleteEmpleado(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PatchMapping("/{id}/estado")
    public ResponseEntity<Empleado> patchEstadoEmpleado(@PathVariable String id, @RequestBody Map<String, Boolean> estado) {
        if (!estado.containsKey("estado")) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Empleado> empleadoActualizada = empleadoService.actualizarEstadoEmpleado(id, estado.get("estado"));
        return empleadoActualizada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<String> getEmpleadoFoto(@PathVariable String id) {
        Optional<String> imagen = empleadoService.getEmpleadoFotoById(id);
        return imagen.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
