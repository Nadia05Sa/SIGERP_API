package com.utez.api_sigerp.service;

import com.utez.api_sigerp.model.Mesa;
import com.utez.api_sigerp.model.Orden;
import com.utez.api_sigerp.model.Producto;
import com.utez.api_sigerp.repository.MesaRepository;
import com.utez.api_sigerp.repository.OrdenRepository;
import com.utez.api_sigerp.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {
    private final OrdenRepository ordenRepository;
    private final MesaRepository mesaRepository;
    private final ProductoRepository productoRepository;

    public OrdenService(OrdenRepository ordenRepository, MesaRepository mesaRepository, ProductoRepository productoRepository) {
        this.ordenRepository = ordenRepository;
        this.mesaRepository = mesaRepository;
        this.productoRepository = productoRepository;
    }

    // Crear una nueva orden
    public Orden save(Orden orden) {
        if (orden == null || orden.getMesa() == null || orden.getMesa().getId() == null) {
            throw new IllegalArgumentException("❌ Error: La orden debe tener una mesa válida.");
        }

        System.out.println("🔍 Buscando mesa con ID: " + orden.getMesa().getId());
        Optional<Mesa> mesaOpt = mesaRepository.findById(orden.getMesa().getId());

        if (mesaOpt.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: No se encontró la mesa con ID " + orden.getMesa().getId());
        }

        if (orden.getProductos() == null || orden.getProductos().isEmpty()) {
            throw new IllegalArgumentException("❌ Error: La orden debe tener al menos un producto.");
        }

        List<String> productoIds = orden.getProductos().stream().map(Producto::getId).toList();
        System.out.println("🔍 Buscando productos con IDs: " + productoIds);

        List<Producto> productos = productoRepository.findAllById(productoIds);
        System.out.println("✅ Productos encontrados: " + productos.size());

        if (productos.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: No se encontraron productos válidos en la base de datos.");
        }

        orden.setMesa(mesaOpt.get());
        orden.setProductos(productos);
        return ordenRepository.save(orden);
    }

    // Obtener todas las órdenes
    public List<Orden> getOrdenes() {
        return ordenRepository.findAll();
    }

    // Buscar órdenes por ID de mesa
    public List<Orden> getOrdenesByMesaId(String mesaId) {
        return ordenRepository.findByMesa_Id(mesaId);
    }

    // Editar una orden
    public Orden updateOrden(String id, Orden orden) {
        Optional<Orden> ordenOpt = ordenRepository.findById(id);
        if (ordenOpt.isEmpty()) {
            throw new IllegalArgumentException("❌ Error: No se encontró la orden con ID " + id);
        }

        Orden ordenToUpdate = ordenOpt.get();

        if (orden.getFecha() != null) ordenToUpdate.setFecha(orden.getFecha());
        if (orden.getEstado() != null) ordenToUpdate.setEstado(orden.getEstado());
        if (orden.getComentario() != null) ordenToUpdate.setComentario(orden.getComentario());

        if (orden.getMesa() != null) {
            Optional<Mesa> mesaOpt = mesaRepository.findById(orden.getMesa().getId());
            mesaOpt.ifPresent(ordenToUpdate::setMesa);
        }

        if (orden.getProductos() != null) {
            List<Producto> productos = productoRepository.findAllById(
                    orden.getProductos().stream().map(Producto::getId).toList()
            );
            ordenToUpdate.setProductos(productos);
        }

        return ordenRepository.save(ordenToUpdate);
    }
}
