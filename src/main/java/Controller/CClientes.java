package Controller;

import DAO.ClienteDAO;
import DTO.Cliente;
import Vista.VBuscar;
import Vista.VCliente;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class CClientes extends Controlador<VCliente, Cliente> implements IController.IOyente {

    public CClientes(VCliente vista, boolean buscar) {
        super(vista);

        dao = new ClienteDAO();

        if (!buscar) {
            vista.getBtnBuscar().setVisible(false);
        }

    }

    @Override
    protected void iniciarEventos() {

        vista.getBtnBuscar().addActionListener(this::eventos);
        vista.getBtnGuardar().addActionListener(this::eventos);
        vista.getBtnReset().addActionListener(this::eventos);

        vista.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                if (vc != null) {
                    vc.dispose();
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
    protected void eventos(java.awt.event.ActionEvent e) {

        try {
            switch (e.getActionCommand()) {
                case "Buscar":
                    if (vc == null || vc.isClosed()) {
                        vc = new VBuscar();
                        var cv = new CClienteVista(vc, this, vista.getParent());
                        vista.getParent().remove(vc);
                        vista.getParent().add(vc);
                        vc.setVisible(true);
                        vc.getBtnNuevo().setVisible(false);
                    }
                    try {
                        vc.setSelected(true);
                    } catch (PropertyVetoException ex) {
                        System.out.println(ex);
                    }
                    break;
                case "Guardar":
                    if (!verificarControles()) {
                        return;
                    }
                    dao.crear(getCliente());
                    mostrarMensaje("Resultado", "¡Cliente Creado!");
                    limpiar();
                    break;

                case "Limpiar":
                    limpiar();
                    break;

                case "Actualizar":
                    if (!verificarControles()) {
                        return;
                    }
                    dao.Actualizar(getCliente());
                    mostrarMensaje("Resultado", "¡Cliente Actualizado!");
                    limpiar();
                    vista.getBtnGuardar().setText("Guardar");
                    tmpId = 0;
                    break;

            }
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }
    }

    private Cliente getCliente() {
        Cliente cliente = new Cliente(tmpId,
                vista.getTxtNombre().getText().trim(),
                vista.getTxtNombreII().getText().trim(),
                vista.getTxtApellido().getText().trim(),
                vista.getTxtApellidoII().getText().trim(),
                vista.getTxtTelefono().getText().trim(),
                vista.getTxtEmail().getText().trim());
        return cliente;
    }

    @Override
    protected boolean verificarControles() {

        if (vista.getTxtNombre().getText().equals("")) {
            mostrarMensajeError(-1, "¡Rellene el campo Nombre!");
            vista.getTxtNombre().requestFocus();
            return false;
        }
        return true;
    }

    private int tmpId = 0;

    @Override
    public void setObjeto(Object valor) {
        var cliente = (Cliente) valor;
        vista.getBtnGuardar().setText("Actualizar");
        tmpId = cliente.getId();
        vista.getTxtNombre().setText(cliente.getNombre());
        vista.getTxtNombreII().setText(cliente.getNombreii());
        vista.getTxtApellido().setText(cliente.getApellido());
        vista.getTxtApellidoII().setText(cliente.getApellidoii());
        vista.getTxtTelefono().setText(cliente.getTelefono());
        vista.getTxtEmail().setText(cliente.getEmail());
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            System.out.println(ex);
        }
    }

    private VBuscar vc;


}
