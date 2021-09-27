package DAO;

import DTO.Rol;
import DTO.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UsuarioDAO extends Conexion<Usuario> {

    public List<DTO.Rol> getRoles() throws SQLException {

        conectar();
        ps = con.prepareStatement("select * from rol");
        rs = ps.executeQuery();

        List<DTO.Rol> lista = new ArrayList<>();

        while (rs.next()) {
            
            var rol = new DTO.Rol(rs.getInt(1), rs.getString(2));
            
            lista.add(rol);
        }

        cerrarConexion();

        return lista;
    }

    @Override
    public Usuario mostrarPorId(Usuario usuario) throws SQLException {

        conectar();
        ps = con.prepareStatement("select * from usuarios where id = ?");
        ps.setString(1, usuario.getId());
        rs = ps.executeQuery();

        if (rs.next()) {
            usuario = new Usuario(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("segundo_nombre"),
                    rs.getString("apellido_paterno"),
                    rs.getString("apellido_materno"),
                    new Rol(rs.getInt("rol"), ""),
                    rs.getString("contrasena"),
                    rs.getBoolean("activo"),
                    null
            );
            usuario.setPermisos(verPermisos(usuario));
        } else {
            usuario = null;
        }
        cerrarConexion();

        return usuario;
    }

    public List<Object[]> listar(String param, int pag, int nPag) throws SQLException {

        conectar();
        
        ps = con.prepareStatement("call verUsuarios(?, ?, ?)");
        ps.setString(1, param);
        ps.setInt(2, (pag - 1) * nPag);
        ps.setInt(3, nPag);
        
        rs = ps.executeQuery();
        
        return getLista();
    }

    @Override
    public void Actualizar(Usuario usuario) throws SQLException {
        conectar();
        ps = con.prepareStatement("UPDATE usuarios SET nombre= ?, segundo_nombre= ? , apellido_paterno = ?, apellido_materno = ?, rol = ?, contrasena = ?, activo = ? WHERE id = ?");
        ps.setString(1, usuario.getNombre());
        ps.setString(2, usuario.getSegundoNombre());
        ps.setString(3, usuario.getApellidoPaterno());
        ps.setString(4, usuario.getApellidoMaterno());
        ps.setInt(5, usuario.getRol().getCodigoRol());
        ps.setString(6, usuario.getContrasena());
        ps.setBoolean(7, usuario.isActivo());
        ps.setString(8, usuario.getId());
        ps.executeUpdate();
        cerrarConexion();
    }

    @Override
    public void crear(Usuario usuario) throws SQLException {

        conectar();
        ps = con.prepareStatement("insert into usuarios values(?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, usuario.getId());
        ps.setString(2, usuario.getNombre());
        ps.setString(3, usuario.getSegundoNombre());
        ps.setString(4, usuario.getApellidoPaterno());
        ps.setString(5, usuario.getApellidoMaterno());
        ps.setInt(6, usuario.getRol().getCodigoRol());
        ps.setString(7, usuario.getContrasena());
        ps.setBoolean(8, usuario.isActivo());
        ps.executeUpdate();
        cerrarConexion();

    }

    public int getSize(String param) throws java.sql.SQLException {
        param = "%" + param + "%";
        conectar();
        ps = con.prepareStatement("select count(*) from usuarios INNER JOIN rol ON usuarios.rol = rol.id where usuarios.id like ? or usuarios.nombre like ? or usuarios.segundo_nombre like ? or usuarios.apellido_paterno like ?");
        ps.setString(1, param);
        ps.setString(2, param);
        ps.setString(3, param);
        ps.setString(4, param);
        rs = ps.executeQuery();
        int size = 0;
        if (rs.next()) {
            size = rs.getInt(1);
            cerrarConexion();
            return size;
        }
        return size;
    }

    public Map<String, List<String>> verPermisos(Usuario usuario) throws SQLException {

        Map<String, List<String>> map = new TreeMap<>();
        List<String> lista;
        conectar();

        ps = con.prepareStatement("call verPermisos(?)");
        ps.setString(1, usuario.getId());

        rs = ps.executeQuery();

        while (rs.next()) {

            String key = rs.getString(1);
            String value = rs.getString(2);

            if (!map.containsKey(key)) {
                lista = new LinkedList<>();
                lista.add(value);
                map.put(key, lista);
            } else {
                map.get(key).add(value);
            }
        }

        cerrarConexion();

        return map;
    }

}
