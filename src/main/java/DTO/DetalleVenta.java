package DTO;

public class DetalleVenta {
    private Venta venta;
    private Servicio servicio;
    private int cantidad;
    private int precio;

    public DetalleVenta() {
    }

    public DetalleVenta(Venta venta, Servicio servicio, int cantidad, int precio) {
        this.venta = venta;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
    
}