package DTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Usuario implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String nombre, segundoNombre, apellidoPaterno, apellidoMaterno, contrasena;
    private String id;
    private Rol rol;
    private boolean activo;
    private Map<String, List<String>> permisos;

    public Map<String, List<String>> getPermisos() {
        return permisos;
    }


    public void setPermisos(Map<String, List<String>> permisos) {
        this.permisos = permisos;
    }

    public Usuario(){
        
    }

    public Usuario(String id, String nombre, String segundoNombre, String apellidoPaterno, String apellidoMaterno, Rol rol, String contrasena, boolean activo, Map<String, List<String>> permisos) {
        this.nombre = nombre;
        this.segundoNombre = segundoNombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.contrasena = contrasena;
        this.id = id;
        this.rol = rol;
        this.activo = activo;
        this.permisos = permisos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
