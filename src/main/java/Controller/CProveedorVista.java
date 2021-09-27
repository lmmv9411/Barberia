package Controller;

import DAO.ProveedorDAO;
import DTO.Proveedor;
import Utilidades.ControladorPaginacion;
import Utilidades.ModeloTabla;
import Utilidades.Paginacion;
import Vista.VBuscar;
import Vista.VProveedor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class CProveedorVista extends Controlador<VBuscar, Proveedor> implements Paginacion {

    public CProveedorVista(VBuscar vista, IController.IOyente oyente) {
        super(vista);
        
        this.vista.setTitle("Buscar Proveedor");
        this.vista.getChkAscendente().setVisible(false);
        modelo = new ModeloTabla(new Object[]{
            "Id", "Nombre", "Teléfono", "Dirección", "Email"
        });

        controladorPaginacion = new ControladorPaginacion(this, this.vista.getPanelPaginacion());

        vista.getJsFilas().setValue(controladorPaginacion.getFILASAMOSTRAR());
        vista.getJsPaginas().setValue(controladorPaginacion.getPAGINASAMOSTRAR());

        vista.getTblBuscar().setModel(modelo);

        dao = new ProveedorDAO();

        vista.getPanelPaginacion().setLayout(new FlowLayout(FlowLayout.CENTER));

        this.oyente = oyente;

        verificarPermisos();
    }

    private void verificarPermisos() {
        var permisos = Utilidades.Sesion.getUsuario().getPermisos();
        vista.getBtnNuevo().setVisible(permisos.get("proveedor").contains("crear"));
    }

    @Override
    protected void iniciarEventos() {
        vista.getBtnBuscar().addActionListener(this::eventos);

        vista.getTblBuscar().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2
                        && vista.getTblBuscar().getSelectedRow() != -1) {

                    Object[] fila
                            = modelo.getValorFila(
                                    vista.getTblBuscar().getSelectedRow());

                    var proveedor = new Proveedor(
                            fila[0] + "",
                            fila[1] + "",
                            fila[2] + "",
                            fila[3] + "",
                            fila[4] + ""
                    );

                    oyente.setObjeto(proveedor);
                }
            }

        });

        vista.getJsFilas().addChangeListener(e -> {
            controladorPaginacion.setFILASAMOSTRAR(Integer.valueOf(vista.getJsFilas().getValue().toString()));
        });
        vista.getJsPaginas().addChangeListener(e -> {
            controladorPaginacion.setPAGINASAMOSTRAR(Integer.valueOf(vista.getJsPaginas().getValue().toString()));
        });

        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                var action = new ActionEvent(vista.getBtnBuscar(), 0, "Buscar");
                eventos(action);
            }
        });

        vista.getBtnNuevo().addActionListener(this::eventos);

        vista.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                if (vp != null && !vp.isClosed()) {
                    vp.dispose();
                }
            }

        });

    }

    @Override
    protected void eventos(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Buscar":
                paginar(1, controladorPaginacion.getFILASAMOSTRAR());
            break;
            case "Nuevo":
                if (vp == null || vp.isClosed()) {
                    vp = new VProveedor();
                    var cproveedor = new CProveedor(vp);
                    vista.getParent().remove(vp);
                    vista.getParent().add(vp);
                    vp.setVisible(true);
                }

                try {
                    vp.setSelected(true);
                } catch (PropertyVetoException ex) {
                    System.out.println(ex);
                }
                vp.getBtnBuscar().setVisible(false);
               
                break;

        }
    }

    @Override
    public void paginar(int pag, int nPag) {
        try {
            modelo.eliminarFilas();
            modelo.setData(((ProveedorDAO) dao).listar(vista.getTxtBuscar().getText(),
                    pag,
                    nPag));
            Utilidades.Tabla.resizeColumnWidth(vista.getTblBuscar());
            controladorPaginacion.crearBotones(pag);
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
    }

    @Override
    public int getSize() {
        int r = 0;
        try {
            r = ((ProveedorDAO) dao).getMax(vista.getTxtBuscar().getText());
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
        return r;
    }

    private final IController.IOyente oyente;
    private VProveedor vp;

}
