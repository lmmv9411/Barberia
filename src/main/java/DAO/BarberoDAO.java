package DAO;

import DTO.Barbero;
import DTO.Usuario;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BarberoDAO extends Conexion<Barbero>{

    public List<Barbero> listar(String param) throws SQLException {

        List<Barbero> listado = new LinkedList<>();
        conectar();
        ps = con.prepareStatement("call verBarbero(?)");
        ps.setString(1, param);
        rs = ps.executeQuery();
        Barbero barbero;
        Usuario usuario;
        while(rs.next()){
            barbero = new Barbero();
            barbero.setId(rs.getInt(1));
            
            usuario = new Usuario();
            usuario.setNombre(rs.getString(2));
            usuario.setApellidoPaterno(rs.getString(3));
            
            barbero.setUsuario(usuario);
            
            listado.add(barbero);
        }
        
        cerrarConexion();
        return listado;
    }

    @Override
    public List<Barbero> listar() throws SQLException {
        conectar();
        List<Barbero> listado = new LinkedList<>();
        ps = con.prepareCall("select barberos.*, usuarios.* from barberos inner join usuarios on usuarios.id = barberos.usuario");
        
        rs = ps.executeQuery();
        
        while(rs.next()){
            var barbero = new Barbero(
                    rs.getInt(1),
                    new Usuario(rs.getString(2), 
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            null, 
                            null, 
                            false, 
                            null)
            );
            listado.add(barbero);
        }
        
        cerrarConexion();
        return listado;
    }
    
}