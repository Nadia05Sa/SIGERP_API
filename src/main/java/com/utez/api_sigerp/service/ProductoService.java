package com.utez.api_sigerp.service;

import com.utez.api_sigerp.model.Categoria;
import com.utez.api_sigerp.model.Mesa;
import com.utez.api_sigerp.model.Producto;
import com.utez.api_sigerp.repository.CategoriaRepository;
import com.utez.api_sigerp.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository ) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Validar producto (método reutilizable)
    private void validarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("❌ Error: El producto no puede ser nulo.");
        }
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("❌ Error: El producto debe tener un nombre válido.");
        }
        if (producto.getPrecio() == null) {
            throw new IllegalArgumentException("❌ Error: El precio del producto no puede ser nulo.");
        }
        if (producto.getCategorias() == null || producto.getCategorias().isEmpty()) {
            throw new IllegalArgumentException("❌ Error: El producto debe tener al menos una categoría.");
        }
    }

    // Crear un nuevo producto
    public Producto save(Producto producto) {
        validarProducto(producto);
        System.out.println("💾 Guardando producto: " + producto.getNombre());
        return productoRepository.save(producto);
    }

    // Ver todos los productos
    public List<Producto> getProductos() {
        System.out.println("📋 Obteniendo todos los productos...");
        List<Producto> productos = productoRepository.findAll();

        if (productos.isEmpty()) {
            System.out.println("⚠️ Advertencia: No se encontraron productos.");
        } else {
            System.out.println("✅ Productos encontrados: " + productos.size());
        }

        return productos;
    }

    // Buscar un producto por nombre
    public Optional<Producto> getProductoByNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: El nombre del producto no puede estar vacío.");
        }

        System.out.println("🔍 Buscando producto con nombre: " + nombre);
        Optional<Producto> productoOpt = productoRepository.findByNombreIgnoreCase(nombre);

        if (productoOpt.isEmpty()) {
            System.out.println("⚠️ Advertencia: No se encontró un producto con el nombre proporcionado.");
        } else {
            System.out.println("✅ Producto encontrado: " + productoOpt.get());
        }

        return productoOpt;
    }

    // Actualizar un producto existente
    public Producto updateProducto(String id, Producto producto) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: El ID del producto no puede estar vacío.");
        }

        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (productoOpt.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: No se encontró el producto con ID " + id);
        }

        Producto productoToUpdate = productoOpt.get();
        if (producto.getNombre() != null) productoToUpdate.setNombre(producto.getNombre());
        if (producto.getPrecio() != null) productoToUpdate.setPrecio(producto.getPrecio());
        if (producto.getDescripcion() != null) productoToUpdate.setDescripcion(producto.getDescripcion());
        if (producto.getCategorias() != null && !producto.getCategorias().isEmpty()) {
            productoToUpdate.setCategorias(producto.getCategorias());
        }

        System.out.println("🔄 Actualizando producto con ID: " + id);
        return productoRepository.save(productoToUpdate);
    }

    // Eliminar un producto por ID
    public void deleteProducto(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: El ID del producto no puede estar vacío.");
        }

        System.out.println("🗑️ Eliminando producto con ID: " + id);
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("❌ Error: No se encontró el producto con ID " + id);
        }

        productoRepository.deleteById(id);
        System.out.println("✅ Producto eliminado con éxito.");
    }

    // Obtener imagen de producto por ID
    public Optional<String> getProductoImageById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: El ID del producto no puede estar vacío.");
        }
        System.out.println("🔍 Obteniendo imagen del producto con ID: " + id);
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (productoOpt.isEmpty()) {
            System.out.println("⚠️ Advertencia: No se encontró el producto con el ID proporcionado.");
            return Optional.empty();
        }
        return Optional.ofNullable(productoOpt.get().getImagen());
    }

    // Cambiar estado de activo a inactivo
    public Optional<Producto> actualizarEstadoProducto(String id, boolean estado) {
        Optional<Producto> productoOptional = productoRepository.findById(id);

        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            producto.setEstado(estado);
            return Optional.of(productoRepository.save(producto));
        }

        return Optional.empty();
    }

    public List<Producto> getProductosPorCategoria(String idCategoria) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(idCategoria);
        if (categoriaOpt.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: Categoría no encontrada con ID: " + idCategoria);
        }

        return productoRepository.findByCategoriasContaining(categoriaOpt.get());
    }
}