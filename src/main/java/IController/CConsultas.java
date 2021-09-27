package IController;

import Controller.CReportes;
import Controller.Controlador;
import DTO.IndicesColumnas;
import DTO.Usuario;
import Utilidades.ControladorPaginacion;
import Utilidades.ModeloTabla;
import Utilidades.Paginacion;
import Vista.VConsulta;
import Vista.VReportes;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public abstract class CConsultas<K> extends Controlador<VConsulta, K>
        implements Paginacion, IReportes {

    public CConsultas(VConsulta vista) {
        super(vista);
        usuario = Utilidades.Sesion.getUsuario();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        checkPermisos();
    }

    protected void checkPermisos() {
        var permisos = usuario.getPermisos();
        var lista = permisos.get(this.tipo()) == null
                ? new java.util.LinkedList<>() : permisos.get(this.tipo());

        vista.getBtnAnular().setVisible(lista.contains("eliminar"));

        lista = permisos.get("informes") == null ? new java.util.LinkedList<>() : permisos.get("informes");
        vista.getBtnExcel().setVisible(lista.contains(this.tipo()));

        this.vista.getPanelDetalles().setVisible(false);
        this.vista.pack();

    }

    @Override
    protected void iniciarVariables() {
        controladorPaginacion = new ControladorPaginacion(this, vista.getPanelPaginacion());
        vista.getJsFilas().setValue(controladorPaginacion.getFILASAMOSTRAR());
        vista.getJsPaginas().setValue(controladorPaginacion.getPAGINASAMOSTRAR());
    }

    @Override
    protected void iniciarEventos() {
        vista.getBtnBuscar().addActionListener(e
                -> paginar(1, controladorPaginacion.getFILASAMOSTRAR()));

        vista.getBtnBuscar().setMnemonic('b');

        vista.getJsFilas().addChangeListener(e -> {
            controladorPaginacion.setFILASAMOSTRAR((int) vista.getJsFilas().getValue());
        });

        vista.getJsPaginas().addChangeListener(e -> {
            controladorPaginacion.setPAGINASAMOSTRAR((int) vista.getJsPaginas().getValue());
        });

        vista.getBtnDetalles().addActionListener(this::eventos);
        vista.getBtnImprimir().addActionListener(this::eventos);
        vista.getBtnLimpiar().addActionListener(this::eventos);

        vista.getTblFacturas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    var evento = new ActionEvent(vista.getBtnDetalles(), 0, "Mostrar Detalles");
                    eventos(evento);
                }
            }

        });

        vista.getBtnAnular().addActionListener(this::eventos);
        vista.getBtnExcel().addActionListener(this::eventos);
    }

    @Override
    protected void eventos(ActionEvent e) {

        row = vista.getTblFacturas().getSelectedRow();
        if (row != -1) {
            row = vista.getTblFacturas().getRowSorter().convertRowIndexToModel(row);
        }
        switch (e.getActionCommand()) {

            case "Mostrar Detalles":

                vista.getPanelDetalles().setVisible(true);

                break;

            case "Limpiar":
                limpiar();
                modelo.eliminarFilas();
                modeloDetalles.eliminarFilas();
                vista.getPanelPaginacion().removeAll();
                vista.getPanelPaginacion().updateUI();
                vista.getPanelDetalles().setVisible(false);
                break;
            case "Crear Excel":

                if (reporteVentas == null || reporteVentas.isClosed()) {
                    reporteVentas = new VReportes();
                    vista.getParent().remove(reporteVentas);
                    vista.getParent().add(reporteVentas);

                    int x = (vista.getSize().width - reporteVentas.getSize().width) / 2;
                    int y = (vista.getSize().height - reporteVentas.getSize().height) / 2;
                    reporteVentas.setLocation(x, y);
                    reporteVentas.setVisible(true);
                    var cReporteVentas = new CReportes(reporteVentas, this);
                }

                break;
        }

    }

    @Override
    public String fechaInicial() {

        if (vista.getTxtFechaInicial().getDate() != null) {
            return sdf.format(vista.getTxtFechaInicial().getDate()) + " 00:00:00";
        }
        return "";
    }

    @Override
    public String fechaFinal() {
        if (vista.getTxtFechaFinal().getDate() != null) {
            return sdf.format(vista.getTxtFechaFinal().getDate()) + " 23:59:59";
        }
        return "";
    }

    @Override
    public String col() {
        var columna = "";
        if (vista.getCmbColumna().getSelectedIndex() != -1) {
            columna = ((IndicesColumnas) vista.getCmbColumna().getSelectedItem()).getId();
        }

        return columna;
    }

    @Override
    public String param() {
        return vista.getTxtFiltro().getText().toLowerCase();
    }

    @Override
    public void paginar(int pag, int nPag) {

        asc = vista.getChkbAscendente().isSelected();

        modelo.eliminarFilas();

        vista.getPanelDetalles().setVisible(false);

    }

    @Override
    protected void limpiar() {
        super.limpiar();
        vista.getCmbColumna().setSelectedIndex(-1);
    }

    private final SimpleDateFormat sdf;
    protected int row, id;
    protected Usuario usuario;
    protected boolean asc;
    protected ModeloTabla modeloDetalles;
    protected VReportes reporteVentas;
}
