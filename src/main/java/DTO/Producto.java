package DTO;

public class Producto {

    private String id;
    private String nombre;
    private Unidad unidad;
    private int cantidad;
    private int minimo;

    public Producto() {
    }

    public Producto(String id, String nombre, Unidad unidad, int cantidad, int minimo) {
        this.id = id;
        this.nombre = nombre;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.minimo = minimo;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }
    
    
    
}
