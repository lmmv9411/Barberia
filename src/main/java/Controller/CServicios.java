package Controller;

import IController.IServicios;
import DAO.ServicioDAO;
import DTO.Servicio;
import Vista.VBuscar;
import Vista.VServicio;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class CServicios extends Controlador<VServicio, Servicio> implements IServicios {
    
    public CServicios(VServicio servicios) {
        super(servicios);
        dao = new ServicioDAO();
    }
    
    @Override
    protected void iniciarEventos() {
        vista.getBtnGuardar().addActionListener(this::eventos);
        vista.getBtnBuscar().addActionListener(this::eventos);
        vista.getBtnLimpiar().addActionListener(this::eventos);
        vista.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                if (!vbs.isClosed()) {
                    vbs.dispose();
                }
            }
        });
    }
    
    @Override
    protected void eventos(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case "Guardar":
                    dao.crear(getServicio());
                    mostrarMensaje("Resultado", "¡Servicio Creado!");
                    limpiar();
                    break;
                case "Buscar":
                    if (vbs == null || vbs.isClosed()) {
                        vbs = new VBuscar();
                        
                        vista.getParent().remove(vbs);
                        vista.getParent().add(vbs);
                        
                        vbs.setVisible(true);
                        var csv = new CServiciosVista(vbs, this);
                    }
                    try {
                        vbs.setSelected(true);
                    } catch (PropertyVetoException ex) {
                        System.out.println(ex);
                    }
                    break;
                case "Actualizar":
                    dao.Actualizar(getServicio());
                    mostrarMensaje("Resultado", "¡Servicio Actualizado!");
                    limpiar();
                    break;
                case "Limpiar":
                    limpiar();
                    break;
                
            }
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
    }
    
    private Servicio getServicio() {
        
        Servicio servicio = new Servicio(
                idTmp,
                Integer.parseInt(vista.getTxtPrecio().getText()),
                vista.getTxtNombre().getText(),
                vista.getChkActivo().isSelected()
        );
        return servicio;
        
    }
    
    @Override
    public void setServicio(Servicio servicio) {
        
        idTmp = servicio.getId();
        vista.getTxtNombre().setText(servicio.getNombre());
        vista.getTxtPrecio().setText(servicio.getPrecio() + "");
        vista.getChkActivo().setSelected(servicio.isActivo());
        vista.getBtnGuardar().setText("Actualizar");
        try {
            vista.setSelected(true);
        } catch (PropertyVetoException ex) {
            System.out.println(ex);
        }
        
    }
    
    @Override
    protected void limpiar() {
        super.limpiar();
        vista.getBtnGuardar().setText("Guardar");
    }
    
    private int idTmp = 0;
    private VBuscar vbs;
    
}
