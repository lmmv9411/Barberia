package Controller;

import IController.CConsultas;
import DAO.ComprasDAO;
import DTO.Compra;
import DTO.IndicesColumnas;
import Utilidades.ModeloTabla;
import Vista.VConsulta;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class CConsultaCompras extends CConsultas<Compra> {

    public CConsultaCompras(VConsulta vista) {
        super(vista);

        dao = new ComprasDAO();

        this.vista.setTitle("Consultar Compras");

        modelo = new ModeloTabla(new Object[]{
            "Id Compra", "Factura", "Fecha", "Id Proveedor", "Proveedor", "Total", "Id Usuario",
            "Usuario"});

        this.vista.getTblFacturas().setModel(modelo);

        modeloDetalles = new ModeloTabla(new Object[]{
            "Id Compra", "Id Producto", "Producto", "Unidad", "Cantidad", "Precio"
        });
        
        var filtros = new IndicesColumnas[]{
            new IndicesColumnas("id_compra", "Id Compra"),
            new IndicesColumnas("factura", "Factura"),
            new IndicesColumnas("id_proveedor", "Id Proveedor"),
            new IndicesColumnas("proveedor", "Proveedor"),
            new IndicesColumnas("id_usuario", "Id Usuario"),
            new IndicesColumnas("usuario", "Usuario"),
        };

        for(IndicesColumnas columna: filtros){
            vista.getCmbColumna().addItem(columna);
        }
        vista.getCmbColumna().setSelectedIndex(-1);
        
        this.vista.getTblDetalles().setModel(modeloDetalles);
        
    }

    @Override
    protected void eventos(ActionEvent e) {
        super.eventos(e);
        try {
            switch (e.getActionCommand()) {
                case "Mostrar Detalles":
                
                    String idCompra= modelo.getValorFila(row)[0] + "";

                    modeloDetalles.eliminarFilas();
                    modeloDetalles.setData(((ComprasDAO) dao).detallesCompras(idCompra));
                
                break;
                case "Imprimir":

                    if (row == -1) {
                        return;
                    }

                    id = (int) modelo.getValorFila(row)[0];

                    reportes.Imprimir print = new reportes.Imprimir();
                    print.imprimirPorId(id, "compras", "idCompra");
                    break;
            }
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
        }
    }

    @Override
    public void paginar(int pag, int nPag) {
        super.paginar(pag, nPag);
        try {

            modelo.eliminarFilas();
            modelo.setData(((ComprasDAO) dao).filtrar(fechaInicial(),
                    fechaFinal(),
                    pag,
                    nPag,
                    vista.getChkbAscendente().isSelected(),
                    col(),
                    param()));

            controladorPaginacion.crearBotones(pag);
            
            Utilidades.Tabla.resizeColumnWidth(vista.getTblFacturas());
            
            vista.getPanelDetalles().setVisible(false);

        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }
    }

    @Override
    public int getSize() {
        int r = 0;
        try {
            r = ((ComprasDAO) dao).getSize(fechaInicial(), fechaFinal(), col(), param());
        } catch (SQLException ex) {
            mostrarMensajeError(ex.getErrorCode(), ex.getMessage());
            dao.cerrarConexion();
        }
        return r;
    }

    @Override
    public String tipo() {
        return "compras";
    }

}
