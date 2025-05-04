public class Test {
    
    public static void main(String [] args){
        TiendaAdministradoraCatalogo tienda=new TiendaAdministradoraCatalogo();
        //tienda.cargarProductosDesdeArchivo();
        //tienda.impirmirCatalogoBasico();
        //tienda.impirmirCatalogoConDetalles();
        UsuarioComprador usuario=new UsuarioComprador(1,"usuario","1234");
        usuario.mostrarProductosDeLaTienda();
        UsuarioVendedor vendedor=new UsuarioVendedor(-2,"chris","Contrasena");
        vendedor.imprimirVentas();
        AccesoUsuario acceso=new AccesoUsuario("SebasGansito","12345678",Utileria.usuarioComprador);
        acceso.


        //tienda.impirmirCatalogoConDetalles();
    }
}
