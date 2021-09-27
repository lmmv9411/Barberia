package Controller;

import DAO.VentaPorCobrar;
import DTO.VentasPorCobrar;
import Utilidades.ControladorPaginacion;
import Utilidades.ModeloTabla;
import Utilidades.Paginacion;
import Vista.VVentaPorCobrar;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.event.InternalFrameEvent;

public class CVentasPorCobrar extends Controlador<VVentaPorCobrar, VentasPorCobrar>
        implements Paginacion {

    private CVentasPorCobrar(VVentaPorCobrar vista) {
        super(vista);

        dao = new VentaPorCobrar();

        modelo = new ModeloTabla(new Object[]{
            "Id", "Fecha", "Descuento", "Abonado", "Total", "Id Cliente", "Cliente",
            "Id Barbero", "Barbero", "Id Cajero", "Cajero", "Estado"
        });

        vista.getTblVentas().setModel(modelo);
        controladorPaginacion = new ControladorPaginacion(this, vista.getPanelPaginacion());
        vista.getJsFilas().setValue(controladorPaginacion.getFILASAMOSTRAR());
        vista.getJsPaginas().setValue(controladorPaginacion.getPAGINASAMOSTRAR());

        vista.getPanelDetalles().setVisible(false);

        modeloDetalles = new ModeloTabla(new Object[]{
            "#Factura", "Fecha", "Abonado", "Id Usuario", "Usuario"
        });

        vista.getTblDetalles().setModel(modeloDetalles);

    }

    public static CVentasPorCobrar getInstance(VVentaPorCobrar vista) {
        if (yo == null) {
            yo = new CVentasPorCobrar(vista);
        }
        return yo;
    }

    private static CVentasPorCobrar yo;

    @Override
    protected void iniciarEventos() {
        vista.getBtnBuscar().addActionListener(e -> paginar(1, controladorPaginacion.getFILASAMOSTRAR()));
        vista.getBtnBuscar().setMnemonic('b');

        vista.getJsFilas().addChangeListener(e
                -> controladorPaginacion.setFILASAMOSTRAR(
                        (int) vista.getJsFilas().getValue()));

        vista.getJsPaginas().addChangeListener(e
                -> controladorPaginacion.setPAGINASAMOSTRAR(
                        (int) vista.getJsPaginas().getValue()));

        vista.getTblVentas().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                var row = vista.getTblVentas().getSelectedRow();

                if (e.getClickCount() == 2
                        && row != -1) {

                    var fila = modelo.getValorFila(row);

                    vista.getTxtNFactura().setText(fila[0] + "");
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                    vista.getTxtFecha().setText(df.format(fila[1]));
                    vista.getTxtIdCliente().setText(fila[5] + "");
                    vista.getTxtCliente().setText(fila[6] + "");
                    vista.getTxtTotalFactura().setText(fila[4] + "");
                    vista.getTxtAbonado().setText(fila[3] + "");
                }
            }

        });

        vista.getBtnActualizar().addActionListener(e -> {

            if (!verificarControles()) {
                return;
            }

            var venta = new VentasPorCobrar();

            venta.setId(Integer.parseInt(vista.getTxtNFactura().getText()));
            var fecha = java.time.LocalDateTime.now();
            venta.setFecha(java.sql.Timestamp.valueOf(fecha));
            venta.setMonto_recibido(Integer.parseInt(vista.getTxtAbonar().getText()));
            venta.setUsuario(Utilidades.Sesion.getUsuario());
            venta.setMontoAbonado(Integer.parseInt(vista.getTxtAbonado().getText()));
            venta.setTotal(Integer.parseInt(vista.getTxtTotalFactura().getText()));

            try {
                ((VentaPorCobrar) dao).crear(venta, null);
                mostrarMensaje("Abonado", "Abono creado!");
                limpiar();
                modelo.eliminarFilas();
            } catch (SQLException ex) {
                mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
                dao.cerrarConexion();
            }
        });

        vista.getTxtAbonar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        vista.getBtnDetalles().addActionListener(e -> {

            var row = vista.getTblVentas().getSelectedRow();

            if (row == -1) {
                return;
            }

            var id = (int) modelo.getValorFila(row)[0];
            modeloDetalles.eliminarFilas();
            try {

                modeloDetalles.setData(((VentaPorCobrar) dao).verDetalleCobro(id));
                vista.getPanelDetalles().setVisible(true);

            } catch (SQLException ex) {
                mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
                dao.cerrarConexion();
            }
        });

        vista.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                yo = null;
            }

        });
    }

    @Override
    public void paginar(int pag, int nPag) {
        vista.getPanelDetalles().setVisible(false);

        try {

            if (vista.getJrbFactura().isSelected()) {
                params[0] = vista.getTxtBuscar().getText();
            } else {
                params[1] = vista.getTxtBuscar().getText();
            }

            var lista = ((VentaPorCobrar) dao).listar(
                    params,
                    pag,
                    nPag,
                    vista.getChkAsc().isSelected());

            modelo.eliminarFilas();
            modelo.setData(lista);

            Utilidades.Tabla.resizeColumnWidth(vista.getTblVentas());
            controladorPaginacion.crearBotones(pag);

        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }
    }

    @Override
    public int getSize() {
        int r = 0;
        try {
            r = ((VentaPorCobrar) dao).getSize(params);
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }
        return r;
    }

    @Override
    protected void eventos(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean verificarControles() {

        if (vista.getTxtNFactura().getText().equals("")) {
            mostrarMensaje("Control Vacío", "!No ha seleccinado ninguna factura!");
            return false;
        }

        if (vista.getTxtAbonar().getText().equals("")) {
            mostrarMensaje("Control Vacío", "Ingresar Monto");
            vista.getTxtAbonar().requestFocus();
            return false;
        }
        return true;
    }

    private final String[] params = new String[]{"", ""};
    private final ModeloTabla modeloDetalles;

}
