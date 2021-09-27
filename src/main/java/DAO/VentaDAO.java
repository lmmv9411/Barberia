package DAO;

import DTO.DetalleVenta;
import DTO.Venta;
import DTO.VentasPorCobrar;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class VentaDAO extends Conexion<Venta> {

    public List<Object[]> filtrar(String f1, String f2,
            int pag, int nPag,
            boolean asc, String col,
            String param) throws SQLException {

        var sql = Utilidades.SQls.getSQL(f1, f2, asc, col, param);

        if(sql.equals("")){
            return new java.util.ArrayList<>();
        }
        
        sql = "SELECT * FROM verFacturas WHERE "
                + sql
                + "LIMIT ?, ?";

        conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, (pag - 1) * nPag);
        ps.setInt(2, nPag);

        rs = ps.executeQuery();

        return getLista();

    }

    public int crearAI(Venta venta) throws SQLException {
        conectar();
        con.setAutoCommit(false);
        int id = 0;

        ps = con.prepareStatement("INSERT INTO ventas VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, venta.getId());
        ps.setInt(2, venta.getCliente().getId());
        ps.setInt(3, venta.getTotal());
        ps.setInt(4, venta.getDescuento());
        ps.setInt(5, venta.getMonto_recibido());
        ps.setInt(6, venta.getBarbero().getId());
        ps.setString(7, venta.getUsuario().getId());
        ps.setTimestamp(8, venta.getFecha());
        ps.setInt(9, venta.getEstado().getId());

        ps.executeUpdate();

        rs = ps.getGeneratedKeys();

        if (rs.next()) {
            id = rs.getInt(1);
        }

        if (venta.getEstado().getId() == 2) {
            var vpc = new VentaPorCobrar();
            var v = new VentasPorCobrar();
            v.setId(id);
            v.setMonto_recibido(venta.getMonto_recibido());
            v.setUsuario(venta.getUsuario());
            v.setFecha(venta.getFecha());
            v.setTotal(venta.getTotal());
            vpc.crear(v, con);
        }

        return id;
    }

    public void crearDetalleVenta(List<DetalleVenta> dvs) throws SQLException {

        ps = con.prepareStatement("INSERT INTO detalle_venta VALUES (?, ?, ?, ?)");

        for (DetalleVenta dv : dvs) {
            ps.setInt(1, dv.getVenta().getId());
            ps.setInt(2, dv.getServicio().getId());
            ps.setInt(3, dv.getCantidad());
            ps.setInt(4, dv.getPrecio());
            ps.addBatch();
        }

        ps.executeBatch();

        con.commit();

        cerrarConexion();
    }

    public int size(String f1, String f2, String col, String param) throws SQLException {
        conectar();
        int r = 0;

        var sql = Utilidades.SQls.getSQL(f1, f2, true, col, param);

        if(sql.equals("")){
            return 0;
        }
        
        sql = sql.replace("ORDER by n_factura ", "");

        ps = con.prepareStatement("SELECT count(*) FROM verFacturas "
                + "WHERE "
                + sql);

        rs = ps.executeQuery();
        if (rs.next()) {
            r = rs.getInt(1);
        }
        cerrarConexion();
        return r;
    }

    public void anular(int idVenta, String descripcion,
            DTO.Usuario usuario) throws SQLException {

        conectar();

        ps = con.prepareStatement("call anularFactura(?, ?, ?)");

        ps.setInt(1, idVenta);
        ps.setString(2, descripcion);
        ps.setString(3, usuario.getId());

        ps.executeUpdate();

        cerrarConexion();

    }

}
