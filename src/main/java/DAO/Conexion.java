package DAO;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class Conexion<T> implements ICRUD<T> {

    public Conexion(String url, String db, String user, String pass) {
        this.url = url;
        this.db = db;
        this.user = user;
        this.pass = pass;
        this.preferencias = Preferences.systemRoot();
    }

    public Conexion() {
        preferencias = Preferences.systemRoot();
        url = preferencias.get("cndbur", "");
        db = preferencias.get("cndbdb", "");
        user = preferencias.get("cndbus", "");
        pass = preferencias.get("cndbps", "");
    }

    public void conectar() throws SQLException {

        if (con != null) {
            cerrarConexion();
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("!Error En El Driver!:\n" + ex.getMessage());
        }
        con = DriverManager.getConnection("jdbc:mysql://" + url + "/" + db, user, pass);
    }

    public Connection getConexion() {
        return con;
    }

    @Override
    public void cerrarConexion() {
        try {
            if (con != null) {
                con.close();
            }

            if (ps != null) {
                ps.close();
            }

            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void rollback() throws SQLException {
        con.rollback();
    }

    protected Connection con;
    protected PreparedStatement ps;
    protected ResultSet rs;
    private final String url, db, user;
    private String pass;
    private final Preferences preferencias;

    @Override
    public T mostrarPorId(T id) throws SQLException {
        return null;
    }

    @Override
    public List<T> listar() throws SQLException {
        return null;
    }

    @Override
    public void eliminar(T obj) throws SQLException {

    }

    @Override
    public void Actualizar(T obj) throws SQLException {

    }

    @Override
    public void crear(T obj) throws SQLException {

    }

    public List<Object[]> getLista() throws SQLException {

        List<Object[]> lista = new ArrayList<>();

        while (rs.next()) {
            var fila = new Object[rs.getMetaData().getColumnCount()];
            
            for (int i = 0; i < fila.length; i++) {
                fila[i] = rs.getObject(i + 1);
            }
            lista.add(fila);
        }
        
        cerrarConexion();
        
        return lista;
    }
}
