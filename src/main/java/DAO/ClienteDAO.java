package DAO;

import DTO.Cliente;
import java.sql.SQLException;
import java.util.List;

public class ClienteDAO extends Conexion<Cliente> {

    public int getSize(String parametro) throws SQLException {
        conectar();

        int size = 0;

        parametro = parametro + "%";

        ps = con.prepareStatement("select count(*) from clientes where "
                + "lower(nombre) like ? "
                + "or lower(segundo_nombre) like ? "
                + "or lower(apellido_paterno) like ? "
                + "or lower(apellido_materno) like ?");

        ps.setString(1, parametro);
        ps.setString(2, parametro);
        ps.setString(3, parametro);
        ps.setString(4, parametro);

        rs = ps.executeQuery();
        if (rs.next()) {
            size = rs.getInt(1);
        }
        cerrarConexion();

        return size;
    }

    public List<Object[]> filtrar(String parametro, int pag, int npag, boolean asc) throws SQLException {

        conectar();

        parametro = parametro + "%";

        String sql = "select *"
                + " from clientes where "
                + "lower(nombre) like ? "
                + "or lower(segundo_nombre) like ? "
                + "or lower(apellido_paterno) like ? "
                + "or lower(apellido_materno) like ?";

        if (asc) {
            sql += "order by id limit ?, ?";
        } else {
            sql += "order by id desc limit ?, ?";
        }

        ps = con.prepareStatement(sql);
        ps.setString(1, parametro);
        ps.setString(2, parametro);
        ps.setString(3, parametro);
        ps.setString(4, parametro);
        ps.setInt(5, (pag - 1) * npag);
        ps.setInt(6, npag);

        rs = ps.executeQuery();
        
        return getLista();
        
    }

    @Override
    public void Actualizar(Cliente cliente) throws SQLException {
        conectar();
        ps = con.prepareStatement("UPDATE clientes SET nombre = ?, segundo_nombre = ?, apellido_paterno = ?, apellido_materno = ?, telefono = ?, correo = ? WHERE id = ?");
        ps.setString(1, cliente.getNombre());
        ps.setString(2, cliente.getNombreii());
        ps.setString(3, cliente.getApellido());
        ps.setString(4, cliente.getApellidoii());
        ps.setString(5, cliente.getTelefono());
        ps.setString(6, cliente.getEmail());
        ps.setInt(7, cliente.getId());
        ps.executeUpdate();
        cerrarConexion();
    }

    @Override
    public void crear(Cliente cliente) throws SQLException {

        conectar();

        ps = con.prepareStatement("insert into clientes values(?, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1, 0);
        ps.setString(2, cliente.getNombre());
        ps.setString(3, cliente.getNombreii());
        ps.setString(4, cliente.getApellido());
        ps.setString(5, cliente.getApellidoii());
        ps.setString(6, cliente.getTelefono());
        ps.setString(7, cliente.getEmail());
        ps.executeUpdate();
        cerrarConexion();

    }

}
