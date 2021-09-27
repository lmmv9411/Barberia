package Controller;

import DAO.RolDAO;
import DTO.Rol;
import Utilidades.ModeloTabla;
import Vista.VRoles;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.LinkedList;

public class CRol extends Controlador<VRoles, Rol> {

    public CRol(VRoles vista) {
        super(vista);

        dao = new RolDAO();

        modelo = new ModeloTabla(new String[]{
            "Id", "Rol"
        });

        vista.getTblRoles().setModel(modelo);

    }

    @Override
    protected void iniciarEventos() {

        vista.getBtnBuscar().addActionListener(this::eventos);
        vista.getBtnCrearRol().addActionListener(this::eventos);
        vista.getBtnActualizar().addActionListener(this::eventos);
        vista.getBtnLimpiar().addActionListener(e -> limpiar());

        vista.getTblRoles().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2
                        && vista.getTblRoles().getSelectedRow() != -1) {

                    int row = vista.getTblRoles().getSelectedRow();

                    try {

                        var rol = new DTO.Rol((int) modelo.getValorFila(row)[0], "");

                        var mapa = ((RolDAO) dao).verPermisos(rol);

                        ITERADOR = iterador();

                        while (ITERADOR.hasNext()) {
                            var chk = ITERADOR.next();
                            chk.setSelected(false);
                            var cod = Integer.parseInt(chk.getName());
                            if (mapa.contains(cod)) {
                                chk.setSelected(true);
                            }
                        }

                    } catch (SQLException ex) {
                        mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
                        dao.cerrarConexion();
                    }
                }
            }
        });
    }

    @Override
    protected void eventos(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case "Buscar":
                    modelo.eliminarFilas();
                    try {
                        modelo.setData(((RolDAO) dao).filtrar(vista.getTxtBuscar().getText()));
                    } catch (SQLException ex) {
                        mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
                        dao.cerrarConexion();
                    }
                    break;
                case "Crear":
                    var permisos = new LinkedList<String>();

                    ITERADOR = iterador();

                    while (ITERADOR.hasNext()) {
                        var chk = ITERADOR.next();
                        if (chk.isSelected()) {
                            permisos.add(chk.getName());
                        }
                    }

                    var rol = new Rol(0, vista.getTxtNombreRol().getText());

                    ((RolDAO) dao).crear(permisos, rol);
                    mostrarMensaje("¡Éxito!", "¡Nuevo Rol Ingresado!");
                    break;
                case "Actualizar":
                    permisos = new LinkedList<>();

                    ITERADOR = iterador();

                    while (ITERADOR.hasNext()) {
                        var chk = ITERADOR.next();
                        if (chk.isSelected()) {
                            permisos.add(chk.getName());
                        }
                    }

                    int id = (int) modelo.getValorFila(vista.getTblRoles().getSelectedRow())[0];
                    rol = new DTO.Rol(id, "");

                    ((RolDAO) dao).Actualizar(rol, permisos);
                    mostrarMensaje("¡Éxito!", "¡Rol Actualizado!");
                    break;

            }

        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            try {
                dao.rollback();
            } catch (SQLException exe) {
                mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            }
            dao.cerrarConexion();
        }
    }

    @Override
    protected void limpiar() {
        super.limpiar();
        modelo.eliminarFilas();

        ITERADOR = iterador();

        while (ITERADOR.hasNext()) {
            ITERADOR.next().setSelected(false);
        }
    }

    private java.util.Iterator<javax.swing.JCheckBox> ITERADOR;

    private java.util.Iterator<javax.swing.JCheckBox> iterador() {

        java.util.Stack<javax.swing.JCheckBox> pila = new java.util.Stack<>();

        for (var component : vista.getPanelPermisos().getComponents()) {
            if (component instanceof javax.swing.JCheckBox) {
                var chk = (javax.swing.JCheckBox) component;
                pila.add(chk);
            }
        }

        return new java.util.Iterator<javax.swing.JCheckBox>() {
            @Override
            public boolean hasNext() {
                return !pila.isEmpty();
            }

            @Override
            public javax.swing.JCheckBox next() {
                return pila.pop();
            }

        };
    }

}
