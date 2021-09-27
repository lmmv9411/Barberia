package Controller;

import DAO.ProductoDAO;
import DTO.Producto;
import DTO.Unidad;
import IController.IOyente;
import Vista.VBuscar;
import Vista.VProducto;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class CProductos extends Controlador<VProducto, Producto> implements IOyente {

    public CProductos(VProducto vista) {
        super(vista);
    }

    @Override
    protected void iniciarVariables() {
        dao = new ProductoDAO();
        try {
            for (Unidad und : ((ProductoDAO) dao).getUnidades()) {
                vista.getCmbUnidad().addItem(und);
            }
        } catch (SQLException ex) {
            dao.cerrarConexion();
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
    }

    @Override
    protected void iniciarEventos() {
        vista.getBtnGuardar().addActionListener(this::eventos);
        vista.getBtnBuscar().addActionListener(this::eventos);
        vista.getBtnLimpiar().addActionListener(this::eventos);
        vista.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                if (vbp != null) {
                    vbp.dispose();
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

                    dao.crear(getProducto());

                    mostrarMensaje("Resultado", "¡Producto Creado!");

                    limpiar();

                    break;
                case "Buscar":
                    if (vbp == null || vbp.isClosed()) {
                        vbp = new VBuscar();
                        vbp.setVisible(true);
                        vista.getParent().remove(vbp);
                        vista.getParent().add(vbp);
                        var cpv = new CProductoVista(vbp, this);
                    }
                    try {
                        vbp.setSelected(true);
                    } catch (PropertyVetoException ex) {
                        System.out.println(ex);
                    }
                    break;
                case "Actualizar":

                    if (verificarControles()) {
                        return;
                    }

                    dao.Actualizar(getProducto());

                    mostrarMensaje("Resultado", "¡Producto Actualizado!");

                    limpiar();

                    break;
                case "Limpiar":
                    limpiar();
                    break;
            }
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
    }

    @Override
    protected boolean verificarControles() {

        for (java.awt.Component component : vista.getPnlDatos().getComponents()) {
            if (component instanceof javax.swing.JTextField) {
                javax.swing.JTextField txt = (javax.swing.JTextField) component;
                if (txt.getText().equals("") && txt.isEditable()) {
                    mostrarMensajeError(-1, txt.getName() + " está vacío!");
                    txt.requestFocus();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void limpiar() {
        super.limpiar();
        vista.getJsCantidad().setValue(0);
        vista.getCmbUnidad().setSelectedIndex(0);
        vista.getTxtIdProducto().requestFocus();
        vista.getBtnGuardar().setText("Guardar");
    }

    @Override
    public void setObjeto(Object valor) {
        var producto = (Producto) valor;
        vista.getBtnGuardar().setText("Actualizar");
        vista.getTxtIdProducto().setText(producto.getId());
        vista.getTxtNombre().setText(producto.getNombre());
        vista.getJsCantidad().setValue(producto.getCantidad());
        vista.getCmbUnidad().setSelectedItem(producto.getUnidad());
        vista.getJsMinimo().setValue(producto.getMinimo());
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            System.out.println(ex);
        }
    }

    private Producto getProducto() {
        return new Producto(
                vista.getTxtIdProducto().getText(),
                vista.getTxtNombre().getText(),
                (Unidad) vista.getCmbUnidad().getSelectedItem(),
                (Integer) vista.getJsCantidad().getValue(),
                (Integer) vista.getJsMinimo().getValue()
        );
    }

    private VBuscar vbp;

}
