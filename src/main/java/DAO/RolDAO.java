package DAO;

import DTO.Rol;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.TreeSet;

public class RolDAO extends Conexion<Rol> {

    public List<Object[]> filtrar(String parametro) throws SQLException {
        
        conectar();
        
        parametro += "%";
        parametro = parametro.toLowerCase();

        ps = con.prepareStatement("Select * from rol where rol.id like ? or lower(rol.rol) like ?");
        ps.setString(1, parametro);
        ps.setString(2, parametro);

        rs = ps.executeQuery();

        return getLista();
    }

    public TreeSet<Integer> verPermisos(Rol rol) throws SQLException {

        conectar();
        
        var lista = new TreeSet<Integer>();
        
        ps = con.prepareStatement("call verPermisosRol(?)");
        ps.setInt(1, rol.getCodigoRol());

        rs = ps.executeQuery();

        while (rs.next()) {
            lista.add(rs.getInt(1));
        }

        cerrarConexion();

        return lista;
    }

    public void crear(List<String> permisos, Rol rol) throws SQLException {
        conectar();

        con.setAutoCommit(false);

        ps = con.prepareStatement("insert into rol values(?, ?)", Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, rol.getCodigoRol());
        ps.setString(2, rol.getDescripcionRol());

        ps.executeUpdate();

        rs = ps.getGeneratedKeys();

        int rolId = 0;

        if (rs.next()) {
            rolId = rs.getInt(1);
        }

        ps = con.prepareStatement("insert into rol_operacion values(?, ?)");

        for (String permiso : permisos) {
            ps.setInt(2, rolId);
            ps.setInt(3, Integer.parseInt(permiso));
            ps.addBatch();
        }

        ps.executeBatch();

        con.commit();

        cerrarConexion();
    }

    public void Actualizar(Rol obj, List<String> ingresar) throws SQLException {
        conectar();

        con.setAutoCommit(false);

        ps = con.prepareStatement("DELETE FROM rol_operacion WHERE rol = ?");

        ps.setInt(1, obj.getCodigoRol());

        ps.executeUpdate();

        ps = con.prepareStatement("INSERT INTO rol_operacion values(?, ?, ?)");

        for (String i : ingresar) {
            ps.setInt(1, 0);
            ps.setInt(2, obj.getCodigoRol());
            ps.setInt(3 , Integer.parseInt(i));
            ps.addBatch();
        }

        ps.executeBatch();

        con.commit();
        cerrarConexion();
    }

}
