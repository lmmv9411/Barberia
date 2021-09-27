package Utilidades;

import DTO.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Sesion {

    public static DTO.Usuario getUsuario() {

        DTO.Usuario usuario = null;

        try (var ois = new ObjectInputStream(new FileInputStream(PATH))) {
            usuario = (Usuario) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return usuario;
    }

    public static void cerrarSesion() {
        File f = new File(PATH);
        f.delete();
    }

    private static final String PATH = System.getProperty("user.home")
            + File.separator
            + ".barber"
            + File.separator
            + ".sesion";
}
