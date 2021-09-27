package DTO;

import java.util.Objects;

public class Barbero {

    private int id;
    private Usuario Usuario;

    public Barbero() {
    }

    public Barbero(int id, Usuario usuario) {
        this.id = id;
        this.Usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.Usuario = usuario;
    }

    @Override
    public String toString() {
        return this.Usuario.getNombre() + " "  + this.Usuario.getApellidoPaterno();
     } 

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Barbero){
            var barbero = (Barbero)obj;
            return barbero.getId() == this.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.id;
        return hash;
    }
    
}
