package Controller;

import DAO.ICRUD;
import Utilidades.ControladorPaginacion;
import Utilidades.ModeloTabla;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public abstract class Controlador<T extends JInternalFrame, K> {

    public Controlador(T vista) {
        this.vista = vista;
        
        
        java.net.URL url = getClass().getResource("/images/barberia.png");

        try {
            java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(url);
            vista.setFrameIcon(new javax.swing.ImageIcon(image));
            
        } catch (java.io.IOException ex) {
            System.out.println(ex);
        }
        
        iniciarVariables();
        iniciarEventos();
    }

    protected void limpiar() {
        java.awt.Container container = vista.getContentPane();
        limpiarRecursivo(container);
    }

    private void limpiarRecursivo(Object container) {
        for (java.awt.Component component : ((java.awt.Container) container).getComponents()) {
            if (component instanceof javax.swing.JPanel) {
                limpiarRecursivo(component);
            } else if (component instanceof javax.swing.JTextField) {
                ((javax.swing.JTextField) component).setText("");
            }
        }
    }

    protected abstract void iniciarEventos();

    protected abstract void eventos(java.awt.event.ActionEvent e);

    protected void mostrarMensaje(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    
    protected void mostrarMensajeError(int error, String mensaje){
         switch(error){
            case 0:
                JOptionPane.showMessageDialog(vista, 
                        "¡Error con la conexión a la base de datos!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected boolean verificarControles() {

        return false;
    }

    protected void iniciarVariables(){
        
    }

    protected final T vista;
    protected ICRUD<K> dao;
    protected ModeloTabla modelo;
    protected ControladorPaginacion controladorPaginacion;

}
