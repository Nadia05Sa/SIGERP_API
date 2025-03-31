package com.utez.api_sigerp.controller;

import com.utez.api_sigerp.model.Producto;
import com.utez.api_sigerp.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        @PutMapping("/estado/{id}")
        public ResponseEntity<Producto> changeEstadoActivo(@PathVariable String id, @RequestParam boolean activo) {
            Producto updatedProducto = productoService.changeEstadoActivo(id, activo);
            return ResponseEntity.ok(updatedProducto);
        }
    }
