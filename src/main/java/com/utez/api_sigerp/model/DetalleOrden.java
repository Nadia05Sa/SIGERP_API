package com.utez.api_sigerp.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class DetalleOrden {

    @DBRef
    private Producto producto;

    private int cantidad;

    private String detalle;

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


}
