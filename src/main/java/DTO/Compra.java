package DTO;

public class Compra {

    private int id;
    private String factura;
    private Proveedor proveedor;
    private Usuario usuario;
    private java.sql.Timestamp fecha;
    private double total;

    public Compra(int id, String factura, Proveedor proveedor, double total, Usuario usuario, java.sql.Timestamp fecha) {
        this.id = id;
        this.factura = factura;
        this.proveedor = proveedor;
        this.usuario = usuario;
        this.fecha = fecha;
        this.total = total;
    }

    public Compra() {
    }

    public int getId() {
        return id;
    }

    public String getFactura() {
        return factura;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public java.sql.Timestamp getFecha() {
        return fecha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFecha(java.sql.Timestamp fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    

}
