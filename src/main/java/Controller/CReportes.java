package Controller;

import IController.IReportes;
import DAO.ReporteDAO;
import Vista.VReportes;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CReportes {

    public CReportes(VReportes vista, IReportes ireportes) {
        this.vista = vista;
        preferences = Preferences.userRoot();
        this.vista.getTxtRuta().setText(preferences.get("rutaReportes", ""));
        iniciarEventos();
        dao = new ReporteDAO();
        this.ireportes = ireportes;
    }

    private void iniciarEventos() {
        vista.getBtnSeleccionar().addActionListener(this::eventos);
        vista.getBtnGenerar().addActionListener(this::eventos);
    }

    private void eventos(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Seleccionar":
                JFileChooser fc = new JFileChooser(preferences.get("rutaReportes", ""));
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setAcceptAllFileFilterUsed(false);
                if (fc.showDialog(vista.getParent(), "Seleccinar") == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    vista.getTxtRuta().setText(f.getAbsolutePath());
                    preferences.put("rutaReportes", f.getAbsolutePath());
                }
                break;
            case "Generar":

                SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
                Calendar c = Calendar.getInstance();
                
                String param = ireportes.tipo();
                
                String nombre = ireportes.tipo();
                
                nombre += sdf.format(c.getTime()) + ".xlsx";
                String path = preferences.get("rutaReportes", "") + File.separator + nombre;

                sdf.applyPattern("yyyy-MM-dd");

                try (FileOutputStream fos = new FileOutputStream(path)) {

                    XSSFWorkbook wk = new XSSFWorkbook();
                    
                    dao.generarReporte(wk ,ireportes.fechaInicial(), ireportes.fechaFinal(), param);
                    wk.write(fos);
                    fos.flush();
                    JOptionPane.showMessageDialog(vista.getParent(), "¡Guardado!", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(vista.getParent(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    if (ex.getErrorCode() == 0) {
                        JOptionPane.showMessageDialog(vista.getParent(), "¡Error de conexión a la base de datos!", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(vista.getParent(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                break;
        }
    }

    private final VReportes vista;
    private final Preferences preferences;
    private final ReporteDAO dao;
    private final IReportes ireportes;

}
