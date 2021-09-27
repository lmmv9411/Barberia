package Utilidades;

import java.awt.Color;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControladorPaginacion {
    
    public ControladorPaginacion(Paginacion controlador, JPanel panel) {
        paginaActual = 1;
        this.controlador = controlador;
        this.panelPaginacion = panel;
        p = Preferences.userRoot();
        FILASAMOSTRAR = p.getInt("FilasMostrar", 10);
        PAGINASAMOSTRAR = p.getInt("PaginasMostrar", 5);

    }
    
    public void crearBotones(int pagActual) {
        
        totalFilas = controlador.getSize();//controladorClientes.getSize();
        totalPaginas = totalFilas / FILASAMOSTRAR;
        
        if (totalFilas % FILASAMOSTRAR > 0) {
            totalPaginas++;
        }
        
        paginaActual = pagActual;
        
        paginaInicio = ((int) (paginaActual / PAGINASAMOSTRAR)) * PAGINASAMOSTRAR;
        
        if (paginaActual % PAGINASAMOSTRAR == 0) {
            paginaInicio = ((int) (paginaActual / PAGINASAMOSTRAR - 1)) * PAGINASAMOSTRAR;
        }
        
        paginaFinal = paginaInicio + PAGINASAMOSTRAR;
        
        panelPaginacion.removeAll();
        
        if (paginaInicio + 1 > PAGINASAMOSTRAR) {
            JButton btn = new JButton("<");
            btn.addActionListener(e -> moverseDePagina(e));
            panelPaginacion.add(btn);
            panelPaginacion.updateUI();
        }
        
        for (int i = paginaInicio; i < paginaFinal; i++) {
            
            if (i == totalPaginas) {
                break;
            }
            JButton btn = new JButton((i + 1) + "");
            
            if (paginaActual - 1 == i) {
                btn.setBackground(Color.GREEN);
                btn.setForeground(Color.BLACK);
            }
            
            btn.addActionListener(e -> moverseDePagina(e));
            
            panelPaginacion.add(btn);
            panelPaginacion.updateUI();
        }
        
        if (totalPaginas > paginaFinal) {
            JButton btn = new JButton(">");
            btn.addActionListener(e -> moverseDePagina(e));
            panelPaginacion.add(btn);
            panelPaginacion.updateUI();
        }
        
    }
    
    private void moverseDePagina(java.awt.event.ActionEvent e) {
        String txt = ((JButton) e.getSource()).getText();
        
        switch (txt) {
            case ">":
                controlador.paginar(paginaFinal + 1, FILASAMOSTRAR);
                break;
            case "<":
                controlador.paginar(paginaInicio, FILASAMOSTRAR);
                break;
            default:
                panelPaginacion.updateUI();
                controlador.paginar(Integer.parseInt(txt), FILASAMOSTRAR);
                crearBotones(Integer.parseInt(txt));
        }
    }
    
    public int getPaginaActual() {
        return paginaActual;
    }
    
    public int getFILASAMOSTRAR() {
        return FILASAMOSTRAR;
    }
    
    public int getPAGINASAMOSTRAR() {
        return PAGINASAMOSTRAR;
    }

    public void setFILASAMOSTRAR(int FILASAMOSTRAR) {
        this.FILASAMOSTRAR = FILASAMOSTRAR;
        p.putInt("FilasMostrar", FILASAMOSTRAR);
    }

    public void setPAGINASAMOSTRAR(int PAGINASAMOSTRAR) {
        this.PAGINASAMOSTRAR = PAGINASAMOSTRAR;
        p.putInt("PaginasMostrar", PAGINASAMOSTRAR);
    }
    
    private Preferences p;
    private int totalFilas, totalPaginas;
    private int FILASAMOSTRAR = 10;
    private int PAGINASAMOSTRAR = 3;
    private int paginaActual, paginaInicio, paginaFinal;
    private final JPanel panelPaginacion;
    private final Paginacion controlador;
    
}
