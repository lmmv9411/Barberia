package DTO;

import java.sql.Timestamp;

public class VentasPorCobrar extends Venta{
    
    private int montoAbonado;

    public VentasPorCobrar(int id, int descuento, int monto_recibido, int total, Cliente cliente, Usuario usuario, Barbero barbero, Timestamp fecha, EstadoFactura estado) {
        super(id, descuento, monto_recibido, total, cliente, usuario, barbero, fecha, estado);
    }

    public VentasPorCobrar() {
    }

    public int getMontoAbonado() {
        return montoAbonado;
    }

    public void setMontoAbonado(int montoAbonado) {
        this.montoAbonado = montoAbonado;
    }
    
    
    
}
