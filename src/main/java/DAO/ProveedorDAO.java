package DAO;

import DTO.Proveedor;
import java.sql.SQLException;
import java.util.List;

public class ProveedorDAO extends Conexion<Proveedor> {

    public List<Object[]> listar(String parametro, int pag, int npag) throws SQLException {
        
        conectar();

        parametro = "%" + parametro + "%";
        
        String sql = "select * from proveedores where id like ? or lower(nombre) like ? limit ?, ?";
            
        ps = con.prepareStatement(sql);
        ps.setString(1, parametro);
        ps.setString(2, parametro);
        ps.setInt(3, (pag - 1) * npag);
        ps.setInt(4, npag);

        rs = ps.executeQuery();

        return getLista();
    }

    public int getMax(String parametro) throws SQLException{
        parametro = "%" + parametro + "%";
        conectar();
        ps = con.prepareStatement("select count(*) from proveedores where id like ? or lower(nombre) like ?");
        ps.setString(1, parametro);
        ps.setString(2, parametro);
        rs = ps.executeQuery();
        int r = 0;
        if(rs.next()){
            r = rs.getInt(1);
        }
        cerrarConexion();
        return r;
    }

    @Override
    public void crear(Proveedor proveedor) throws SQLException {
        conectar();
        ps = con.prepareStatement("insert into proveedores values(?, ?, ?, ?, ?)");
        ps.setString(1, proveedor.getId());
        ps.setString(2, proveedor.getNombre());
        ps.setString(3, proveedor.getTelefono());
        ps.setString(4, proveedor.getDireccion());
        ps.setString(5, proveedor.getEmail());
        ps.executeUpdate();
        cerrarConexion();
    }

    @Override
    public void Actualizar(Proveedor obj) throws SQLException {
        conectar();
        ps = con.prepareStatement("UPDATE proveedores SET nombre= ?, telefono = ?, direccion = ?, email = ? WHERE id = ?");
        ps.setString(1, obj.getNombre());
        ps.setString(2, obj.getTelefono());
        ps.setString(3, obj.getDireccion());
        ps.setString(4, obj.getEmail());
        ps.setString(5, obj.getId());
        
        ps.executeUpdate();
        
        cerrarConexion();
    }
    
}
