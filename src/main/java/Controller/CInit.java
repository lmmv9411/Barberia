package Controller;

import DTO.Usuario;
import Vista.*;

public class CInit {

    public CInit(VInit init) {

        this.init = init;

        this.dpContenedor = init.getDesktoPane();

        iniciarEventos();

        permisos();

        String txt = this.init.getLblUsuario().getText();

        String usr = usuario.getNombre() + " " + usuario.getApellidoPaterno();

        this.init.getLblUsuario().setText(txt + " " + usr);

    }

    private void permisos() {
        
        usuario = Utilidades.Sesion.getUsuario();

        var permisos = usuario.getPermisos();

        init.getMenuVentas().setVisible(permisos.containsKey("ventas"));
        init.getMenuClientes().setVisible(permisos.containsKey("ventas"));

        //No modificar ésto, es el acceso a crear solamente.
        init.getMenuAlmacen().setVisible(permisos.containsKey("almacen"));

        if (permisos.containsKey("almacen")) {

            var permiso = permisos.get("compras");
            init.getmIngresarProducto().setVisible(permiso != null && permiso.contains("crear"));

            permiso = permisos.get("salida_almacen");
            init.getMenuSalidaProducto().setVisible(permiso != null && permiso.contains("crear"));

            permiso = permisos.get("producto");
            init.getMenuProducto().setVisible(permiso != null && permiso.contains("crear"));

            permiso = permisos.get("proveedor");
            init.getMenuProveedor().setVisible(permiso != null && permiso.contains("crear"));
        }
        //----

        if (permisos.containsKey("consultas")) {
            init.getMenuConsultas().setVisible(true);
            var permiso = permisos.get("consultas");
            init.getmConsultaCompras().setVisible(permiso.contains("compras"));
            init.getmConsultaVenta().setVisible(permiso.contains("ventas"));
            init.getmConsultaProductos().setVisible(permiso.contains("productos"));
            init.getmConsultaSalidasAlmacen().setVisible(permiso.contains("salidas almacen"));
        } else {
            init.getMenuConsultas().setVisible(false);
        }

        if (permisos.containsKey("mantenimiento")) {
            init.getMenuMantenimiento().setVisible(true);
            init.getMenuUsuarios().setVisible(permisos.get("usuarios").contains("crear"));
            init.getMenuServicios().setVisible(permisos.get("servicios").contains("crear"));
            init.getMenuConfigDB().setVisible(permisos.get("base_de_datos").contains("acceso"));
            init.getMenuRoles().setVisible(permisos.get("roles").contains("acceso"));
        } else {
            init.getMenuMantenimiento().setVisible(false);
        }

    }

    private void iniciarEventos() {
        init.getMenuClientesCrear().addActionListener(this::eventos);
        init.getMenuUsuarios().addActionListener(this::eventos);
        init.getMenuServicios().addActionListener(this::eventos);
        init.getmNuevaVenta().addActionListener(this::eventos);
        init.getMenuCerrarSesion().addActionListener(this::eventos);
        init.getMenuSalir().addActionListener(this::eventos);
        init.getmConsultaVenta().addActionListener(e -> {
            if (VCFactura == null || VCFactura.isClosed()) {
                VCFactura = new VConsulta();
                new CConsultaFacturas(VCFactura);
                centrar(VCFactura);
            }
        });

        init.getmConsultaCompras().addActionListener(e -> {
            if (VCCompra == null || VCCompra.isClosed()) {
                VCCompra = new VConsulta();
                var cFactura = new CConsultaCompras(VCCompra);
                centrar(VCCompra);
            }
        });

        init.getMenuCerrarSesion().addActionListener(this::eventos);
        init.getMenuConfigDB().addActionListener(this::eventos);
        init.getmIngresarProducto().addActionListener(this::eventos);
        init.getMenuRoles().addActionListener(this::eventos);

        //Se hace así para no confundir nombres ingreso-actualizar.
        init.getmCrearProducto().addActionListener(e -> {
            var producto = new VProducto();
            CProductos cpr = new CProductos(producto);
            centrar(producto);
        });

        init.getMenuProveedor().addActionListener(e -> {
            var proveedor = new VProveedor();
            CProveedor cp = new CProveedor(proveedor);
            centrar(proveedor);
        });

        init.getmCreditoVentas().addActionListener(e -> {
            CVentasPorCobrar.getInstance(VVentaPorCobrar.getInstance());
            centrar(VVentaPorCobrar.getInstance());
        });

        init.getmCHistorialCrediticio().addActionListener(e -> {
            var historialCrediticio = new VConsulta();
            var controlador = new CHistorialCrediticio(historialCrediticio);
            centrar(historialCrediticio);
        });
        
        init.getmConsultaProductos().addActionListener(e -> {
            var consulta = new VBuscar();
            var ctrl = new CProductoVista(consulta, null);
            centrar(consulta);
        });
        
        init.getMenuSalidaProducto().addActionListener(this::eventos);
        
    }

    private void eventos(java.awt.event.ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Crear - Actualizar":
                if (vCliente == null || vCliente.isClosed()) {
                    vCliente = new VCliente();
                    var cClientes = new CClientes(vCliente, true);
                    centrar(vCliente);
                }
                break;
            case "Usuarios":
                if (vUsuario == null || vUsuario.isClosed()) {
                    vUsuario = new VUsuario();
                    var cUsuarios = new CUsuarios(vUsuario);
                    centrar(vUsuario);
                }
                break;
            case "Servicios":
                if (vServicio == null || vServicio.isClosed()) {
                    vServicio = new VServicio();
                    var cServicios = new CServicios(vServicio);
                    centrar(vServicio);
                }
                break;
            case "Nueva Venta":
                CVenta.getInstance(VVenta.getInstance());
                centrar(VVenta.getInstance());
                break;
            case "Cerrar Sesión":
                Utilidades.Sesion.cerrarSesion();
                
                if (login == null) {
                    init.dispose();
                    login = new VLogin();
                    var CLogin = new CInicioSesion(login);
                }
                break;
            case "Salir":
                System.exit(0);
                break;
            case "Configuración DB":
                if (VDB == null || !VDB.isActive()) {
                    var configdb = new VBaseDeDatos();
                    new CConfiguracionDB(configdb, true);
                    configdb.setVisible(true);
                }
                break;
            case "Ingreso":
                if (vCompras == null || vCompras.isClosed()) {
                    vCompras = new VCompras();
                    CCompras ccompras = new CCompras(vCompras);
                    centrar(vCompras);
                }
                break;
            case "Roles":
                if (vRoles == null || vRoles.isClosed()) {
                    var roles = new VRoles();
                    var crol = new CRol(roles);
                    centrar(roles);
                }
                break;
            case "Salida":
                if(vSalidas == null || vSalidas.isClosed()){
                    vSalidas = new VSalidasProductos();
                    new CSalidas(vSalidas);
                    centrar(vSalidas);
                }
        }
    }

    private void centrar(javax.swing.JComponent component) {
        dpContenedor.remove(component);
        dpContenedor.add(component);
        component.setVisible(true);
        int x = (dpContenedor.getSize().width - component.getSize().width) / 2;
        int y = (dpContenedor.getSize().height - component.getSize().height) / 2;
        component.setLocation(x, y);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    private final VInit init;
    private final javax.swing.JDesktopPane dpContenedor;
    private Usuario usuario;
    private VUsuario vUsuario;
    private VServicio vServicio;
    private VCliente vCliente;
    private VConsulta VCFactura;
    private VConsulta VCCompra;
    private VBaseDeDatos VDB;
    private VLogin login;
    private VCompras vCompras;
    private VRoles vRoles;
    private VSalidasProductos vSalidas;
}
