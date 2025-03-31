package com.utez.api_sigerp.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.time.LocalDate;

@Document(collection = "ventas")
public class Venta {
    @Id
    private String id;
    private LocalDate fecha;
    private double ingresosDia;
    private double ingresosTarde;
    private double total;
    private int clientesDia;
    private int clientesTarde;
    private List<Orden> ordenes;

    public Venta(LocalDate fecha, double ingresosDia, double ingresosTarde, double total, int clientesDia, int clientesTarde, List<Orden> ordenes) {
        this.fecha = fecha;
        this.ingresosDia = ingresosDia;
        this.ingresosTarde = ingresosTarde;
        this.total = total;
        this.clientesDia = clientesDia;
        this.clientesTarde = clientesTarde;
        this.ordenes = ordenes;
    }

    public void calcularTotal() {
        this.total = this.ingresosDia + this.ingresosTarde;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public double getIngresosDia() { return ingresosDia; }
    public void setIngresosDia(double ingresosDia) { this.ingresosDia = ingresosDia; }
    public double getIngresosTarde() { return ingresosTarde; }
    public void setIngresosTarde(double ingresosTarde) { this.ingresosTarde = ingresosTarde; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public int getClientesDia() { return clientesDia; }
    public void setClientesDia(int clientesDia) { this.clientesDia = clientesDia; }
    public int getClientesTarde() { return clientesTarde; }
    public void setClientesTarde(int clientesTarde) { this.clientesTarde = clientesTarde; }
    public List<Orden> getOrdenes() { return ordenes; }
    public void setOrdenes(List<Orden> ordenes) { this.ordenes = ordenes; }
}
