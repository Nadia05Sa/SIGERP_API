package com.utez.api_sigerp.callback;


import com.utez.api_sigerp.model.Venta;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveCallback;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class VentaCallback implements BeforeSaveCallback<Venta> {
    public Venta onBeforeSave(Venta venta, Document document) {
        if (venta.getFecha() == null) {
            venta.setFecha(LocalDate.now());
        }
        venta.setIngresosDia(venta.getIngresosDia() == 0.0 ? 0.0 : venta.getIngresosDia());
        venta.setIngresosTarde(venta.getIngresosTarde() == 0.0 ? 0.0 : venta.getIngresosTarde());
        venta.setClientesDia(venta.getClientesDia() == 0 ? 0 : venta.getClientesDia());
        venta.setClientesTarde(venta.getClientesTarde() == 0 ? 0 : venta.getClientesTarde());
        venta.calcularTotal();
        return venta;
    }

    @Override
    public Venta onBeforeSave(Venta entity, org.bson.Document document, String collection) {
        return null;
    }
}