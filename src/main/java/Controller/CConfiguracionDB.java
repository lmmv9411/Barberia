package Controller;

import DAO.Conexion;
import Vista.VBaseDeDatos;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;

public class CConfiguracionDB {

    public CConfiguracionDB(VBaseDeDatos vista, boolean camposVisibles) {
        this.vista = vista;
        preferences = Preferences.systemRoot();

        if (camposVisibles) {
            this.vista.getTxtUrl().setText(preferences.get("cndbur", ""));
            this.vista.getTxtNombreDB().setText(preferences.get("cndbdb", ""));
            this.vista.getTxtUsuario().setText(preferences.get("cndbus", ""));
            this.vista.getTxtPass().setText(preferences.get("cndbps", ""));
        } 

        java.awt.Dimension size = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        int x = (size.width - this.vista.getSize().width) / 2;
        int y = (size.height - this.vista.getSize().height) / 2;

        this.vista.setLocation(x, y);

        iniciarEventos();
    }

    private void iniciarEventos() {
        vista.getBtnTest().addActionListener(this::eventos);
        vista.getBtnGuardar().addActionListener(this::eventos);
        vista.getVerPass().addActionListener((e) -> {
            if (vista.getVerPass().isSelected()) {
                vista.getTxtPass().setEchoChar((char) 0);
            } else {
                vista.getTxtPass().setEchoChar('*');
            }
        });
        
    }

    private void eventos(ActionEvent e) {

        if (checkControlesVacios(vista.getContentPane())) {
            JOptionPane.showMessageDialog(vista, "!Controles Vacíos!", "", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (e.getActionCommand()) {
            case "Guardar":
                preferences.put("cndbur", vista.getTxtUrl().getText());
                preferences.put("cndbdb", vista.getTxtNombreDB().getText());
                preferences.put("cndbus", vista.getTxtUsuario().getText());
                preferences.put("cndbps", new String(vista.getTxtPass().getPassword()));
                JOptionPane.showMessageDialog(vista, "!Guardado!", "", JOptionPane.INFORMATION_MESSAGE);

                break;

            case "Test":
                con = new Conexion(vista.getTxtUrl().getText(),
                        vista.getTxtNombreDB().getText(),
                        vista.getTxtUsuario().getText(),
                        new String(vista.getTxtPass().getPassword()));
                try {
                    con.conectar();
                    if(con.getConexion().isClosed()){
                        JOptionPane.showMessageDialog(vista, "Error al conectar", "", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    con.cerrarConexion();
                    JOptionPane.showMessageDialog(vista, "Conectado", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(vista, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                }
                break;
            
        }
    }

    private boolean checkControlesVacios(Object container) {

        for (java.awt.Component component : ((java.awt.Container) container).getComponents()) {
            if (component instanceof javax.swing.JPanel) {
                return checkControlesVacios(component);
            } else if (component instanceof javax.swing.JTextField
                    && !(component instanceof javax.swing.JPasswordField)) {
                if (((javax.swing.JTextField) component).getText().equals("")) {
                    return true;
                }
            }
        }

        return false;
    }

    private Conexion con;
    private final VBaseDeDatos vista;
    private final Preferences preferences;

}
