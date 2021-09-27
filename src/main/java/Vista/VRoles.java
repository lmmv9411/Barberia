package Vista;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class VRoles extends javax.swing.JInternalFrame {

    public VRoles() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPermisos = new javax.swing.JPanel();
        chkConsultarVenta = new javax.swing.JCheckBox();
        chkCrearVenta = new javax.swing.JCheckBox();
        chkAnularventa = new javax.swing.JCheckBox();
        chkCrearCliente = new javax.swing.JCheckBox();
        chkActualizarCliente = new javax.swing.JCheckBox();
        chkConsultarCliente = new javax.swing.JCheckBox();
        chkCrearProducto = new javax.swing.JCheckBox();
        chkCrearProveedor = new javax.swing.JCheckBox();
        chkActualizarProveedor = new javax.swing.JCheckBox();
        chkConsultarProveedor = new javax.swing.JCheckBox();
        chkCrearCompra = new javax.swing.JCheckBox();
        chkAnularCompra = new javax.swing.JCheckBox();
        chkConsultarCompra = new javax.swing.JCheckBox();
        chkConsultarProducto = new javax.swing.JCheckBox();
        chkActualizarProducto = new javax.swing.JCheckBox();
        chkCrearUsuario = new javax.swing.JCheckBox();
        chkActualizarUsuario = new javax.swing.JCheckBox();
        chkConsultarUsuario = new javax.swing.JCheckBox();
        chkCrearServicio = new javax.swing.JCheckBox();
        chkActualizarServicio = new javax.swing.JCheckBox();
        chkConsultarServicio = new javax.swing.JCheckBox();
        chkReporteVenta = new javax.swing.JCheckBox();
        chkReporteCompra = new javax.swing.JCheckBox();
        chkRoles = new javax.swing.JCheckBox();
        chkAlmacen = new javax.swing.JCheckBox();
        chkBaseDeDatos = new javax.swing.JCheckBox();
        chkConsultas = new javax.swing.JCheckBox();
        chkManteniento = new javax.swing.JCheckBox();
        panelBuscar = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRoles = new javax.swing.JTable();
        panelCrear = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreRol = new javax.swing.JTextField();
        btnCrearRol = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Roles");

        panelPermisos.setBorder(javax.swing.BorderFactory.createTitledBorder("Permisos"));

        chkConsultarVenta.setText("Consultar Venta");
        chkConsultarVenta.setName("13"); // NOI18N

        chkCrearVenta.setText("Crear Venta");
        chkCrearVenta.setName("1"); // NOI18N

        chkAnularventa.setText("Anular Venta");
        chkAnularventa.setName("3"); // NOI18N

        chkCrearCliente.setText("Crear Cliente");
        chkCrearCliente.setName("4"); // NOI18N

        chkActualizarCliente.setText("Actualizar Cliente");
        chkActualizarCliente.setName("6"); // NOI18N

        chkConsultarCliente.setText("Consultar Cliente");
        chkConsultarCliente.setName("5"); // NOI18N

        chkCrearProducto.setText("Crear Producto");
        chkCrearProducto.setName("20"); // NOI18N

        chkCrearProveedor.setText("Crear Proveedor");
        chkCrearProveedor.setName("23"); // NOI18N

        chkActualizarProveedor.setText("Actualizar Proveedor");
        chkActualizarProveedor.setName("25"); // NOI18N

        chkConsultarProveedor.setText("Consultar Proveedor");
        chkConsultarProveedor.setName("24"); // NOI18N

        chkCrearCompra.setText("Crear Compra");
        chkCrearCompra.setName("15"); // NOI18N

        chkAnularCompra.setText("Anular Compra");
        chkAnularCompra.setName("16"); // NOI18N
        chkAnularCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAnularCompraActionPerformed(evt);
            }
        });

        chkConsultarCompra.setText("Consultar Compra");
        chkConsultarCompra.setName("7"); // NOI18N

        chkConsultarProducto.setText("Consultar Producto");
        chkConsultarProducto.setName("21"); // NOI18N

        chkActualizarProducto.setText("Actualizar Producto");
        chkActualizarProducto.setName("22"); // NOI18N

        chkCrearUsuario.setText("Crear Usuario");
        chkCrearUsuario.setName("26"); // NOI18N

        chkActualizarUsuario.setText("Actualizar Usuario");
        chkActualizarUsuario.setName("28"); // NOI18N

        chkConsultarUsuario.setText("Consultar Usuario");
        chkConsultarUsuario.setName("27"); // NOI18N

        chkCrearServicio.setText("Crear Servicio");
        chkCrearServicio.setName("29"); // NOI18N

        chkActualizarServicio.setText("Actualizar Servicio");
        chkActualizarServicio.setName("31"); // NOI18N

        chkConsultarServicio.setText("Consultar Servicio");
        chkConsultarServicio.setName("30"); // NOI18N

        chkReporteVenta.setText("Informe Venta");
        chkReporteVenta.setName("11"); // NOI18N

        chkReporteCompra.setText("Informe Compra");
        chkReporteCompra.setName("10"); // NOI18N

        chkRoles.setText("Roles");
        chkRoles.setName("32"); // NOI18N

        chkAlmacen.setText("Menu Almacén");
        chkAlmacen.setName("8"); // NOI18N

        chkBaseDeDatos.setText("Menu Base De Datos");
        chkBaseDeDatos.setName("33"); // NOI18N

        chkConsultas.setText("Menu Consultas");
        chkConsultas.setName("14"); // NOI18N

        chkManteniento.setText("Menu Mantenimiento");
        chkManteniento.setName("9"); // NOI18N

        javax.swing.GroupLayout panelPermisosLayout = new javax.swing.GroupLayout(panelPermisos);
        panelPermisos.setLayout(panelPermisosLayout);
        panelPermisosLayout.setHorizontalGroup(
            panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPermisosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPermisosLayout.createSequentialGroup()
                        .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(chkCrearVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkConsultarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkAnularventa, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkCrearCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkConsultarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkActualizarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(chkAnularCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkConsultarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkCrearCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkConsultarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkCrearProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPermisosLayout.createSequentialGroup()
                                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(chkCrearProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(chkActualizarProveedor)
                                    .addComponent(chkConsultarProveedor))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(chkConsultarServicio)
                                    .addComponent(chkActualizarServicio)
                                    .addComponent(chkCrearServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelPermisosLayout.createSequentialGroup()
                                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(chkCrearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(chkActualizarUsuario)
                                    .addComponent(chkConsultarUsuario))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(chkRoles)
                                    .addComponent(chkReporteCompra)
                                    .addComponent(chkReporteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panelPermisosLayout.createSequentialGroup()
                        .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(chkAlmacen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chkConsultas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chkBaseDeDatos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(chkManteniento)))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        panelPermisosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {chkActualizarProducto, chkAnularCompra, chkConsultarCompra, chkConsultarProducto, chkCrearCompra, chkCrearProducto});

        panelPermisosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {chkActualizarCliente, chkAnularventa, chkConsultarCliente, chkConsultarVenta, chkCrearCliente, chkCrearVenta});

        panelPermisosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {chkActualizarProveedor, chkConsultarProveedor, chkCrearProveedor});

        panelPermisosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {chkActualizarUsuario, chkConsultarUsuario, chkCrearUsuario});

        panelPermisosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {chkActualizarServicio, chkConsultarServicio, chkCrearServicio});

        panelPermisosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {chkReporteCompra, chkReporteVenta, chkRoles});

        panelPermisosLayout.setVerticalGroup(
            panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPermisosLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPermisosLayout.createSequentialGroup()
                        .addComponent(chkCrearProveedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkActualizarProveedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkConsultarProveedor))
                    .addGroup(panelPermisosLayout.createSequentialGroup()
                        .addComponent(chkCrearVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkConsultarVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkAnularventa))
                    .addComponent(chkCrearProducto)
                    .addGroup(panelPermisosLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(chkConsultarProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkCrearCompra))
                    .addGroup(panelPermisosLayout.createSequentialGroup()
                        .addComponent(chkCrearServicio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkActualizarServicio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkConsultarServicio)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelPermisosLayout.createSequentialGroup()
                        .addComponent(chkActualizarProducto)
                        .addGap(78, 78, 78))
                    .addGroup(panelPermisosLayout.createSequentialGroup()
                        .addComponent(chkCrearCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkConsultarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkActualizarCliente))
                    .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelPermisosLayout.createSequentialGroup()
                            .addComponent(chkReporteVenta)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(chkReporteCompra))
                        .addGroup(panelPermisosLayout.createSequentialGroup()
                            .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(panelPermisosLayout.createSequentialGroup()
                                    .addComponent(chkAnularCompra)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(chkConsultarCompra))
                                .addGroup(panelPermisosLayout.createSequentialGroup()
                                    .addComponent(chkCrearUsuario)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(chkActualizarUsuario)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(chkConsultarUsuario)
                                .addComponent(chkRoles)))))
                .addGap(18, 18, 18)
                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkAlmacen)
                    .addComponent(chkManteniento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkBaseDeDatos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkConsultas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelPermisosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {chkActualizarProducto, chkAnularCompra, chkConsultarCompra, chkConsultarProducto, chkCrearCompra, chkCrearProducto});

        panelPermisosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {chkActualizarCliente, chkAnularventa, chkConsultarCliente, chkConsultarVenta, chkCrearCliente, chkCrearVenta});

        panelPermisosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {chkActualizarProveedor, chkConsultarProveedor, chkCrearProveedor});

        panelPermisosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {chkActualizarUsuario, chkConsultarUsuario, chkCrearUsuario});

        panelPermisosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {chkActualizarServicio, chkConsultarServicio, chkCrearServicio});

        panelPermisosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {chkReporteCompra, chkReporteVenta, chkRoles});

        panelBuscar.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

        btnBuscar.setText("Buscar");

        tblRoles.setShowGrid(true);
        jScrollPane1.setViewportView(tblRoles);

        javax.swing.GroupLayout panelBuscarLayout = new javax.swing.GroupLayout(panelBuscar);
        panelBuscar.setLayout(panelBuscarLayout);
        panelBuscarLayout.setHorizontalGroup(
            panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelBuscarLayout.createSequentialGroup()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBuscarLayout.setVerticalGroup(
            panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelCrear.setBorder(javax.swing.BorderFactory.createTitledBorder("Crear"));

        jLabel1.setText("Nombre");

        btnCrearRol.setText("Crear");

        javax.swing.GroupLayout panelCrearLayout = new javax.swing.GroupLayout(panelCrear);
        panelCrear.setLayout(panelCrearLayout);
        panelCrearLayout.setHorizontalGroup(
            panelCrearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCrearLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(panelCrearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCrearLayout.createSequentialGroup()
                        .addComponent(btnCrearRol)
                        .addGap(0, 0, 0))
                    .addComponent(txtNombreRol))
                .addContainerGap())
        );
        panelCrearLayout.setVerticalGroup(
            panelCrearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCrearLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCrearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombreRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCrearRol)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnActualizar.setText("Actualizar");

        btnLimpiar.setText("Limpiar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPermisos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnActualizar)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpiar)
                                .addGap(0, 0, 0))
                            .addComponent(panelCrear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelCrear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnActualizar)
                            .addComponent(btnLimpiar))))
                .addGap(18, 18, 18)
                .addComponent(panelPermisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkAnularCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAnularCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkAnularCompraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCrearRol;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox chkActualizarCliente;
    private javax.swing.JCheckBox chkActualizarProducto;
    private javax.swing.JCheckBox chkActualizarProveedor;
    private javax.swing.JCheckBox chkActualizarServicio;
    private javax.swing.JCheckBox chkActualizarUsuario;
    private javax.swing.JCheckBox chkAlmacen;
    private javax.swing.JCheckBox chkAnularCompra;
    private javax.swing.JCheckBox chkAnularventa;
    private javax.swing.JCheckBox chkBaseDeDatos;
    private javax.swing.JCheckBox chkConsultarCliente;
    private javax.swing.JCheckBox chkConsultarCompra;
    private javax.swing.JCheckBox chkConsultarProducto;
    private javax.swing.JCheckBox chkConsultarProveedor;
    private javax.swing.JCheckBox chkConsultarServicio;
    private javax.swing.JCheckBox chkConsultarUsuario;
    private javax.swing.JCheckBox chkConsultarVenta;
    private javax.swing.JCheckBox chkConsultas;
    private javax.swing.JCheckBox chkCrearCliente;
    private javax.swing.JCheckBox chkCrearCompra;
    private javax.swing.JCheckBox chkCrearProducto;
    private javax.swing.JCheckBox chkCrearProveedor;
    private javax.swing.JCheckBox chkCrearServicio;
    private javax.swing.JCheckBox chkCrearUsuario;
    private javax.swing.JCheckBox chkCrearVenta;
    private javax.swing.JCheckBox chkManteniento;
    private javax.swing.JCheckBox chkReporteCompra;
    private javax.swing.JCheckBox chkReporteVenta;
    private javax.swing.JCheckBox chkRoles;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelBuscar;
    private javax.swing.JPanel panelCrear;
    private javax.swing.JPanel panelPermisos;
    private javax.swing.JTable tblRoles;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtNombreRol;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnCrearRol() {
        return btnCrearRol;
    }

    public JCheckBox getChkActualizarCliente() {
        return chkActualizarCliente;
    }

    public JCheckBox getChkActualizarProducto() {
        return chkActualizarProducto;
    }

    public JCheckBox getChkActualizarProveedor() {
        return chkActualizarProveedor;
    }

    public JCheckBox getChkActualizarServicio() {
        return chkActualizarServicio;
    }

    public JCheckBox getChkActualizarUsuario() {
        return chkActualizarUsuario;
    }

    public JCheckBox getChkAnularCompra() {
        return chkAnularCompra;
    }

    public JCheckBox getChkAnularventa() {
        return chkAnularventa;
    }

    public JCheckBox getChkConsultarCliente() {
        return chkConsultarCliente;
    }

    public JCheckBox getChkConsultarCompra() {
        return chkConsultarCompra;
    }

    public JCheckBox getChkConsultarProducto() {
        return chkConsultarProducto;
    }

    public JCheckBox getChkConsultarProveedor() {
        return chkConsultarProveedor;
    }

    public JCheckBox getChkConsultarServicio() {
        return chkConsultarServicio;
    }

    public JCheckBox getChkConsultarUsuario() {
        return chkConsultarUsuario;
    }

    public JCheckBox getChkConsultarVenta() {
        return chkConsultarVenta;
    }

    public JCheckBox getChkCrearCliente() {
        return chkCrearCliente;
    }

    public JCheckBox getChkCrearCompra() {
        return chkCrearCompra;
    }

    public JCheckBox getChkCrearProducto() {
        return chkCrearProducto;
    }

    public JCheckBox getChkCrearProveedor() {
        return chkCrearProveedor;
    }

    public JCheckBox getChkCrearServicio() {
        return chkCrearServicio;
    }

    public JCheckBox getChkCrearUsuario() {
        return chkCrearUsuario;
    }

    public JCheckBox getChkCrearVenta() {
        return chkCrearVenta;
    }

    public JCheckBox getChkReporteCompra() {
        return chkReporteCompra;
    }

    public JCheckBox getChkReporteVenta() {
        return chkReporteVenta;
    }

    public JCheckBox getChkRoles() {
        return chkRoles;
    }

    public JCheckBox getChkAlmacen() {
        return chkAlmacen;
    }

    public JCheckBox getChkBaseDeDatos() {
        return chkBaseDeDatos;
    }

    public JCheckBox getChkConsultas() {
        return chkConsultas;
    }

    public JCheckBox getChkManteniento() {
        return chkManteniento;
    }

    
    
    public JPanel getPanelBuscar() {
        return panelBuscar;
    }

    public JPanel getPanelCrear() {
        return panelCrear;
    }

    public JPanel getPanelPermisos() {
        return panelPermisos;
    }

    public JTable getTblRoles() {
        return tblRoles;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JTextField getTxtNombreRol() {
        return txtNombreRol;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

}
