public class Test {
    
    public static void main(String [] args){
        Tienda tienda=new Tienda();
        tienda.cargarProductosDesdeArchivo();
        tienda.impirmirCatalogoBasico();

        tienda.impirmirCatalogoConDetalles();
    }
}
