package reportes;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Imprimir {

    public void imprimirPorId(int id, String reporte, String parametro) {
        try {
            JasperReport report = getReport(reporte);

            Map<String, Object> parametros = new TreeMap<>();
            parametros.put(parametro, id);

            DAO.Conexion con = new DAO.Conexion();
            con.conectar();
            
            JasperPrint jprint = JasperFillManager.fillReport(report, parametros, con.getConexion());
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            view.setTitle("Reporte");

            view.setVisible(true);
        } catch (JRException | SQLException ex) {
            System.out.println(ex);
        }
    }

    public JasperReport getReport(String reporte) {
        try {
            switch (reporte) {
                case "ventas":
                    return (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/Factura.jasper"));
                case "compras":
                    return (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/Compras.jasper"));
            }
        } catch (JRException ex) {
            System.out.println(ex);
        }

        return null;
    }

}
