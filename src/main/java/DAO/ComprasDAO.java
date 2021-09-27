package DAO;

import DTO.Compra;
import DTO.DetalleCompra;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ComprasDAO extends Conexion<Compra> {

    public int crearAI(Compra obj) throws SQLException {
        conectar();

        con.setAutoCommit(false);

        ps = con.prepareStatement("insert into compras values (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, 0);
        ps.setString(2, obj.getFactura());
        ps.setString(3, obj.getProveedor().getId());
        ps.setDouble(4, obj.getTotal());
        ps.setString(5, obj.getUsuario().getId());
        ps.setTimestamp(6, obj.getFecha());

        ps.executeUpdate();

        rs = ps.getGeneratedKeys();

        int r = 0;

        if (rs.next()) {
            r = rs.getInt(1);
        }

        return r;
    }

    public void crearDetalleCompra(List<DetalleCompra> dcs) throws SQLException {

        ps = con.prepareStatement("INSERT INTO detalle_compra VALUES (?, ?, ?, ?)");

        for (DetalleCompra dc : dcs) {
            ps.setInt(1, dc.getId());
            ps.setString(2, dc.getProducto().getId());
            ps.setInt(3, dc.getCantidad());
            ps.setDouble(4, dc.getPrecio());
            ps.addBatch();
        }

        ps.executeBatch();

    }

    public void actualizarStock(List<DetalleCompra> dcs) throws SQLException {

        ps = con.prepareStatement(" UPDATE productos as a"
                + " inner join productos as b on b.id = a.id"
                + " set a.cantidad = b.cantidad + ?"
                + " where a.id = ?");

        for (DetalleCompra dc : dcs) {
            ps.setInt(1, dc.getCantidad());
            ps.setString(2, dc.getProducto().getId());
            ps.addBatch();
        }

        ps.executeBatch();
        con.commit();
        cerrarConexion();
    }

    public List<Object[]> filtrar(String fechaInicial, String fechaFinal,
            int pag, int npag, boolean asc,
            String col,
            String param)
            throws SQLException {

        var sql= Utilidades.SQls.getSQL(fechaInicial, fechaFinal, asc, col, param);
        
        if(sql.equals("")){
            return new java.util.ArrayList<>();
        }
        
        sql = sql.replace("n_factura", "id");
        sql = "select * from viewCompras where " + sql + "Limit ?, ?";

        conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, (pag - 1) * npag);
        ps.setInt(2, npag);
        rs = ps.executeQuery();

        return getLista();

    }

    public int getSize(String fi, String ff, String col, String param) throws SQLException {

        conectar();
        int r = 0;

        var sql = Utilidades.SQls.getSQL(fi, ff, true, col, param);
        
        if(sql.equals("")){
            return 0;
        }
        
        sql = sql.replace("ORDER by n_factura", "");
        sql = "select count(*) from viewCompras where " + sql;

        ps = con.prepareStatement(sql);

        rs = ps.executeQuery();

        if (rs.next()) {
            r = rs.getInt(1);
        }

        cerrarConexion();
        return r;
    }

    public List<Object[]> detallesCompras(String id) throws SQLException {
        conectar();

        ps = con.prepareStatement("call verDetalleCompra(?)");

        ps.setString(1, id);

        rs = ps.executeQuery();

        return getLista();

    }

}
