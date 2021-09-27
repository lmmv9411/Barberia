package Controller;

import DAO.BarberoDAO;
import DAO.ServicioDAO;
import DAO.VentaDAO;
import DTO.*;
import Utilidades.ModeloTabla;
import Vista.VBuscar;
import Vista.VVenta;
import com.formdev.flatlaf.ui.FlatTextBorder;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;

public class CVenta extends Controlador<VVenta, Venta> implements IController.IOyente {

    private CVenta(VVenta vista) {
        super(vista);

        this.formatter = NumberFormat.getCurrencyInstance();
        this.formatter.setMaximumFractionDigits(0);
        vista.getTxtRecibido().setText(formatter.format(0));
        vista.getTxtDescuento().setText(formatter.format(0));

        dao = new DAO.VentaDAO();

        modelo = new ModeloTabla(new Object[]{
            "Id", "Servicio", "Precio", "Cantidad", "Total"
        });

        this.vista.getTblVentas().setRowHeight(40);
        this.vista.getTblVentas().setModel(modelo);
        ceroFormatedao = formatter.format(0);
    }

    public static CVenta getInstance(VVenta vista) {
        if (yo == null) {
            yo = new CVenta(vista);
        }
        return yo;
    }

    private static CVenta yo;

    @Override
    protected void iniciarVariables() {
        //iniciar rellenando barberos en combobox
        try {
            var daoBarberos = new BarberoDAO();

            for (Barbero barbero : daoBarberos.listar()) {
                vista.getChkBarbero().addItem(barbero);
            }

            var daoServicios = new ServicioDAO();

            for (Servicio servicio : daoServicios.listar()) {
                vista.getChkServicio().addItem(servicio);
            }

            usuario = Utilidades.Sesion.getUsuario();

        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }

        vista.getChkBarbero().setSelectedIndex(-1);
        vista.getChkServicio().setSelectedIndex(-1);
    }

    @Override
    protected void iniciarEventos() {
        vista.getBtnAgregar().addActionListener(this::eventos);
        vista.getBtnGuardar().addActionListener(this::eventos);

        vista.getBtnGuardar().setMnemonic('g');
        vista.getBtnImprimir().setMnemonic('i');
        vista.getBtnBuscarCliente().setMnemonic('c');

        vista.getBtnCancelar().addActionListener(this::eventos);
        vista.getBtnEliminar().addActionListener(this::eventos);
        vista.getBtnImprimir().addActionListener(this::eventos);
        vista.getTxtDescuento().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                String regex = "^(\\d+|\\s?)$";
                String descuento = vista.getTxtDescuento().getText();

                if (!descuento.matches(regex)) {
                    e.consume();
                    vista.getTxtDescuento().setText(descuento.substring(0, descuento.length() - 1));
                    return;
                }

                int subtotal = 0;
                int recibido = 0;
                try {
                    subtotal = formatter.parse(vista.getTxtSubtotal().getText()).intValue();
                    recibido = formatter.parse(vista.getTxtRecibido().getText()).intValue();
                } catch (ParseException ex) {

                }

                descuento = descuento.equals("") ? "0" : descuento;
                int r = subtotal - Integer.valueOf(descuento);
                int cambio = recibido - r;

                if (!descuento.equals("")) {
                    String txt = formatter.format(r);
                    vista.getTxtTotal().setText(txt);
                } else {
                    vista.getTxtTotal().setText(vista.getTxtSubtotal().getText());
                }

                String cambioStr = formatter.format(cambio);
                vista.getTxtCambio().setText(cambioStr);

            }

        });

        vista.getTxtRecibido().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String regex = "^(\\d+|\\s?)$";
                String recibido = vista.getTxtRecibido().getText();

                if (!recibido.matches(regex)) {
                    e.consume();
                    vista.getTxtRecibido().setText(recibido.substring(0, recibido.length() - 1));
                    return;
                }

                int total = 0;
                String cambioStr;

                try {
                    total = formatter.parse(vista.getTxtTotal().getText()).intValue();
                } catch (ParseException ex) {

                }

                int recibidoF = 0;
                try {
                    recibidoF = formatter.parse(vista.getTxtRecibido().getText()).intValue();
                } catch (ParseException ex) {
                    try {
                        recibidoF = Integer.parseInt(vista.getTxtRecibido().getText());
                    } catch (NumberFormatException exe) {

                    }
                }

                if (total == 0) {
                    recibidoF *= -1;
                    vista.getTxtCambio().setText(formatter.format(recibidoF));
                } else {

                    int cambio = recibidoF - total;
                    if (cambio < 0) {
                        vista.getTxtCambio().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    } else {
                        vista.getTxtCambio().setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
                    }
                    cambioStr = formatter.format(cambio);
                    vista.getTxtCambio().setText(cambioStr);
                }
            }
        });

        vista.getBtnBuscarCliente().addActionListener(e -> {
            if (vc == null || vc.isClosed()) {
                vc = new VBuscar();
                var cv = new CClienteVista(vc, this, vista.getParent());
                vista.getParent().remove(vc);
                vista.getParent().add(vc);
                vc.setVisible(true);
            }
            try {
                vc.setSelected(true);
            } catch (PropertyVetoException ex) {
                System.out.println(ex);
            }
        });

        vista.getTblVentas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (vista.getTblVentas().getSelectedRow() != -1) {
                        int fila = vista.getTblVentas().getSelectedRow();

                        var servicio = new Servicio((int) modelo.getValueAt(fila, 0), 0, "", false);
                        vista.getChkServicio().setSelectedItem(servicio);
                        vista.getTxtPrecioServicio().setText(modelo.getValueAt(fila, 2) + "");
                        vista.getJsCantidad().setValue(modelo.getValueAt(fila, 3));
                        modelo.eliminarFila(fila);
                        calcularTotal();
                        vista.getBtnAgregar().setText("Actualizar");
                    }
                }
            }
        });

        vista.getTxtRecibido().addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (vista.getTxtRecibido().getText().equals(ceroFormatedao)) {
                    vista.getTxtRecibido().setText("");
                    return;
                }

                try {
                    int r = formatter.parse(vista.getTxtRecibido().getText()).intValue();
                    vista.getTxtRecibido().setText(r + "");
                } catch (ParseException ex) {

                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (vista.getTxtRecibido().getText().equals("")) {
                    vista.getTxtRecibido().setText(ceroFormatedao);
                    return;
                }

                int n = Integer.parseInt(vista.getTxtRecibido().getText());
                String r = formatter.format(n);
                vista.getTxtRecibido().setText(r);
            }
        });

        vista.getTxtDescuento().addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (vista.getTxtDescuento().getText().equals(ceroFormatedao)) {
                    vista.getTxtDescuento().setText("");
                    return;
                }

                try {
                    int r = formatter.parse(vista.getTxtDescuento().getText()).intValue();
                    vista.getTxtDescuento().setText(r + "");
                } catch (ParseException ex) {

                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (vista.getTxtDescuento().getText().equals("")) {
                    vista.getTxtDescuento().setText(ceroFormatedao);
                    return;
                }

                int n = Integer.parseInt(vista.getTxtDescuento().getText());
                String r = formatter.format(n);
                vista.getTxtDescuento().setText(r);
            }
        });

        vista.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                vc.dispose();
                yo = null;
            }

        });

        vista.getChkServicio().addItemListener(e -> {
            var servicio = (Servicio) e.getItem();
            vista.getTxtPrecioServicio().setText(formatter.format(servicio.getPrecio()));
        });
    }

    @Override
    protected void eventos(java.awt.event.ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Agregar":
            case "Actualizar":

                int precioInt = 0;

                try {
                    precioInt = formatter.parse(vista.getTxtPrecioServicio().getText()).intValue();
                } catch (ParseException ex) {
                    System.out.println(ex);
                }

                int cantInt = Integer.parseInt(vista.getJsCantidad().getValue().toString());
                int montoInt = precioInt * cantInt;

                String precio = formatter.format(precioInt);
                String monto = formatter.format(montoInt);

                rowData = new Object[]{
                    ((Servicio) vista.getChkServicio().getSelectedItem()).getId(),
                    ((Servicio) vista.getChkServicio().getSelectedItem()).getNombre(),
                    precio,
                    cantInt,
                    monto
                };

                agregarFila(rowData);

                calcularTotal();

                vista.getJsCantidad().setValue(1);
                vista.getBtnAgregar().setText("Agregar");
                vista.getTblVentas().clearSelection();
                vista.getChkServicio().setSelectedIndex(-1);
                vista.getTxtPrecioServicio().setText("");

                break;
            case "Guardar":
            case "Imprimir":
                facturar(e.getActionCommand());
                break;
            case "Eliminar":

                if (vista.getTblVentas().getSelectedRow() != -1) {
                    modelo.eliminarFila(vista.getTblVentas().getSelectedRow());
                }
                calcularTotal();
                break;
            case "Cancelar":
                limpiar();
                break;
        }
    }

    private void calcularTotal() {
        int subtotal = 0, m = 0;

        for (var v : modelo.getData()) {
            try {
                m = formatter.parse(v[4].toString()).intValue();
            } catch (ParseException ex) {
                mostrarMensajeError(-1, ex.getMessage());
                m = 0;
            }
            subtotal += m;
        }

        var descuento = 0;
        var recibido = 0;
        try {
            descuento = formatter.parse(vista.getTxtDescuento().getText()).intValue();
            recibido = formatter.parse(vista.getTxtRecibido().getText()).intValue();
        } catch (ParseException ex) {

        }
        int total = subtotal - descuento;
        int cambio = recibido - total;

        if (cambio < 0) {
            vista.getTxtCambio().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        } else {
            vista.getTxtCambio().setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        }

        vista.getTxtTotal().setText(formatter.format(total));
        vista.getTxtSubtotal().setText(formatter.format(subtotal));
        vista.getTxtCambio().setText(formatter.format(cambio));
    }

    @Override
    protected boolean verificarControles() {

        if (vista.getTxtIdCliente().getText().equals("")) {
            mostrarMensajeError(-1, "! Id Cliente Vacío!");
            vista.getTxtIdCliente().requestFocus();
            return false;
        }

        if (vista.getChkBarbero().getSelectedIndex() == -1) {
            mostrarMensajeError(-1, "¡Seleccione Un Barbero!");
            return false;
        }

        if (vista.getTxtDescuento().getText().equals("")) {
            vista.getTxtDescuento().setText(formatter.format(0));
        }

        if (vista.getTxtRecibido().getText().equals("")) {
            vista.getTxtRecibido().setText(formatter.format(0));
        }

        return true;
    }

    @Override
    protected void limpiar() {

        vista.getChkBarbero().setSelectedIndex(-1);
        vista.getChkServicio().setSelectedIndex(-1);

        super.limpiar();
        vista.getTxtDescuento().setText(formatter.format(0));
        vista.getTxtRecibido().setText(formatter.format(0));
        vista.getTxtTotal().setText(formatter.format(0));
        vista.getTxtSubtotal().setText(formatter.format(0));
        modelo.eliminarFilas();
        vista.getTxtCambio().setBorder(new FlatTextBorder());

    }

    private void facturar(String operacion) {
        if (!verificarControles()) {
            return;
        }
        if (modelo.getRowCount() == 0) {
            mostrarMensajeError(-1, "!No ha ingresado productos!");
            return;
        }

        int recibidoInt;
        int descuentoInt;
        int totalInt;

        try {
            recibidoInt = formatter.parse(vista.getTxtRecibido().getText()).intValue();
            descuentoInt = formatter.parse(vista.getTxtDescuento().getText()).intValue();
            totalInt = formatter.parse(vista.getTxtTotal().getText()).intValue();
        } catch (ParseException ex) {
            mostrarMensajeError(-1, ex.getMessage());
            return;
        }

        int estado = 0;

        if (recibidoInt < totalInt) {

            int r = JOptionPane.showConfirmDialog(vista,
                    "El monto recibido es menor al total"
                    + "\n¿Desea continuar?",
                    "Diferencias de saldo",
                    JOptionPane.WARNING_MESSAGE);
            if (r == JOptionPane.CANCEL_OPTION) {
                return;
            }

            estado = 2;
        }

        try {

            Cliente cliente = new Cliente();
            cliente.setId(Integer.parseInt(vista.getTxtIdCliente().getText()));

            java.sql.Timestamp fecha = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());

            var barbero = (Barbero) vista.getChkBarbero().getSelectedItem();

            Venta venta = new Venta(0,
                    descuentoInt,
                    recibidoInt,
                    totalInt,
                    cliente,
                    usuario,
                    barbero,
                    fecha,
                    new DTO.EstadoFactura(estado, null));

            //Aquí se realiza la incerción en la tabla ventas y se obtine el id autoincremental.
            venta.setId(((VentaDAO) dao).crearAI(venta));

            //Aquí comienza la recolección de los datos de la tabla e inserción en 
            //tbl_detalles_venta
            DetalleVenta dv = null;
            Servicio servicio = null;
            List<DetalleVenta> dvs = new ArrayList<>();

            for (int i = 0; i < modelo.getRowCount(); i++) {

                var idServicio = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                servicio = new Servicio();
                servicio.setId(idServicio);

                var precioServicio = formatter.parse(modelo.getValueAt(i, 2).toString()).intValue();

                var cantidadServicio = Integer.parseInt(modelo.getValueAt(i, 3).toString());

                dv = new DetalleVenta(venta, servicio, cantidadServicio, precioServicio);

                dvs.add(dv);
            }

            ((VentaDAO) dao).crearDetalleVenta(dvs);

            if (operacion.equals("Imprimir")) {
                reportes.Imprimir print = new reportes.Imprimir();
                print.imprimirPorId(venta.getId(), "venta", "idFactura");
            } else {
                mostrarMensaje("Resultado", "¡Factura Registrada!");
            }

            limpiar();

        } catch (ParseException ex) {
            mostrarMensajeError(-1, ex.getMessage());
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
    }

    public void agregarFila(Object[] rowData) {

        int precioInt = 0;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            //Si ya existe en la tabla aumento cantidad
            if (modelo.getValueAt(i, 0).equals(rowData[0])) {
                try {
                    precioInt = formatter.parse(modelo.getValueAt(i, 2).toString()).intValue();
                } catch (ParseException ex) {
                    mostrarMensajeError(-1, ex.getMessage());
                    return;
                }
                int cantidad = Integer.parseInt(modelo.getValueAt(i, 3).toString()) + Integer.parseInt(rowData[3] + "");
                String subtotalF = formatter.format(precioInt * cantidad);
                modelo.setValueAt(subtotalF, i, 4);
                modelo.setValueAt(cantidad, i, 3);
                calcularTotal();
                return;
            }
            //Fin de comprobación
        }

        modelo.setFila(rowData);
        calcularTotal();
    }

    @Override
    public void setObjeto(Object valor) {
        var cliente = (Cliente) valor;
        vista.getTxtIdCliente().setText(
                cliente.getId() + ""
        );
        vista.getTxtNombreCliente().setText(
                cliente.getNombre() + " " + cliente.getNombreii() + " "
                + cliente.getApellido() + " " + cliente.getApellidoii()
        );
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            System.out.println(ex);
        }
    }

    private Object[] rowData;
    private final NumberFormat formatter;
    private Usuario usuario;
    private final String ceroFormatedao;
    private VBuscar vc;

}
