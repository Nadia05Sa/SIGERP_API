package com.utez.api_sigerp.service;

import com.utez.api_sigerp.model.Venta;
import com.utez.api_sigerp.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio que contiene la lógica de negocio relacionada con las ventas.
 */
@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }

    public List<Venta> obtenerVentasPorSemana() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.minusDays(6);
        return ventaRepository.findByFechaBetween(inicioSemana, hoy);
    }

    public Venta agregarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }
}