package DTO;

import java.io.Serializable;

public class Rol implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private int codigoRol;
    private String descripcionRol;

    public Rol(int codigoRol, String descripcionRol) {
        this.codigoRol = codigoRol;
        this.descripcionRol = descripcionRol;
    }

    public int getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(int codigoRol) {
        this.codigoRol = codigoRol;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    @Override
    public String toString() {
        return descripcionRol;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Rol)){
            return false;
        }
        return this.codigoRol == ((Rol) obj).codigoRol;
    }

}
