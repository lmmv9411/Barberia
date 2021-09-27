package DAO;

import DTO.DetalleVenta;
import java.sql.SQLException;
import java.util.List;

public class DetalleVentaDAO extends Conexion<DetalleVenta> {

    public List<Object[]> listar(int id) throws SQLException {

        conectar();

        ps = con.prepareStatement("call verDetalleVenta(?)");
        ps.setInt(1, id);
        rs = ps.executeQuery();

        return getLista();
    }

}
