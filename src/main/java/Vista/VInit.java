package Vista;

import java.util.prefs.Preferences;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

public class VInit extends javax.swing.JFrame {

    public VInit() {
        initComponents();

        java.net.URL url = getClass().getResource("/images/barberia.png");

        try {
            java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(url);
            setIconImage(image);
        } catch (java.io.IOException ex) {
            System.out.println(ex);
        }

        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dpContenedor = new javax.swing.JDesktopPane();
        panelFooter = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        barraDeMenu = new javax.swing.JMenuBar();
        menuApp = new javax.swing.JMenu();
        menuCerrarSesion = new javax.swing.JMenuItem();
        menuSalir = new javax.swing.JMenuItem();
        menuVentas = new javax.swing.JMenu();
        mNuevaVenta = new javax.swing.JMenuItem();
        menuClientes = new javax.swing.JMenu();
        menuClientesCrear = new javax.swing.JMenuItem();
        menuAlmacen = new javax.swing.JMenu();
        mIngresarProducto = new javax.swing.JMenuItem();
        menuSalidaProducto = new javax.swing.JMenuItem();
        menuProducto = new javax.swing.JMenuItem();
        menuProveedor = new javax.swing.JMenuItem();
        menuConsultas = new javax.swing.JMenu();
        mConsultaVenta = new javax.swing.JMenuItem();
        mConsultaCompras = new javax.swing.JMenuItem();
        mConsultaSalidasAlmacen = new javax.swing.JMenuItem();
        mConsultaProductos = new javax.swing.JMenuItem();
        mCHistorialCrediticio = new javax.swing.JMenuItem();
        menuCredito = new javax.swing.JMenu();
        mCreditoVentas = new javax.swing.JMenuItem();
        menuMantenimiento = new javax.swing.JMenu();
        menuUsuarios = new javax.swing.JMenuItem();
        menuServicios = new javax.swing.JMenuItem();
        menuConfigDB = new javax.swing.JMenuItem();
        menuRoles = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Barbería Murillo´s");

        dpContenedor.setToolTipText("");
        dpContenedor.setName(""); // NOI18N

        lblUsuario.setText("Usuario Actual:");

        javax.swing.GroupLayout panelFooterLayout = new javax.swing.GroupLayout(panelFooter);
        panelFooter.setLayout(panelFooterLayout);
        panelFooterLayout.setHorizontalGroup(
            panelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(391, Short.MAX_VALUE))
        );
        panelFooterLayout.setVerticalGroup(
            panelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
        );

        dpContenedor.setLayer(panelFooter, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dpContenedorLayout = new javax.swing.GroupLayout(dpContenedor);
        dpContenedor.setLayout(dpContenedorLayout);
        dpContenedorLayout.setHorizontalGroup(
            dpContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dpContenedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        dpContenedorLayout.setVerticalGroup(
            dpContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dpContenedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFooter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(438, Short.MAX_VALUE))
        );

        menuApp.setText("App");

        menuCerrarSesion.setText("Cerrar Sesión");
        menuApp.add(menuCerrarSesion);

        menuSalir.setText("Salir");
        menuApp.add(menuSalir);

        barraDeMenu.add(menuApp);

        menuVentas.setText("Ventas");

        mNuevaVenta.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mNuevaVenta.setText("Nueva Venta");
        menuVentas.add(mNuevaVenta);

        barraDeMenu.add(menuVentas);

        menuClientes.setText("Clientes");

        menuClientesCrear.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuClientesCrear.setText("Crear - Actualizar");
        menuClientes.add(menuClientesCrear);

        barraDeMenu.add(menuClientes);

        menuAlmacen.setText("Almacén");

        mIngresarProducto.setText("Ingreso");
        menuAlmacen.add(mIngresarProducto);

        menuSalidaProducto.setText("Salida");
        menuAlmacen.add(menuSalidaProducto);

        menuProducto.setText("Producto");
        menuAlmacen.add(menuProducto);

        menuProveedor.setText("Proveedor");
        menuAlmacen.add(menuProveedor);

        barraDeMenu.add(menuAlmacen);

        menuConsultas.setText("Consultas");

        mConsultaVenta.setText("Ventas");
        menuConsultas.add(mConsultaVenta);

        mConsultaCompras.setText("Compras");
        menuConsultas.add(mConsultaCompras);

        mConsultaSalidasAlmacen.setText("Salidas Almacén");
        menuConsultas.add(mConsultaSalidasAlmacen);

        mConsultaProductos.setText("Productos");
        menuConsultas.add(mConsultaProductos);

        mCHistorialCrediticio.setText("Historial Crediticio");
        menuConsultas.add(mCHistorialCrediticio);

        barraDeMenu.add(menuConsultas);

        menuCredito.setText("Crédito");

        mCreditoVentas.setText("Ventas");
        menuCredito.add(mCreditoVentas);

        barraDeMenu.add(menuCredito);

        menuMantenimiento.setText("Mantenimiento");

        menuUsuarios.setText("Usuarios");
        menuMantenimiento.add(menuUsuarios);

        menuServicios.setText("Servicios");
        menuMantenimiento.add(menuServicios);

        menuConfigDB.setText("Configuración DB");
        menuMantenimiento.add(menuConfigDB);

        menuRoles.setText("Roles");
        menuMantenimiento.add(menuRoles);

        barraDeMenu.add(menuMantenimiento);

        jMenu1.setText("Temas");

        jMenuItem1.setText("Light-1");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Light-2");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem5.setText("Dark-1");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText("Dark-2");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        barraDeMenu.add(jMenu1);

        setJMenuBar(barraDeMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dpContenedor)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dpContenedor)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        cambiarTema(evt.getActionCommand());
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        cambiarTema(evt.getActionCommand());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        cambiarTema(evt.getActionCommand());
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        cambiarTema(evt.getActionCommand());
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraDeMenu;
    private javax.swing.JDesktopPane dpContenedor;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JMenuItem mCHistorialCrediticio;
    private javax.swing.JMenuItem mConsultaCompras;
    private javax.swing.JMenuItem mConsultaProductos;
    private javax.swing.JMenuItem mConsultaSalidasAlmacen;
    private javax.swing.JMenuItem mConsultaVenta;
    private javax.swing.JMenuItem mCreditoVentas;
    private javax.swing.JMenuItem mIngresarProducto;
    private javax.swing.JMenuItem mNuevaVenta;
    private javax.swing.JMenu menuAlmacen;
    private javax.swing.JMenu menuApp;
    private javax.swing.JMenuItem menuCerrarSesion;
    private javax.swing.JMenu menuClientes;
    private javax.swing.JMenuItem menuClientesCrear;
    private javax.swing.JMenuItem menuConfigDB;
    private javax.swing.JMenu menuConsultas;
    private javax.swing.JMenu menuCredito;
    private javax.swing.JMenu menuMantenimiento;
    private javax.swing.JMenuItem menuProducto;
    private javax.swing.JMenuItem menuProveedor;
    private javax.swing.JMenuItem menuRoles;
    private javax.swing.JMenuItem menuSalidaProducto;
    private javax.swing.JMenuItem menuSalir;
    private javax.swing.JMenuItem menuServicios;
    private javax.swing.JMenuItem menuUsuarios;
    private javax.swing.JMenu menuVentas;
    private javax.swing.JPanel panelFooter;
    // End of variables declaration//GEN-END:variables

    public JDesktopPane getDpContenedor() {
        return dpContenedor;
    }

    public JMenuItem getMenuClientesCrear() {
        return menuClientesCrear;
    }

    public JMenuItem getMenuServicios() {
        return menuServicios;
    }

    public JMenuItem getMenuUsuarios() {
        return menuUsuarios;
    }

    public JDesktopPane getDesktoPane() {
        return this.dpContenedor;
    }

    public JMenuItem getMenuNuevaVenta() {
        return mNuevaVenta;
    }

    public JMenuItem getmCrearProducto() {
        return menuProducto;
    }

    public JMenuItem getmIngresarProducto() {
        return mIngresarProducto;
    }

    public JMenuItem getmNuevaVenta() {
        return mNuevaVenta;
    }

    private void cambiarTema(String tema) {
        try {
            switch (tema) {
                case "Light-1":
                    javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
                    break;
                case "Light-2":
                    javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
                    break;
                case "Dark-1":
                    javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
                    break;
                case "Dark-2":
                    javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarculaLaf());
                    break;
                default:
                    javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarculaLaf());
            }

            SwingUtilities.updateComponentTreeUI(this);

            Preferences temas = Preferences.userRoot();
            temas.put("tema", javax.swing.UIManager.getLookAndFeel().getClass().getName());
        } catch (UnsupportedLookAndFeelException ex) {
            System.out.println(ex);
        }
    }

    public JMenuItem getMenuCerrarSesion() {
        return menuCerrarSesion;
    }

    public JMenuItem getMenuSalir() {
        return menuSalir;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public JMenuItem getmConsultaVenta() {
        return mConsultaVenta;
    }

    public JMenuItem getMenuConfigDB() {
        return menuConfigDB;
    }

    public JMenu getMenuClientes() {
        return menuClientes;
    }

    public JMenuItem getMenuCrearProducto() {
        return menuProducto;
    }

    public JMenu getMenuMantenimiento() {
        return menuMantenimiento;
    }

    public JMenu getMenuAlmacen() {
        return menuAlmacen;
    }

    public JMenuItem getMenuSalidaProducto() {
        return menuSalidaProducto;
    }

    public JMenu getMenuVentas() {
        return menuVentas;
    }

    public JMenuItem getMenuRoles() {
        return menuRoles;
    }

    public JMenuItem getMenuProveedor() {
        return menuProveedor;
    }

    public JMenuItem getmConsultaCompras() {
        return mConsultaCompras;
    }

    public JMenu getMenuConsultas() {
        return menuConsultas;
    }

    public JMenuItem getMenuProducto() {
        return menuProducto;
    }

    public JMenuItem getmConsultaProductos() {
        return mConsultaProductos;
    }

    public JMenuItem getmConsultaSalidasAlmacen() {
        return mConsultaSalidasAlmacen;
    }

    public JMenuItem getmCreditoVentas() {
        return mCreditoVentas;
    }

    public JMenuItem getmCHistorialCrediticio() {
        return mCHistorialCrediticio;
    }
    
    
    
}
