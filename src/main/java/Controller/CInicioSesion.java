package Controller;

import DAO.UsuarioDAO;
import DTO.Usuario;
import Vista.VBaseDeDatos;
import Vista.VInit;
import Vista.VLogin;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.commons.codec.digest.DigestUtils;

public class CInicioSesion {

    public CInicioSesion(VLogin vista) {
        this.vista = vista;
        iniciarEventos();
        dao = new UsuarioDAO();

        java.net.URL url = getClass().getResource("/images/barberia.png");

        try {
            java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(url);
            vista.setIconImage(image);
        } catch (java.io.IOException ex) {
            System.out.println(ex);
        }

        if (Utilidades.Sesion.getUsuario() != null) {
            VInit init = new VInit();
            CInit cinit = new CInit(init);
            init.setVisible(true);
            this.vista.dispose();
        } else {
            this.vista.setVisible(true);
        }

    }

    private void iniciarEventos() {
        vista.getBtnEntrar().addActionListener(this::eventos);
        vista.getBtnSalir().addActionListener(this::eventos);
        vista.getBtnVerPass().addActionListener((e) -> {
            if (vista.getBtnVerPass().isSelected()) {
                vista.getTxtContrasena().setEchoChar((char) 0);
            } else {
                vista.getTxtContrasena().setEchoChar('*');
            }
        });
    }

    private void eventos(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Entrar":

                if (vista.getTxtUsuario().getText().equals("") || vista.getTxtContrasena().getText().equals("")) {
                    vista.getTxtAdvertencia().setText("¡Rellene los campos vacíos!");
                    return;
                }

                Usuario usuario = new Usuario();
                usuario.setId(vista.getTxtUsuario().getText());
                String pass = DigestUtils.sha3_256Hex(new String(vista.getTxtContrasena().getPassword()));
                usuario.setContrasena(pass);

                try {
                    Usuario user = dao.mostrarPorId(usuario);

                    if (user != null
                            && user.getId().equals(usuario.getId())
                            && user.getContrasena().equals(usuario.getContrasena())) {

                        if (!guardarSesion(user)) {
                            return;
                        }
                        var init = new VInit();
                        var cinit = new CInit(init);
                        init.setVisible(true);
                        vista.dispose();
                    } else {
                        vista.getTxtAdvertencia().setText("¡Usuario y/o Contraseña Inconrrectos!");
                    }
                } catch (SQLException ex) {

                    if (ex.getErrorCode() == 0 || ex.getErrorCode() == 1046) {

                        int r = JOptionPane.showConfirmDialog(vista, "¡Error con la conexión a la base de datos!\n"
                                + "¿Desea configurar conexión?.", "Sin conexión", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                        if (r == JOptionPane.OK_OPTION) {
                            VBaseDeDatos vbd = new VBaseDeDatos();
                            vbd.setVisible(true);
                            CConfiguracionDB cbd = new CConfiguracionDB(vbd, false);
                        }
                    } else {
                        JOptionPane.showMessageDialog(vista, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
        }

    }

    private boolean guardarSesion(Usuario user) {

        try {
            String path = System.getProperty("user.home") + File.separator + ".barber";
            File f = new File(path);
            if (!f.exists()) {
                f.mkdir();
            }
            path += File.separator + ".sesion";
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(user);
            oos.flush();
            oos.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private final VLogin vista;
    private final UsuarioDAO dao;

}
