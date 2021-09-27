package DTO;

public class Cliente {

    public Cliente() {

    }

    public Cliente(int id, String nombre, String nombreii, String apellido, String apellidoii, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.nombreii = nombreii;
        this.apellidoii = apellidoii;
        this.email = email;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    private String nombre, apellido, telefono;
    private String nombreii, apellidoii, email;

    public String getNombreii() {
        return nombreii;
    }

    public void setNombreii(String nombreii) {
        this.nombreii = nombreii;
    }

    public String getApellidoii() {
        return apellidoii;
    }

    public void setApellidoii(String apellidoii) {
        this.apellidoii = apellidoii;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private int id;
}
