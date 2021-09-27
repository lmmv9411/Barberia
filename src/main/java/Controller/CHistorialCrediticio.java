package Controller;

import DAO.VentaPorCobrar;
import DTO.IndicesColumnas;
import DTO.VentasPorCobrar;
import IController.CConsultas;
import Utilidades.ControladorPaginacion;
import Utilidades.ModeloTabla;
import Vista.VConsulta;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.table.TableColumnModel;

public class CHistorialCrediticio extends CConsultas<VentasPorCobrar> {

    public CHistorialCrediticio(VConsulta vista) {
        super(vista);

        dao = new VentaPorCobrar();

        vista.setTitle("Historial Crediticio");
        vista.getBtnAnular().setVisible(false);
        vista.getPanelDetalles().setVisible(false);

        var titulos = new String[]{
            "#Factura", "Fecha", "Id Cliente", "Cliente", "Total Factura", "Descuento Factura",
            "Monto Recibido", "Id Barbero", "Barbero", "Estado", "Cajero"};

        modelo = new ModeloTabla(titulos);

        var filtros = new IndicesColumnas[]{
            new IndicesColumnas("id", titulos[0]),
            new IndicesColumnas("id_cliente", titulos[2]),
            new IndicesColumnas("cliente", titulos[3]),
            new IndicesColumnas("id_barbero", titulos[7]),
            new IndicesColumnas("barbero", titulos[8]),
            new IndicesColumnas("estado", titulos[9]),
            new IndicesColumnas("cajero", titulos[10])
        };

        for (IndicesColumnas columna : filtros) {
            vista.getCmbColumna().addItem(columna);
        }

        vista.getCmbColumna().setSelectedIndex(-1);

        vista.getTblFacturas().setModel(modelo);

        final TableColumnModel columnModel = vista.getTblFacturas().getColumnModel();
        for (int i = 0; i < vista.getTblFacturas().getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth((titulos[i] + "").length() * 10);
        }

        controladorPaginacion = new ControladorPaginacion(this, vista.getPanelPaginacion());

        modeloDetalles = new ModeloTabla(new Object[]{
            "#Factura", "Fecha", "Abonado", "Id Usuario", "Usuario"
        });

        vista.getTblDetalles().setModel(modeloDetalles);
        
    }

    @Override
    protected void eventos(ActionEvent e) {
        super.eventos(e);
        switch (e.getActionCommand()) {
            case "Mostrar Detalles":
                try {

                if(row == -1){
                    vista.getPanelDetalles().setVisible(false);
                    return;
                }
                    
                id = (int) modelo.getValorFila(row)[0];
                modeloDetalles.eliminarFilas();
                modeloDetalles.setData(((VentaPorCobrar) dao).verDetalleCobro(id));
                
            } catch (SQLException ex) {
                mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
                dao.cerrarConexion();
            }
            break;
        }
    }

    @Override
    public void paginar(int pag, int nPag) {

        super.paginar(pag, nPag);
        
        try {
            var lista = ((VentaPorCobrar) dao).filtrar(
                    fechaInicial(), fechaFinal(), pag, nPag,
                    asc,
                    col(),
                    param());

            modelo.eliminarFilas();
            modelo.setData(lista);
            Utilidades.Tabla.resizeColumnWidth(vista.getTblFacturas());
            controladorPaginacion.crearBotones(pag);
            vista.getPanelDetalles().setVisible(false);
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }
    }

    @Override
    public int getSize() {
        int size = 0;
        try {
            size = ((VentaPorCobrar) dao).size(
                    fechaInicial(), fechaFinal(), col(),
                    param());
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }
        return size;
    }
    
    @Override
    public String tipo() {
       return "ventascredito";
    }

    @Override
    protected void checkPermisos() {
        
    }
    

}
