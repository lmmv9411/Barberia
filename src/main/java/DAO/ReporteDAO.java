package DAO;

import java.sql.SQLException;
import java.sql.Types;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReporteDAO extends Conexion {

    public void generarReporte(XSSFWorkbook wb, String f1, String f2, String param) throws SQLException {
        conectar();
        
        XSSFSheet sh = wb.createSheet("reporte");
        
        switch (param) {
            case "compras":
                ps = con.prepareStatement("call verCompraCompleta(?, ?)");
                crearReporte(sh, wb, f1, f2);
                break;
            case "ventas":
                ps = con.prepareStatement("call verFacturaCompleta(?, ?)");
                crearReporte(sh, wb, f1, f2);
                break;
            case "ventascredito":
                ps = con.prepareStatement("call verCreditosVentas(?, ?)");
                crearReporte(sh, wb, f1, f2);
                break;
        }
        cerrarConexion();
    }

    public void crearReporte(XSSFSheet sh, XSSFWorkbook wb, String f1, String f2) throws SQLException {

        ps.setString(1, f1);
        ps.setString(2, f2);
        rs = ps.executeQuery();

        XSSFRow row = sh.createRow(0);
        XSSFCell cell;

        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(rs.getMetaData().getColumnLabel(i + 1));
        }

        var c = 1;
        while (rs.next()) {

            row = sh.createRow(c);

            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                cell = row.createCell(i - 1);
                setValor(rs.getMetaData().getColumnType(i), cell, wb, i);
            }
            c++;
        }

        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            sh.autoSizeColumn(i);
        }

    }

    private void setValor(final int tipo, XSSFCell celda, XSSFWorkbook wb, final int colum) throws SQLException {
        switch (tipo) {
            case Types.INTEGER:
                celda.setCellValue(rs.getInt(colum));
                break;
            case Types.VARCHAR:
                celda.setCellValue(rs.getString(colum));
                break;
            case Types.TIMESTAMP:

                XSSFCreationHelper createHelper = wb.getCreationHelper();
                XSSFCellStyle cellStyle = wb.createCellStyle();
                cellStyle.setDataFormat(
                        createHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm"));
                
                celda.setCellValue(rs.getTimestamp(colum));
                celda.setCellStyle(cellStyle);
                
                break;
            case Types.DOUBLE:
                celda.setCellValue(rs.getDouble(colum));
                break;
        }
    }

}
