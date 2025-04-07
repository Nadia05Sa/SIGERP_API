package com.utez.api_sigerp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

/**
 * Representa una venta en la base de datos MongoDB.
 */
@Document(collection = "ventas")
public class Venta {
    @Id
    private String id;
    private LocalDate fecha;
    private double ingresos_dia;
    private double ingresos_tarde;
    private double ingresos;
    private double gastos_empleados;
    private double gastos_productos;
    private double gastos;
    private double beneficios;

    public Venta() {}

    public Venta(LocalDate fecha, double ingresos_dia, double ingresos_tarde, double gastos_empleados, double gastos_productos) {
        this.fecha = fecha;
        this.ingresos_dia = ingresos_dia;
        this.ingresos_tarde = ingresos_tarde;
        this.gastos_empleados = gastos_empleados;
        this.gastos_productos = gastos_productos;
        calcularIngresos();
        calcularGastos();
        calcularBeneficios();
    }

    public String getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getIngresos() {
        return ingresos;
    }

    public double getGastos() {
        return gastos;
    }

    public double getBeneficios() {
        return beneficios;
    }

    public double getIngresos_dia() {
        return ingresos_dia;
    }

    public double getIngresos_tarde() {
        return ingresos_tarde;
    }

    public double getGastos_empleados() {
        return gastos_empleados;
    }

    public double getGastos_productos() {
        return gastos_productos;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setIngresos_dia(double ingresos_dia) {
        this.ingresos_dia = ingresos_dia;
        calcularIngresos();
        calcularBeneficios();
    }

    public void setIngresos_tarde(double ingresos_tarde) {
        this.ingresos_tarde = ingresos_tarde;
        calcularIngresos();
        calcularBeneficios();
    }

    public void setGastos_empleados(double gastos_empleados) {
        this.gastos_empleados = gastos_empleados;
        calcularGastos();
        calcularBeneficios();
    }

    public void setGastos_productos(double gastos_productos) {
        this.gastos_productos = gastos_productos;
        calcularGastos();
        calcularBeneficios();
    }

    private void calcularIngresos() {
        this.ingresos = this.ingresos_dia + this.ingresos_tarde;
    }

    private void calcularGastos() {
        this.gastos = this.gastos_empleados + this.gastos_productos;
    }

    private void calcularBeneficios() {
        this.beneficios = this.ingresos - this.gastos;
    }
}
