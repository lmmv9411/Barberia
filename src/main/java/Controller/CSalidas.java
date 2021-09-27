package Controller;

import DAO.ProductoDAO;
import DTO.Producto;
import IController.IOyente;
import Vista.VBuscar;
import Vista.VSalidasProductos;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

public class CSalidas extends Controlador<VSalidasProductos, Producto> implements IOyente {

    public CSalidas(VSalidasProductos vista) {
        super(vista);
        dao = new ProductoDAO();
    }

    @Override
    protected void iniciarEventos() {
        vista.getBtnBuscar().addActionListener(this::eventos);
    }

    @Override
    protected void eventos(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Buscar":
                if (buscar == null || buscar.isClosed()) {
                    buscar = new VBuscar();
                    buscar.setVisible(true);
                    vista.getParent().remove(buscar);
                    vista.getParent().add(buscar);
                    var buscador = new CProductoVista(buscar, this);
                }
                try {
                    buscar.setSelected(true);
                } catch (PropertyVetoException ex) {
                    System.out.println(ex);
                }
                break;
        }
    }

    @Override
    public void setObjeto(Object valor) {

        var producto = (Producto) valor;
        vista.getTxtIdProducto().setText(producto.getId());
        vista.getTxtProducto().setText(producto.getNombre());
        vista.getTxtStock().setText(producto.getCantidad() + "");
        try {
            vista.setSelected(true);
            vista.getTxtCosto().setText(((ProductoDAO) dao).getCosto(producto) + "");
        } catch (PropertyVetoException | SQLException ex) {
            System.out.println(ex);
        }
    }

    private VBuscar buscar;
}
