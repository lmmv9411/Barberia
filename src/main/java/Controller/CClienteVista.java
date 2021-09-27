package Controller;

import DAO.ClienteDAO;
import DTO.Cliente;
import Utilidades.ControladorPaginacion;
import Utilidades.ModeloTabla;
import Utilidades.Paginacion;
import Vista.VBuscar;
import Vista.VCliente;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


//Clase para para vista de clientes
public class CClienteVista extends Controlador<VBuscar, Cliente> implements Paginacion {

    public CClienteVista(VBuscar vista, IController.IOyente oyente, Container main) {
        super(vista);

        dao = new ClienteDAO();
        
        this.vista.setTitle("Buscar Clientes");
        
        vista.getPanelPaginacion().setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        controladorPaginacion = new ControladorPaginacion(this, vista.getPanelPaginacion());

        modelo = new ModeloTabla(new Object[]{
            "Id", "Cliente", "Teléfono", "Correo"
        }) {
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return getData().get(rowIndex)[columnIndex];
                    case 1:
                        return getData().get(rowIndex)[1] + " "
                                + getData().get(rowIndex)[2] + " "
                                + getData().get(rowIndex)[3] + " "
                                + getData().get(rowIndex)[4];
                    case 2:
                        return getData().get(rowIndex)[5];
                    case 3:
                        return getData().get(rowIndex)[6];
                }
                return "";
            }

        };

        vista.getTblBuscar().setModel(modelo);

        vista.getJsFilas().setValue(controladorPaginacion.getFILASAMOSTRAR());
        vista.getJsPaginas().setValue(controladorPaginacion.getPAGINASAMOSTRAR());

        this.ioyente = oyente;
        this.main = main;
    }

    @Override
    protected void iniciarEventos() {
        vista.getBtnBuscar().addActionListener(this::eventos);
        vista.getBtnNuevo().addActionListener(this::eventos);
        vista.getTblBuscar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (vista.getTblBuscar().getSelectedRow() != -1) {
                        var fila = modelo.getValorFila(vista.getTblBuscar().getSelectedRow());
                        var cliente = new Cliente(
                                (int) fila[0],
                                fila[1] + "",
                                fila[2] + "",
                                fila[3] + "",
                                fila[4] + "",
                                fila[5] + "",
                                fila[6] + "");
                        ioyente.setObjeto(cliente);
                    }
                }
            }

        });
        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                buscar();
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
                buscar();
                break;
            case "Nuevo":
                if (Vcliente == null || Vcliente.isClosed()) {
                    Vcliente = new VCliente();
                    CClientes ccliente = new CClientes(Vcliente, false);
                    main.add(Vcliente);
                    Vcliente.setVisible(true);
                    break;
                }
        }
    }

    @Override
    public void paginar(int pag, int nPag) {
        try {
            modelo.eliminarFilas();
            modelo.setData(((ClienteDAO) dao).filtrar(vista.getTxtBuscar().getText(),
                    pag,
                    nPag,
                    vista.getChkAscendente().isSelected()));
            Utilidades.Tabla.resizeColumnWidth(vista.getTblBuscar());
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
            r = ((ClienteDAO) dao).getSize(vista.getTxtBuscar().getText());
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }

        return r;
    }

    private void buscar() {
        paginar(1, controladorPaginacion.getFILASAMOSTRAR());
    }

    private VCliente Vcliente;
    private final Container main;
    private final IController.IOyente ioyente;
}
