package DAO;

import DTO.Servicio;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ServicioDAO extends Conexion<Servicio> {

    @Override
    public void Actualizar(Servicio servicios) throws SQLException {
        conectar();
        ps = con.prepareStatement("update servicios set nombre = ?, precio = ?, active = ? where id = ?");

        ps.setString(1, servicios.getNombre());
        ps.setInt(2, servicios.getPrecio());
        ps.setBoolean(3, servicios.isActivo());
        ps.setInt(4, servicios.getId());

        ps.executeUpdate();
        cerrarConexion();
    }

    @Override
    public void crear(Servicio servicios) throws SQLException {
        conectar();
        ps = con.prepareStatement("insert into servicios values(?, ?, ?, ?)");
        ps.setInt(1, servicios.getId());
        ps.setString(2, servicios.getNombre());
        ps.setInt(3, servicios.getPrecio());
        ps.setBoolean(4, servicios.isActivo());
        ps.executeUpdate();
        cerrarConexion();
    }

    public List<Object[]> filtrar(String param, int pag, int nPag) throws SQLException {

        param = param + "%";

        conectar();

        ps = con.prepareStatement("Select * from servicios where nombre like ?");
        ps.setString(1, param);
        rs = ps.executeQuery();

        return getLista();
    }

    public int size() throws SQLException {
        conectar();
        int s = 0;
        ps = con.prepareStatement("select count(*) from servicios");
        rs = ps.executeQuery();
        if (rs.next()) {
            s = rs.getInt(1);
        }
        cerrarConexion();
        return s;
    }

    @Override
    public List<Servicio> listar() throws SQLException {
        conectar();
        List<Servicio> lista = new LinkedList<>();

        ps = con.prepareStatement("select * from servicios");

        rs = ps.executeQuery();

        while (rs.next()) {
            var servicio = new Servicio(
                    rs.getInt(1),
                    rs.getInt(3),
                    rs.getString(2),
                    rs.getBoolean(4));
            lista.add(servicio);
        }

        cerrarConexion();

        return lista;

    }

}
