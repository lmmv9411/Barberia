package DTO;

public class Venta {

    private int id, descuento, monto_recibido, total;
    private Cliente cliente;
    private Usuario usuario;
    private Barbero barbero;
    private EstadoFactura estado;
    private java.sql.Timestamp fecha;

    public Venta(int id, int descuento, int monto_recibido, int total, Cliente cliente, Usuario usuario, Barbero barbero, java.sql.Timestamp fecha, EstadoFactura estado) {
        this.id = id;
        this.descuento = descuento;
        this.monto_recibido = monto_recibido;
        this.total = total;
        this.cliente = cliente;
        this.usuario = usuario;
        this.barbero = barbero;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Barbero getBarbero() {
        return barbero;
    }

    public void setBarbero(Barbero barbero) {
        this.barbero = barbero;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Venta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getMonto_recibido() {
        return monto_recibido;
    }

    public void setMonto_recibido(int monto_recibido) {
        this.monto_recibido = monto_recibido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public java.sql.Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Timestamp fecha) {
        this.fecha = fecha;
    }

    public EstadoFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }

}
