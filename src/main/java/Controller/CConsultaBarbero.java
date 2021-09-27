package Controller;

import DAO.BarberoDAO;
import DTO.Barbero;
import DTO.Usuario;
import Utilidades.ModeloTabla;
import Vista.VBuscar;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CConsultaBarbero extends Controlador<VBuscar, Barbero> {

    public CConsultaBarbero(VBuscar vista) {
        super(vista);
        modelo = new ModeloTabla(new Object[]{
            "Id", "Nombre", "Apellido"
        });
        
        vista.getTblBuscar().setModel(modelo);
        dao = new BarberoDAO();
        
    }

    @Override
    protected void iniciarEventos() {
        vista.getBtnBuscar().addActionListener(this::eventos);
        vista.getTblBuscar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(vista.getTblBuscar().getSelectedRow() != -1 && e.getClickCount() == 2){
                    var datos = modelo.getValorFila(vista.getTblBuscar().getSelectedRow());
                    var usuario = new Usuario();
                    usuario.setNombre(datos[1] + "");
                    usuario.setApellidoPaterno(datos[2] + "");
                    var barbero = new Barbero((int)datos[0], usuario);
                    
                }
            }
        });
    }

    @Override
    protected void eventos(ActionEvent e) {
        //try {
            switch (e.getActionCommand()) {
                case "Buscar":
                    modelo.eliminarFilas();
                   // modelo.setData(((BarberoDAO) dao).listar(vista.getTxtBuscar().getText()));
                    break;
            }
//        } catch (SQLException ex) {
//            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
//        }
    }

   
}
