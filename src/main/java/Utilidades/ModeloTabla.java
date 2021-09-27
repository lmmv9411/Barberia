package Utilidades;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTabla extends AbstractTableModel {

    public ModeloTabla(Object[] encabezados) {
        this.ENCABEZADOS = encabezados;
        lista = new java.util.ArrayList<>();
    }

    public ModeloTabla(Object[] encabezados, List<Object[]> data) {
        this.ENCABEZADOS = encabezados;
        lista = data;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return this.ENCABEZADOS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       return lista.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return ENCABEZADOS[column].toString();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (lista.size() > 0) {
            return lista.get(0)[columnIndex].getClass();
        }
        return String.class;
    }

    public List<Object[]> getData() {
        return lista;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        lista.get(rowIndex)[columnIndex] = aValue;
        fireTableDataChanged();
    }

    /**
     * Eliminar fila de estructura de datos y del JTable.
     *
     * @param row Fila a eliminar
     *
     */
    public void eliminarFila(int row) {
        lista.remove(row);
        fireTableDataChanged();
    }

    /**
     * Eliminar todo el contenido de la estructura de datos y del JTable
     */
    public void eliminarFilas() {
        lista.removeAll(lista);
        fireTableDataChanged();
    }

    public void setData(List<Object[]> datos) {
        lista = datos;
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object[] getValorFila(int row) {
        return lista.get(row);
    }
    
    public void setFila(Object[] row){
        lista.add(row);
        fireTableDataChanged();
    }

    private List<Object[]> lista;
    private final Object[] ENCABEZADOS;
}
