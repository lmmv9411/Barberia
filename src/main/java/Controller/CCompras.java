package Controller;

import DAO.ComprasDAO;
import DTO.Compra;
import DTO.DetalleCompra;
import DTO.Producto;
import DTO.Proveedor;
import DTO.Usuario;
import Utilidades.ModeloTabla;
import Vista.VBuscar;
import Vista.VCompras;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class CCompras extends Controlador<VCompras, Compra> implements IController.IOyente {

    public CCompras(VCompras vista) {
        super(vista);

        modelo = new ModeloTabla(new Object[]{
            "Id", "Producto", "Unidad Medida", "Costo", "Cantidad", "Subtotal"
        });

        vista.getTblCompras().setModel(modelo);
        this.formatter = NumberFormat.getCurrencyInstance();
        this.formatter.setMaximumFractionDigits(0);
        dao = new ComprasDAO();
        USUARIO = Utilidades.Sesion.getUsuario();
    }

    @Override
    protected void iniciarEventos() {
        vista.getBtnBuscarProveedor().addActionListener(e -> {
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
        });
        vista.getBtnBuscarProducto().addActionListener(e -> {
            if (vcpr == null || vcpr.isClosed()) {
                vcpr = new VBuscar();
                vista.getParent().remove(vcpr);
                vista.getParent().add(vcpr);
                CProductoVista ccp = new CProductoVista(vcpr, this);
                vcpr.setVisible(true);
            }
            try {
                vcpr.setSelected(true);
            } catch (PropertyVetoException ex) {
                System.out.println(ex);
            }
        });

        vista.getTblCompras().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r = vista.getTblCompras().getSelectedRow();
                if (e.getClickCount() == 2
                        && r != -1) {
                    vista.getTxtIdProducto().setText(modelo.getValorFila(r)[0] + "");
                    vista.getTxtNombreProducto().setText(modelo.getValorFila(r)[1] + "");
                    vista.getTxtUnidadMedida().setText(modelo.getValorFila(r)[2] + "");
                    vista.getTxtPrecioProducto().setText(modelo.getValorFila(r)[3] + "");
                    vista.getJsCantidad().setValue(modelo.getValorFila(r)[4]);
                    vista.getBtnActualizar().setText("Actualizar");
                    modelo.eliminarFila(r);
                    calcularTotal();
                }
            }

        });

        vista.getTxtFactura().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }

        });

        vista.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                if (vcp != null) {
                    vcp.dispose();
                }
                if (vcpr != null) {
                    vcpr.dispose();
                }
            }
        });

        vista.getBtnEliminar().addActionListener(this::eventos);
        vista.getBtnActualizar().addActionListener(this::eventos);
        vista.getBtnGuardar().addActionListener(this::eventos);
        vista.getBtnCancelar().addActionListener(this::eventos);

    }

    @Override
    protected void eventos(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Eliminar":
                int r = vista.getTblCompras().getSelectedRow();
                if (r == -1) {
                    return;
                }
                modelo.eliminarFila(r);
                calcularTotal();
                break;
            case "Agregar":
            case "Actualizar":
                if (verificarProductoVacio()) {
                    return;
                }
                int cant = Integer.parseInt(vista.getJsCantidad().getValue() + "");

                int costo;

                try {
                    costo = formatter.parse(vista.getTxtPrecioProducto().getText()).intValue();
                } catch (ParseException ex) {
                    try {
                        costo = Integer.parseInt(vista.getTxtPrecioProducto().getText());
                    } catch (NumberFormatException exe) {
                        costo = 0;
                    }
                }

                Object[] fila = {
                    vista.getTxtIdProducto().getText(),
                    vista.getTxtNombreProducto().getText(),
                    vista.getTxtUnidadMedida().getText(),
                    formatter.format(costo),
                    cant,
                    formatter.format(cant * costo)
                };

                vista.getBtnActualizar().setText("Agregar");
                agregarProducto(fila);
                limpiarPanelProducto();
                break;
            case "Guardar":

                if (verificarControles()) {
                    return;
                }

                java.sql.Timestamp fecha = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());

                double total = 0;

                try {
                    total = formatter.parse(vista.getTxtTotal().getText()).doubleValue();
                } catch (ParseException ex) {
                    mostrarMensajeError(-1, ex.getMessage());
                }

                var proveedor = new Proveedor();
                proveedor.setId(vista.getTxtIdProveedor().getText());

                Compra compra = new Compra(0,
                        vista.getTxtFactura().getText(),
                        proveedor,
                        total,
                        USUARIO,
                        fecha
                );

                try {
                    int id = ((ComprasDAO) dao).crearAI(compra);

                    java.util.List<DetalleCompra> ldc = new java.util.LinkedList<>();

                    for (var f : modelo.getData()){
                        var producto = new Producto();
                        producto.setId(f[0] + "");
                        var dc = new DetalleCompra(id, producto, (int) f[4],
                                formatter.parse(f[3] + "").doubleValue());
                        ldc.add(dc);
                    }

                    ((ComprasDAO) dao).crearDetalleCompra(ldc);
                    ((ComprasDAO) dao).actualizarStock(ldc);

                    mostrarMensaje("Resultado", "¡Compra Ingresada!");
                    limpiarPanelProveedor();
                    limpiarPanelProducto();

                } catch (SQLException ex) {
                    mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
                    dao.cerrarConexion();
                } catch (ParseException ex) {
                    mostrarMensajeError(-1, ex.getMessage());
                }

                break;
            case "Cancelar":
                limpiarPanelProducto();
                limpiarPanelProveedor();
                break;

        }
    }

    public void agregarProducto(Object[] fila) {

        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (modelo.getValueAt(i, 0).equals(fila[0])) {
                try {
                    int cant = Integer.parseInt(modelo.getValueAt(i, 3) + "")
                            + Integer.parseInt(fila[3] + "");
                    int costo = formatter.parse(modelo.getValueAt(i, 2) + "").intValue();
                    int subt = cant * costo;
                    modelo.setValueAt(formatter.format(subt), i, 4);
                    modelo.setValueAt(cant, i, 3);
                    calcularTotal();
                    return;
                } catch (ParseException ex) {

                }
            }
        }

        modelo.setFila(fila);
        calcularTotal();
    }

    public void calcularTotal() {
        int total = 0;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            try {
                total += formatter.parse(modelo.getValueAt(i, 5) + "").intValue();
            } catch (ParseException ex) {

            }
        }
        vista.getTxtTotal().setText(formatter.format(total));
    }

    private void limpiarPanelProducto() {

        for (java.awt.Component component : vista.getJpProducto().getComponents()) {
            if (component instanceof javax.swing.JTextField) {
                ((javax.swing.JTextField) component).setText("");
            } else if (component instanceof javax.swing.JSpinner) {
                ((javax.swing.JSpinner) component).setValue(1);
            }
        }

    }

    private void limpiarPanelProveedor() {
        for (java.awt.Component component : vista.getJpProveedor().getComponents()) {
            if (component instanceof javax.swing.JTextField) {
                ((javax.swing.JTextField) component).setText("");
            }
        }

        for (int i = modelo.getRowCount() - 1; i > -1; i--) {
            modelo.eliminarFila(i);
        }

        vista.getTxtTotal().setText(formatter.format(0));

    }

    @Override
    protected boolean verificarControles() {
        for (java.awt.Component component : vista.getJpProveedor().getComponents()) {
            if (component instanceof javax.swing.JTextField) {
                javax.swing.JTextField txt = (javax.swing.JTextField) component;
                if (txt.getText().equals("")) {
                    mostrarMensajeError(-1, txt.getName() + " Está Vacío!");
                    ((javax.swing.JTextField) component).requestFocus();
                    return true;
                }
            }
        }

        if (modelo.getRowCount() < 1) {
            mostrarMensajeError(-1, "!No Ha Ingresado Datos Aún!");
            return true;
        }

        return false;
    }

    private boolean verificarProductoVacio() {
        for (java.awt.Component component : vista.getJpProducto().getComponents()) {
            if (component instanceof javax.swing.JTextField) {
                javax.swing.JTextField txt = (javax.swing.JTextField) component;
                if (txt.getText().equals("")) {
                    mostrarMensajeError(-1, txt.getName() + " Está Vacío!");
                    ((javax.swing.JTextField) component).requestFocus();
                    return true;
                }
            }
        }
        return false;
    }

    public void setProveedor(Proveedor proveedor) {
        vista.getTxtIdProveedor().setText(proveedor.getId());
        vista.getTxtNombreProveedor().setText(proveedor.getNombre());
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            System.out.println(ex);
        }
    }

    public void setProducto(Producto producto) {
        vista.getTxtIdProducto().setText(producto.getId());
        vista.getTxtNombreProducto().setText(producto.getNombre());
        vista.getTxtUnidadMedida().setText(producto.getUnidad().getNombre());
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            System.out.println(ex);
        }
    }

    private final Usuario USUARIO;
    private VBuscar vcp;
    private VBuscar vcpr;
    private final NumberFormat formatter;

    @Override
    public void setObjeto(Object valor) {
        if(valor instanceof Producto){
            setProducto((Producto) valor);
        }else{
            setProveedor((Proveedor) valor);
        }
    }
}
