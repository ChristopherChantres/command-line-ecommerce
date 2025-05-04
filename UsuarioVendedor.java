
import java.util.ArrayList;

public class UsuarioVendedor extends Usuario {
    // Constructor
    protected double montoVendido;//almacena el monto total vendido por el vendedor
    protected ArrayList <Producto> productosOfrecidos;//almacena los productos que el vendedor tiene a la venta, el los puede modificar o eliminar

    protected ArrayList <Producto> productosVendidos;//almacena los productos de las ordenes pasadas, integrar con CompradorOrdenesPasadas
    protected TiendaAdministradoraCatalogo tienda;//accede a la tienda para obtener los productos que tiene a la venta, modificar los productos de la tienda, etc
    protected AdministradorOrdenesPasadas administradorOrdenesPasadas;//accede a las ordenes pasadas del vendedor, para que pueda ver sus ventas pasadas

    //----------------------------------------------------------------------------------------------------------------------------------
    //INICIALIZACION 
    public UsuarioVendedor(int id,String username, String contrasena){
        super(username, contrasena, id);
        iniciarProductos();
    }

    //obtiene los productos ofrecidos por el vendedor, se ejecuta al iniciar sesion
    public void iniciarProductos(){
        this.productosOfrecidos = tienda.getProductosOfrecidos(super.getId());
    }


    //----------------------------------------------------------------------------------------------------------------------------------
    //MODIFICACION DE PRODUCTOS PROPIOS
    //agrega un nuevo producto a la lista de productos ofrecidos por el vendedor
    public void agregarProducto(Producto producto){
        //le asignamos el id del vendedor al producto
        if(producto != null && !productosOfrecidos.contains(producto)){
            producto.setID(tienda.getProximoIdProducto());
            producto.setId_vendedor(super.getId());//le asignamos el id del vendedor al producto
            productosOfrecidos.add(producto);//agrega el producto a la lista de productos ofrecidos por el vendedor

            tienda.agregarProducto(producto);//agrega el producto a la tienda
        }
    }
    /* 
    //agrega el stock de un producto en particular
    public void agregarStockProducto(int id_producto, int stock){
        //busca el producto en la lista de productos del vendedor
        for(Producto p: productosOfrecidos){
            if(p.getID()==id_producto){
                //si el producto existe, se le agrega el stock
                p.setStock(p.getStock()+stock);

                tienda.agregarStockProducto(id_producto, stock);//agrega el stock a la tienda
                break;
            }
        }
    }
        */

    //modifica el producto, en cada argumento modificable que se solicite

    public void modificarProducto(int id_producto, String nombre, String descripcion, double precio){
        for(Producto p: productosOfrecidos){
            if(p.getID()==id_producto){
                p.setNombre(nombre);
                p.setDescripcion(descripcion);
                p.setPrecio(precio);
                //tienda.modificarProducto(id_producto, nombre, descripcion, precio);//modifica el producto en la tienda
                break;
            }
        }
    }

    //metodo que elimina un producto de la lista de productos ofrecidos por el vendedor
    public void eliminarProducto(int id_producto){
        for(Producto p: productosOfrecidos){
            if(p.getID()==id_producto){
                productosOfrecidos.remove(p);//elimina el producto de la lista de productos ofrecidos por el vendedor

                tienda.eliminarProducto(id_producto);//elimina el producto de la tienda
                break;
            }
        }
    }


//----------------------------------------------------------------------------------------------------------------------------------
//MODIFICACION DEL USUARIO

    public void eliminarUsuario(){
        //eliminar el usuario de la tienda, solo elimina los productos existentes del usuario, el usuario en si se elimina desde control de accesos
        for(Producto p: productosOfrecidos){
            eliminarProducto(p.getID());
        }
        //eliminar usuario con acceso USUARIOS
        salir();
    }

//----------------------------------------------------------------------------------------------------------------------------------
//IMPRESION DE PRODUCTOS, VENTAS Y ORDENES PASADAS

    //imprime los productos ofrecidos por el vendedor
    public void imprimirProductosOfrecidos(){
        if(productosOfrecidos.isEmpty()){
            Utileria.mensaje("No tienes productos a la venta", Utileria.TipoDeMensaje.INFO);
        }else{
            Utileria.mensaje("Se imprimen los productos que ofreces:", Utileria.TipoDeMensaje.INFO);
            for(Producto p: productosOfrecidos){
                p.imprimirBasico();
            }
        }
    }

    public void imprimirVentas(){
        productosVendidos= administradorOrdenesPasadas.ordenesPasadasVendedor(super.getId());//obtiene los productos vendidos por el vendedor
        if(productosVendidos.isEmpty()){
            Utileria.mensaje("No tienes ventas", Utileria.TipoDeMensaje.INFO);
        }else{
            Utileria.mensaje("Se imprimen los productos vendidos:", Utileria.TipoDeMensaje.INFO);
            double total=0;
            for(Producto p: productosVendidos){
                p.imprimirParaCarrito();
                total+=p.getSubtotal();//suma el total de las ventas
            }
            System.out.println("Total vendido: " + total);//imprime el total de las ventas
        }
    }



//----------------------------------------------------------------------------------------------------------------------------------
//GUARDAR CAMBIOS EN LOS PRODUCTOS, EN EL ARCHIVO DE PRODUCTOS
    public void salir(){//metodo que se ejecuta al cerrar sesion, guarda los cambios de productos y elimina los productos correspondientes en el archivo de la tienda
        tienda.guardarProductosEnArchivo();//guarda los cambios de productos en el archivo de la tienda
    }


    // Generar un ID negativo
    /*private static int generarIDNegativo() {
        Random random = new Random();
        return -1 * (1 + random.nextInt(999999)); // ID negativo entre -1 y -999999
    }
            
        */
}