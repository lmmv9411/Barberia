package DTO;

public class Servicio {
    private int id, precio;
    private String nombre;
    boolean activo;

    public Servicio(int id, int precio, String nombre, boolean activo) {
        this.id = id;
        this.precio = precio;
        this.nombre = nombre;
        this.activo = activo;
    }

    public Servicio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Servicio){
            var servicio = (Servicio) obj;
            return servicio.getId() == this.id;
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.id;
        return hash;
    }
    
}