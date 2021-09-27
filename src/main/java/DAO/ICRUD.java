package DAO;

public interface ICRUD<T> {
    
    T mostrarPorId(T id) throws java.sql.SQLException;
    java.util.List<T> listar() throws java.sql.SQLException;
    void eliminar(T persona) throws java.sql.SQLException;
    void Actualizar(T persona) throws java.sql.SQLException;
    void crear(T persona) throws java.sql.SQLException;
    void cerrarConexion();
    void rollback() throws java.sql.SQLException;
}
