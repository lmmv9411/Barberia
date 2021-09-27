package DTO;

import java.util.Objects;

public class Unidad {

    private int id;
    private String nombre;

    public Unidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Unidad() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Unidad){
            return this.nombre.equals(((Unidad) obj).getNombre());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

}
