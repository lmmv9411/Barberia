package Controller;

import DAO.UsuarioDAO;
import DTO.Usuario;
import Utilidades.ControladorPaginacion;
import Utilidades.ModeloTabla;
import Vista.VUsuario;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.table.TableColumnModel;
import org.apache.commons.codec.digest.DigestUtils;

public class CUsuarios extends Controlador<VUsuario, Usuario> implements Utilidades.Paginacion {

    public CUsuarios(VUsuario vista) {
        super(vista);

        var titulos = new Object[]{
            "Id", "Nombre", "Segundo Nombre", "Apellido Paterno", "Apellido Materno",
            "Rol", "Activo"
        };
        
        modelo = new ModeloTabla(titulos);

        vista.getPanelPaginacion().setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        vista.getTblUsuarios().setModel(modelo);
        
        final TableColumnModel columnModel = vista.getTblUsuarios().getColumnModel();
        for (int i = 0; i < vista.getTblUsuarios().getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth((titulos[i]+"").length() * 10);
        }
        
        controladorPaginacion = new ControladorPaginacion(this, vista.getPanelPaginacion());
    }

    @Override
    protected void iniciarEventos() {
        vista.getBtnBuscar().addActionListener(e -> paginar(1, controladorPaginacion.getFILASAMOSTRAR()));
        vista.getBtnGuardar().addActionListener(this::eventos);
        vista.getBtnReset().addActionListener(this::eventos);
        vista.getBtnVerPass().addActionListener(this::eventos);

        vista.getTblUsuarios().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int row = vista.getTblUsuarios().getSelectedRow();

                if (e.getClickCount() == 2 && row != -1) {

                    var fila = modelo.getValorFila(row);

                    vista.getTxtId().setText(fila[0] + "");
                    vista.getTxtNombre().setText(fila[1] + "");
                    vista.getTxtNombreII().setText(fila[2] + "");
                    vista.getTxtApellido().setText(fila[3] + "");
                    vista.getTxtApellidoII().setText(fila[4] + "");

                    var rol = new DTO.Rol((int) fila[7], fila[5] + "");
                    vista.getCmbxRol().setSelectedItem(rol);

                    vista.getChkActivo().setSelected((boolean) fila[6]);
                    //vista.getTxtPass().setText(fila[7] + "");
                    vista.getBtnGuardar().setText("Actualizar");

                }
            }
        });

        vista.getTxtId()
                .addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e
                    ) {
                        if (!Character.isDigit(e.getKeyChar())) {
                            e.consume();
                        }
                    }

                });
        
        
        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                var accion = new java.awt.event.ActionEvent(e.getComponent(), 0, "Buscar");
                eventos(accion);
            }
        });
    }

    @Override
    protected void iniciarVariables() {
        this.dao = new UsuarioDAO();
        try {
            var listaRoles = ((UsuarioDAO) dao).getRoles();

            listaRoles.forEach(rol -> {
                vista.getCmbxRol().addItem(rol);
            });

        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
    }

    @Override
    protected void eventos(java.awt.event.ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case "Guardar":
                    if (!verificarControles()) {
                        return;
                    }
                    dao.crear(obtenerUsuario());
                    mostrarMensaje("Resultado", "Usuario Creado Con Éxito!");
                    limpiar();
                    vista.getBtnBuscar().doClick();
                    break;
                case "Ver":
                    if (vista.getBtnVerPass().isSelected()) {
                        vista.getTxtPass().setEchoChar((char) 0);
                    } else {
                        vista.getTxtPass().setEchoChar('*');
                    }
                    break;
                case "Actualizar":
                    if (!verificarControles()) {
                        return;
                    }
                    dao.Actualizar(obtenerUsuario());
                    mostrarMensaje("Resultado", "!Usuario Actualizado Con Éxito!");
                    limpiar();
                    vista.getBtnBuscar().doClick();
                    break;
                case "Limpiar":
                    limpiar();
            }
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
    }

    @Override
    public void paginar(int pag, int nPag) {
        try {
            modelo.eliminarFilas();
            modelo.setData(((UsuarioDAO) dao).listar(vista.getTxtBuscar().getText(), pag, nPag));
            Utilidades.Tabla.resizeColumnWidth(vista.getTblUsuarios());
            controladorPaginacion.crearBotones(pag);
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
    }

    @Override
    public int getSize() {
        int size = 0;
        try {
            size = ((UsuarioDAO) dao).getSize(vista.getTxtBuscar().getText());
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }

        return size;
    }

    private Usuario obtenerUsuario() {
        
        String pass = DigestUtils.sha3_256Hex(new String(vista.getTxtPass().getPassword()));
        
        return new Usuario(vista.getTxtId().getText(),
                vista.getTxtNombre().getText(),
                vista.getTxtNombreII().getText(),
                vista.getTxtApellido().getText(),
                vista.getTxtApellidoII().getText(),
                (DTO.Rol) vista.getCmbxRol().getSelectedItem(),
                pass,
                vista.getChkActivo().isSelected(),
                null);

    }

    @Override
    protected boolean verificarControles() {

        for (java.awt.Component component : vista.getPanelDatos().getComponents()) {
            if (component instanceof javax.swing.JTextField
                    || component instanceof javax.swing.JPasswordField) {
                javax.swing.JTextField txt = (javax.swing.JTextField) component;
                if (txt.getText().equals("")) {
                    mostrarMensajeError(-1, txt.getName() + " está vacío!");
                    txt.requestFocus();
                    return false;
                }
            }
        }

        return true;
    }

}
