package Controller;

import DAO.ProductoDAO;
import DTO.Producto;
import IController.IOyente;
import Utilidades.ControladorPaginacion;
import Utilidades.ModeloTabla;
import Utilidades.Paginacion;
import Vista.VBuscar;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class CProductoVista extends Controlador<VBuscar, Producto> implements Paginacion {

    public CProductoVista(VBuscar vista, IOyente oyente) {

        super(vista);
        
        this.vista.setTitle("Buscar Productos");
        this.vista.getChkAscendente().setVisible(false);
        
        modelo = new ModeloTabla(new Object[]{
            "Id", "Nombre", "Unidad", "Cantidad", "Mínimo"
        });

        this.vista.getTblBuscar().setModel(modelo);

        dao = new ProductoDAO();

        this.vista.getPanelPaginacion().setLayout(new FlowLayout(FlowLayout.CENTER));

        controladorPaginacion = new ControladorPaginacion(this, this.vista.getPanelPaginacion());

        vista.getJsFilas().setValue(controladorPaginacion.getFILASAMOSTRAR());
        vista.getJsPaginas().setValue(controladorPaginacion.getPAGINASAMOSTRAR());

        this.oyente = oyente;
    }

    @Override
    protected void iniciarEventos() {

        vista.getBtnBuscar().addActionListener(this::eventos);

        vista.getTblBuscar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int r = vista.getTblBuscar().getSelectedRow();

                if (e.getClickCount() == 2 && r != -1) {

                    var fila = modelo.getValorFila(r);

                    var unidad = new DTO.Unidad();
                    unidad.setNombre(fila[2] + "");

                    var producto = new Producto(
                            fila[0] + "",
                            fila[1] + "",
                            unidad,
                            (int) fila[3],
                            (Integer) fila[4]
                    );

                    oyente.setObjeto(producto);
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
    }

    @Override
    protected void eventos(ActionEvent e) {
        try {
            ((ProductoDAO) dao)
                    .listar(vista.getTxtBuscar().getText(),
                            1,
                            controladorPaginacion.getFILASAMOSTRAR());
            switch (e.getActionCommand()) {
                case "Buscar":
                    paginar(1, controladorPaginacion.getFILASAMOSTRAR());
                    break;
            }

        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }

    }

    @Override
    public void paginar(int pag, int nPag) {
        try {
            modelo.eliminarFilas();
            modelo.setData(((ProductoDAO) dao).listar(vista.getTxtBuscar().getText(), pag, nPag));
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
            r = ((ProductoDAO) dao).getMax(vista.getTxtBuscar().getText());
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
        return r;
    }

    @Override
    protected void limpiar() {
        super.limpiar();
        modelo.eliminarFilas();
        vista.getTxtBuscar().requestFocus();
    }

    private final IOyente oyente;
    
}