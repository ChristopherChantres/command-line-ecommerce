public class Test {
    
    public static void main(String [] args){
        //TiendaAdministradoraCatalogo tienda=new TiendaAdministradoraCatalogo();
        //tienda.cargarProductosDesdeArchivo();
        //tienda.impirmirCatalogoBasico();
        //tienda.impirmirCatalogoConDetalles();
        UsuarioComprador usuario=new UsuarioComprador(8,"pepe","89");
        //AdministradorOrdenesPasadas administradorOrdenesPasadas= new AdministradorOrdenesPasadas();
        //usuario.mostrarProductosDeLaTienda();

        //administradorOrdenesPasadas.cargarOrdenesDesdeArchivo();
        //administradorOrdenesPasadas.imprimirOrdenesPasadas(8);
        usuario.imprimirOrdenesPasadas();
        if(usuario.devolverOrdenPasada(4)!=-1){
            System.out.println("Orden pasada devuelta");
        }else{
            System.out.println("No se pudo devolver la orden pasada");
        }


        // if(usuario.imprimirOrdenesPasadas()){
        //     System.out.println("Ordenes pasadas impresas");
        // }else{
        //     System.out.println("No hay ordenes pasadas");
        // }
        //UsuarioVendedor vendedor=new UsuarioVendedor(-2,"chris","Contrasena");
        //vendedor.imprimirVentas();
        //AccesoUsuario acceso=new AccesoUsuario("SebasGansito","12345678",Utileria.usuarioComprador);
        //acceso.


        //tienda.impirmirCatalogoConDetalles();
    }
}
