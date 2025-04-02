package com.utez.api_sigerp.controller;

import com.utez.api_sigerp.model.Empleado;
import com.utez.api_sigerp.model.Mesa;
import com.utez.api_sigerp.model.Producto;
import com.utez.api_sigerp.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<Producto> saveProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.save(producto);
        return ResponseEntity.ok(newProducto);
    }

    // Ver todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> getProductos() {
        List<Producto> productos = productoService.getProductos();
        return productos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productos);
    }

    // Buscar un producto por nombre
    @GetMapping("/{nombre}")
    public ResponseEntity<Producto> getProductoByNombre(@PathVariable String nombre) {
        Optional<Producto> producto = productoService.getProductoByNombre(nombre);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


        // Obtener imagen de producto por ID
        @GetMapping("/image/{id}")
        public ResponseEntity<String> getProductoImage(@PathVariable String id) {
            Optional<String> imagen = productoService.getProductoImageById(id);
            return imagen.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        // Cambiar estado de activo a inactivo
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Producto> actualizarEstadoProducto(@PathVariable String id, @RequestBody Map<String, Boolean> estado) {
        if (!estado.containsKey("estado")) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Producto> productoActualizada = productoService.actualizarEstadoProducto(id, estado.get("estado"));
        return productoActualizada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Producto> updateEmpleado(@PathVariable String id, @RequestBody Producto producto) {
        Producto productoUpdated = productoService.updateProducto(id, producto);
        return productoUpdated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(productoUpdated);
    }
}
