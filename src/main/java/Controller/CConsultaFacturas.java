package Controller;

import IController.CConsultas;
import DAO.DetalleVentaDAO;
import DAO.VentaDAO;
import DTO.IndicesColumnas;
import DTO.Venta;
import Utilidades.ModeloTabla;
import Vista.VConsulta;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;

public final class CConsultaFacturas extends CConsultas<Venta> {

    public CConsultaFacturas(VConsulta vista) {
        super(vista);

        dao = new VentaDAO();

        var titulos = new String[]{
            "Id", "Fecha", "Descuento", "Monto Recibido", "Total", "Id Cliente",
            "Cliente", "Id Barbero", "Barbero", "Id Cajero", "Cajero", "Estado"
        };

        modelo = new ModeloTabla(titulos);

        var filtros
                = new String[][]{
                    {"Id", "n_factura"},
                    {"Id Cliente", "id_cliente"},
                    {"Cliente", "cliente"},
                    {"Id Barbero", "id_barbero"},
                    {"Barbero", "barbero"},
                    {"Id Cajero", "id_cajero"},
                    {"Cajero", "cajero"},
                    {"Estado", "estado"}
                };

        for (String[] clave : filtros) {
            vista.getCmbColumna().addItem(new IndicesColumnas(clave[1], clave[0]));
        }

        vista.getCmbColumna().setSelectedIndex(-1);

        modeloDetalles = new ModeloTabla(new Object[]{
            "Id", "Servicio", "Cantidad", "Precio", "Subtotal"
        });

        vista.getTblFacturas().setModel(modelo);
        vista.getTblDetalles().setModel(modeloDetalles);

        final TableColumnModel columnModel = vista.getTblFacturas().getColumnModel();
        for (int i = 0; i < vista.getTblFacturas().getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth((titulos[i] + "").length() * 10);
        }

    }

    @Override
    protected void eventos(ActionEvent e) {
        super.eventos(e);
        try {
            switch (e.getActionCommand()) {
                case "Mostrar Detalles":

                    DetalleVentaDAO dvdao = new DetalleVentaDAO();

                    id = (int) modelo.getValorFila(row)[0];
                    modeloDetalles.eliminarFilas();
                    modeloDetalles.setData(dvdao.listar(id));

                    break;

                case "Anular":

                    if (row == -1) {
                        return;
                    }
                    int r = JOptionPane.showInternalConfirmDialog(vista,
                            "¿Seguro desea eliminar factura "
                            + modelo.getValorFila(row)[0] + "?",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);

                    if (r == JOptionPane.CANCEL_OPTION) {
                        return;
                    }

                    String descripcion = "";

                    while ((descripcion = JOptionPane.showInputDialog(vista,
                            "Motivo de anulación:", "",
                            JOptionPane.PLAIN_MESSAGE)).equals("")) {

                    }

                    ((VentaDAO) dao).anular((int) modelo.getValorFila(row)[0],
                            descripcion,
                            usuario);

                    JOptionPane.showMessageDialog(vista,
                            "¡Factura Anulada!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                    modelo.eliminarFilas();

                    break;
                case "Imprimir":

                    if (row == -1) {
                        return;
                    }

                    id = (int) modelo.getValorFila(row)[0];
                    var estado = modelo.getValorFila(row)[11] + "";
                    if (estado.equals("anulada")) {
                        JOptionPane.showMessageDialog(vista,
                                "No puede imprimir factura anulada!",
                                "", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    reportes.Imprimir print = new reportes.Imprimir();
                    print.imprimirPorId(id, "ventas", "idFactura");
                    break;
            }
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }
    }

    @Override
    public void paginar(int pag, int nPag) {
        super.paginar(pag, nPag);
        try {

            modelo.eliminarFilas();

            asc = vista.getChkbAscendente().isSelected();

            modelo.setData(((VentaDAO) dao).filtrar(fechaInicial(),
                    fechaFinal(), pag, nPag, asc, col(), param()));

            Utilidades.Tabla.resizeColumnWidth(vista.getTblFacturas());

            controladorPaginacion.crearBotones(pag);

        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
    }

    @Override
    public int getSize() {
        try {
            return ((VentaDAO) dao).size(fechaInicial(), fechaFinal(), col(), param());
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
        return 0;
    }

    @Override
    public String tipo() {
        return "ventas";
    }

}
