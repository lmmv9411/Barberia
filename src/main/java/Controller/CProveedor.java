package Controller;

import DAO.ProveedorDAO;
import DTO.Proveedor;
import IController.IOyente;
import Vista.VBuscar;
import Vista.VProveedor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

public class CProveedor extends Controlador<VProveedor, Proveedor> implements IOyente {

    public CProveedor(VProveedor vista) {
        super(vista);
        dao = new ProveedorDAO();
    }

    @Override
    protected void iniciarEventos() {
        vista.getBtnGuardar().addActionListener(this::eventos);
        vista.getBtnBuscar().addActionListener(this::eventos);
        vista.getBtnLimpiar().addActionListener(this::eventos);
        vista.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                if (vcp != null) {
                    vcp.dispose();
                }
            }
        });

        vista.getTxtTelefono().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }

    @Override
    protected void eventos(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case "Guardar":
                    if (verificarControles()) {
                        return;
                    }

                    dao.crear(getProveedor());

                    mostrarMensaje("¡Éxito!", "¡Proveedor Ingresado!");
                    limpiar();

                    break;
                case "Buscar":
                    if (vcp == null || vcp.isClosed()) {
                        vcp = new VBuscar();
                        vista.getParent().remove(vcp);
                        vista.getParent().add(vcp);
                        CProveedorVista ccp = new CProveedorVista(vcp, this);
                        vcp.setVisible(true);
                    }
                    try {
                        vcp.setSelected(true);
                    } catch (PropertyVetoException ex) {
                        System.out.println(ex);
                    }
                    break;
                case "Actualizar":

                    dao.Actualizar(getProveedor());

                    mostrarMensaje("¡Éxito!", "¡Proveedor Actualizado!");

                    limpiar();

                    break;
                case "Limpiar":
                    limpiar();
                    break;
            }
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }
    }

    @Override
    protected boolean verificarControles() {

        for (java.awt.Component component : vista.getPanelDatos().getComponents()) {
            if (component instanceof javax.swing.JTextField) {
                javax.swing.JTextField txt = (javax.swing.JTextField) component;
                if (txt.getText().equals("")) {
                    mostrarMensaje("Control Vacío!", txt.getName() + " está vacío!");
                    txt.requestFocus();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void setObjeto(Object valor) {
        var proveedor = (Proveedor) valor;
        vista.getBtnGuardar().setText("Actualizar");
        vista.getTxtId().setText(proveedor.getId());
        vista.getTxtNombre().setText(proveedor.getNombre());
        vista.getTxtDireccion().setText(proveedor.getDireccion());
        vista.getTxtTelefono().setText(proveedor.getTelefono());
        vista.getTxtEmail().setText(proveedor.getEmail());
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            System.out.println(ex);
        }
    }

    private Proveedor getProveedor() {
        return new Proveedor(vista.getTxtId().getText(),
                vista.getTxtNombre().getText(),
                vista.getTxtTelefono().getText(),
                vista.getTxtDireccion().getText(),
                vista.getTxtEmail().getText());
    }

    @Override
    protected void limpiar() {
        super.limpiar();
        vista.getBtnGuardar().setText("Guardar");
    }

    private VBuscar vcp;

}
