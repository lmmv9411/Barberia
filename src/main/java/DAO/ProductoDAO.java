package DAO;

import DTO.Producto;
import DTO.Unidad;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProductoDAO extends Conexion<Producto> {

    public List<Object[]> listar(String parametro, int pag, int npag) throws SQLException {

        conectar();

        ps = con.prepareStatement("call verProductoId(?, ?, ?)");
        ps.setString(1, parametro);
        ps.setInt(2, (pag - 1) * npag);
        ps.setInt(3, npag);

        rs = ps.executeQuery();

        return getLista();
    }

    public int getMax(String parametro) throws SQLException {

        parametro = parametro + "%";

        conectar();

        ps = con.prepareStatement("select count(*) from productos where "
                + "id like ? or nombre like ?");

        ps.setString(1, parametro);
        ps.setString(2, parametro);
        rs = ps.executeQuery();
        int r = 0;
        if (rs.next()) {
            r = rs.getInt(1);
        }
        cerrarConexion();
        return r;
    }

    @Override
    public void crear(Producto producto) throws SQLException {

        conectar();

        ps = con.prepareStatement("insert into productos values(?, ?, ?, ?, ?)");
        ps.setString(1, producto.getId());
        ps.setString(2, producto.getNombre());
        ps.setInt(3, producto.getUnidad().getId());
        ps.setInt(4, producto.getCantidad());
        ps.setInt(5, producto.getMinimo());

        ps.executeUpdate();

        cerrarConexion();

    }

    public List<Unidad> getUnidades() throws SQLException {
        conectar();
        List<Unidad> lista = new LinkedList<>();

        ps = con.prepareCall("select * from unidades");
        rs = ps.executeQuery();

        while (rs.next()) {
            var und = new Unidad(rs.getInt(1), rs.getString(2));
            lista.add(und);
        }

        cerrarConexion();
        return lista;
    }

    @Override
    public void Actualizar(Producto obj) throws SQLException {
        conectar();

        ps = con.prepareStatement("UPDATE productos SET nombre = ?, unidad = ?, minimo = ? WHERE id = ?");
        ps.setString(1, obj.getNombre());
        ps.setInt(2, obj.getUnidad().getId());
        ps.setInt(3, obj.getMinimo());
        ps.setString(4, obj.getId());

        ps.executeUpdate();

        cerrarConexion();
    }
    
    public int getCosto(Producto producto) throws SQLException{
        conectar();
        ps = con.prepareStatement("SELECT precio from detalle_compra " +
                                  "WHERE producto = ? " +
                                  "AND id_compra = " + 
                                  "(SELECT MIN(id_compra) " +
                                  "FROM detalle_compra WHERE producto = ?)");
        ps.setString(1, producto.getId());
        ps.setString(2, producto.getId());
        
        rs = ps.executeQuery();
        
        int r = 0;
        
        if(rs.next()){
            r = rs.getInt(1);
        }
        
        cerrarConexion();
        
        return r;
        
    }

}
