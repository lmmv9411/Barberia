package Controller;

import IController.IServicios;
import DAO.ServicioDAO;
import DTO.Servicio;
import Utilidades.ControladorPaginacion;
import Utilidades.ModeloTabla;
import Utilidades.Paginacion;
import Vista.VBuscar;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.NumberFormat;

public class CServiciosVista extends Controlador<VBuscar, Servicio> implements Paginacion {

    public CServiciosVista(VBuscar vista, IServicios iservicios) {
        super(vista);
        
        this.vista.setTitle("Buscar Servicios");
        this.vista.getChkAscendente().setVisible(false);
        
        this.vista.getPanelPaginacion().setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));

        this.vista.getTblBuscar().setRowHeight(30);
        this.vista.getTblBuscar().setAutoCreateRowSorter(true);

        dao = new ServicioDAO();

        modelo = new ModeloTabla(new Object[]{
            "Id", "Nombre", "Precio"
        });

        controladorPaginacion = new ControladorPaginacion(this, this.vista.getPanelPaginacion());

        vista.getJsFilas().setValue(controladorPaginacion.getFILASAMOSTRAR());
        vista.getJsPaginas().setValue(controladorPaginacion.getPAGINASAMOSTRAR());

        this.vista.getTblBuscar().setModel(modelo);
        this.iservicios = iservicios;
        this.formatter = NumberFormat.getCurrencyInstance();
        this.formatter.setMaximumFractionDigits(0);
    }

    @Override
    protected void iniciarEventos() {
        vista.getBtnBuscar().addActionListener(this::eventos);
        vista.getTblBuscar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (vista.getTblBuscar().getSelectedRow() != -1) {
                        var fila = modelo.getValorFila(vista.getTblBuscar().getSelectedRow());
                        var servicio = new DTO.Servicio(
                                (int) fila[0],
                                (int) fila[2],
                                fila[1] + "",
                                true);
                        iservicios.setServicio(servicio);
                    }
                }
            }

        });

        vista.getJsFilas().addChangeListener(e -> {
            controladorPaginacion.setFILASAMOSTRAR(Integer.valueOf(vista.getJsFilas().getValue().toString()));
        });
        vista.getJsPaginas().addChangeListener(e -> {
            controladorPaginacion.setPAGINASAMOSTRAR(Integer.valueOf(vista.getJsPaginas().getValue().toString()));
        });
    }

    @Override
    protected void eventos(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "Buscar":
                paginar(1, controladorPaginacion.getFILASAMOSTRAR());
                break;
        }

    }

    @Override
    public void paginar(int pag, int nPag) {
        try {

            modelo.eliminarFilas();

            modelo.setData(((ServicioDAO) dao).filtrar(
                    vista.getTxtBuscar().getText(),
                    1,
                    controladorPaginacion.getFILASAMOSTRAR()
            ));

            Utilidades.Tabla.resizeColumnWidth(vista.getTblBuscar());

            controladorPaginacion.crearBotones(1);
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }
    }

    @Override
    public int getSize() {
        try {
            return ((ServicioDAO) dao).size();
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }
        return 0;
    }

    private final IServicios iservicios;
    private final NumberFormat formatter;

}
