package DAO;

import DTO.VentasPorCobrar;
import java.sql.SQLException;
import java.util.List;

public class VentaPorCobrar extends Conexion<VentasPorCobrar> {

    public void crear(VentasPorCobrar venta, java.sql.Connection conn) throws SQLException {

        if (conn == null) {
            conectar();
            con.setAutoCommit(false);
        } else {
            con = conn;
        }

        ps = con.prepareStatement("insert into ventas_por_cobrar values("
                + "?, ?, ?, ?)");

        ps.setInt(1, venta.getId());
        ps.setInt(2, venta.getMonto_recibido());
        ps.setTimestamp(3, venta.getFecha());
        ps.setString(4, venta.getUsuario().getId());

        ps.executeUpdate();

        ps = con.prepareStatement("UPDATE ventas "
                + "set monto_recibido = ? "
                + "WHERE id = ?");

        ps.setInt(1, venta.getMonto_recibido() + venta.getMontoAbonado());
        ps.setInt(2, venta.getId());

        ps.executeUpdate();

        if (conn == null) {
            if (venta.getMontoAbonado() + venta.getMonto_recibido() >= venta.getTotal()) {
                ps = con.prepareStatement("update ventas set estado = ? where id = ?");
                //Pagada = 0
                ps.setInt(1, 0);
                ps.setInt(2, venta.getId());
                ps.executeUpdate();
            }
        }

        if (conn == null) {
            con.commit();
            cerrarConexion();
        }
    }

    public List<Object[]> listar(String[] params,
            int pag,
            int npag,
            boolean asc) throws SQLException {

        conectar();
        
        var orden = "ORDER BY n_factura ";

        if (!asc) {
            orden += "DESC ";
        }

        ps = con.prepareStatement("select * from verFacturas "
                + "where n_factura like ? "
                + "AND lower(cliente) LIKE ? "
                + "AND estado = 'Por Cobrar' "
                + orden
                + "limit ?, ?");

        ps.setString(1, params[0] + "%");
        ps.setString(2, params[1] + "%");
        ps.setInt(3, (pag - 1) * npag);
        ps.setInt(4, npag);

        rs = ps.executeQuery();

        return getLista();
    }

    public int getSize(String[] params) throws SQLException {
        conectar();

        ps = con.prepareStatement("select count(*) from verFacturas where n_factura like ? "
                + "AND lower(cliente) LIKE ? "
                + "AND estado = 'Por Cobrar' ");

        ps.setString(1, params[0] + "%");
        ps.setString(2, params[1] + "%");

        rs = ps.executeQuery();

        int r = 0;

        if (rs.next()) {
            r = rs.getInt(1);
        }

        return r;
    }

    public List<Object[]> verDetalleCobro(int id) throws SQLException {
        conectar();
        ps = con.prepareStatement("call verDetalleCobro(?)");

        ps.setInt(1, id);

        rs = ps.executeQuery();

        return getLista();
    }

    public List<Object[]> filtrar(String f1, String f2,
                                int pag, int nPag, boolean asc,
                                String col, String param) throws SQLException {
        
        var sql = Utilidades.SQls.getSQL(f1, f2, asc, col, param);
        
        if(sql.equals("")){
            return new java.util.ArrayList<>();
        }
        
        sql = sql.replace("n_factura", "id");
        
        sql = "select * from h_ventas_cred where "
                + sql
                + " LIMIT ?, ?";

        conectar();

        ps = con.prepareStatement(sql);

        ps.setInt(1, (pag - 1) * nPag);
        ps.setInt(2, nPag);

        rs = ps.executeQuery();

        return getLista();
    }

    public int size(String f1, String f2, String col, String param) throws SQLException {
        
        var sql = Utilidades.SQls.getSQL(f1, f2, true, col, param);
        
        if(sql.equals("")){
            return 0;
        }
        
        sql = sql.replace("ORDER by n_factura ", "");
        
        sql = "select count(*) from h_ventas_cred where " + sql;
        
        conectar();

        ps = con.prepareStatement(sql);

        rs = ps.executeQuery();

        int size = 0;

        if (rs.next()) {
            size = rs.getInt(1);
        }

        return size;

    }

}
