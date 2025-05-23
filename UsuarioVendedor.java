
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
        tienda= new TiendaAdministradoraCatalogo();
        administradorOrdenesPasadas= new AdministradorOrdenesPasadas();
        iniciarProductos();
    }

    //obtiene los productos ofrecidos por el vendedor, se ejecuta al iniciar sesion
    public void iniciarProductos(){
        this.productosOfrecidos = tienda.getProductosOfrecidos(super.getId());
    }


    public String toString() {
        return "Vendedor ID: " + super.getId() + ",username: " + super.getUsername() +  ", Monto vendido: " + montoVendido;
    }
    //----------------------------------------------------------------------------------------------------------------------------------
    //MODIFICACION DE PRODUCTOS PROPIOS
    //agrega un nuevo producto a la lista de productos ofrecidos por el vendedor
    public boolean agregarProducto(String nombre, String descripcion, double precio){
        boolean agregado=false;
        //le asignamos el id del vendedor al producto
        if(nombre != null && descripcion != null && precio > 0){
            Producto producto= new Producto();
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setID(tienda.getProximoIdProducto());
            producto.setId_vendedor(super.getId());//le asignamos el id del vendedor al producto
            productosOfrecidos.add(producto);//agrega el producto a la lista de productos ofrecidos por el vendedor

            tienda.agregarProducto(producto);//agrega el producto a la tienda
            agregado = guardarModificacionesProductos();
        }
        return agregado;
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

    
    //modifica el producto, en cada argumento modificable que se solicite, NO IMPLEMENTAR, EXTRA
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
    public boolean eliminarProducto(int id_producto){
        boolean eliminado=false;
        for(int i=0;i<productosOfrecidos.size();i++){
            if(productosOfrecidos.get(i).getID()==id_producto){
                productosOfrecidos.remove(i);//elimina el producto de la lista de productos ofrecidos por el vendedor
                tienda.eliminarProducto(id_producto);//elimina el producto de la tienda
                eliminado = guardarModificacionesProductos();
                break;
            }
        }
        return eliminado;
    }


//----------------------------------------------------------------------------------------------------------------------------------
//MODIFICACION DEL USUARIO

    public void eliminarTodosLosProductos(){
        //eliminar el usuario de la tienda, solo elimina los productos existentes del usuario, el usuario en si se elimina desde control de accesos
        for(int i=0;i<productosOfrecidos.size();i++){
            tienda.eliminarProducto(productosOfrecidos.get(i).getID());//elimina el producto de la tienda
        }
        //eliminar usuario con acceso USUARIOS, manejar desde el main
        guardarModificacionesProductos();
    }

//----------------------------------------------------------------------------------------------------------------------------------
//IMPRESION DE PRODUCTOS, VENTAS Y ORDENES PASADAS

    //imprime los productos ofrecidos por el vendedor
    public void imprimirProductosOfrecidos(){
        if(productosOfrecidos.isEmpty()){
            Utileria.mensaje("No tienes productos a la venta", Utileria.TipoDeMensaje.INFO);
        }else{
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
            double total=0;
            for(Producto p: productosVendidos){
                p.imprimirParaCarrito();
                total+=p.getSubtotal();//suma el total de las ventas
            }
            System.out.println("Total vendido: $" + total);//imprime el total de las ventas
        }
    }



//----------------------------------------------------------------------------------------------------------------------------------
//GUARDAR CAMBIOS EN LOS PRODUCTOS, EN EL ARCHIVO DE PRODUCTOS
    public boolean guardarModificacionesProductos(){//metodo que se ejecuta al cerrar sesion, guarda los cambios de productos y elimina los productos correspondientes en el archivo de la tienda
        //guarda los cambios de productos en el archivo de la tienda
        return tienda.guardarProductosEnArchivo();//retorna si se guardaron los cambios o no
    }


    // Generar un ID negativo
    /*private static int generarIDNegativo() {
        Random random = new Random();
        return -1 * (1 + random.nextInt(999999)); // ID negativo entre -1 y -999999
    }
            
        */
}