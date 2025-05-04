import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Clase Carrito, que almacena los productos que el comprador ha agregado a su carrito de compras
public class Carrito {
    private ArrayList <Producto> productos_en_carrito;
    private ArrayList <Producto> catalogoProductos;
    private int total;
    private int idCompra;
    private int idComprador;
    private double saldoComprador;
    private TiendaAdministradoraCatalogo tienda;
    /*
    public Carrito(int idComprador, double saldoComprador, int idCompra) { 
        this.idComprador=idComprador;
        this.saldoComprador=saldoComprador;
        this.idCompra=idCompra;

        //inicializamos arreglo el carrito
        this.productos_en_carrito = new ArrayList <Producto>();
        this.catalogoProductos= tienda.getListaDeProductos();
        this.total=0;
        //almacenar el catalogo de productos, es necesario para que el carrito funcione
        tienda= new TiendaAdministradoraCatalogo();
    }
         */

    public Carrito(int idComprador, int idCompra) { 
        this.idComprador=idComprador;
        this.idCompra=idCompra;

        //inicializamos arreglo el carrito
        tienda= new TiendaAdministradoraCatalogo();
        this.productos_en_carrito = new ArrayList <Producto>();
        this.catalogoProductos= tienda.getListaDeProductos();
        this.total=0;
        //almacenar el catalogo de productos, es necesario para que el carrito funcione

    }

    //setter id comprador
    public void setIdComprador(int idComprador){
        this.idComprador=idComprador;
    }

    //setter saldo
    public void setSaldo(double saldoComprador){
        this.saldoComprador=saldoComprador;
    }

    //setter id compra
    public void setIdCompra(int idCompra){
        this.idCompra=idCompra;
    }

    //almacenar el catalogo de productos, es necesario para que el carrito funcione
    /* 
    public void almacenarCatalogo(){
        this.catalogoProductos=tienda.getListaDeProductos();
    }
    */


    //get de id de la compra
    public int getIdCompra(){
        return idCompra;
    }


    //agregar o quitar la cantidad de un producto al carrito
    public void agregarOQuitarCantidadProducto(int id,int cantidad) {
        //comprobar si ya esta en carrito, modificar la cantidad
        boolean yaEnCarrrito=false;
        boolean eliminarProducto=false;
        for(Producto p: productos_en_carrito){
            if(p.getID()==id){
                p.modificarCantidad(cantidad);
                yaEnCarrrito=true;
                if(p.getCantidad()<=0){
                    eliminarProducto=true;
                }else{
                    p.imprimirActualizacionProducto();
                }
                break;
            }
        }

        //si no esta en carrito y se debe agregar, agregar objeto producto a carrito
        if(!yaEnCarrrito && !eliminarProducto){
        for(Producto p: catalogoProductos){
            if(p.getID()==id){
                p.setCantidad(cantidad);
                productos_en_carrito.add(p);
                p.imprimirProductoAgregado();
                break;
            }
        }
       }

       //si el producto tiene cantidad 0
       if(eliminarProducto){
            eliminarProducto(id);
       }
        
    }

    //eliminar en su totalidad un tipo de producto del carrito
    public boolean eliminarProducto(int id){
        for(int i=0;i<productos_en_carrito.size();i++){
            Producto productoTemporal=productos_en_carrito.get(i);
            if(productoTemporal.getID()==id){
                productos_en_carrito.remove(i);
                productoTemporal.imprimirEliminarProducto();
                return true;
            }
        }
        return false;
    }

    //eliminar todos los productos del carrito al terminar la compra
    public void vaciarCarrito(){
        productos_en_carrito.clear();
    }

    //imprimir el carrito producto por producto, si no hay productos, imprimir mensaje de carrito vacio
    public void imprimirCarrito(){
        if(productos_en_carrito.size()==0){
            Utileria.mensaje("El carrito está vacío!", Utileria.TipoDeMensaje.INFO);
        }else{
            total=0;
            System.out.println("");
            for(Producto p: productos_en_carrito){
                p.imprimirParaCarrito();
                total+=p.getSubtotal();
            }
            System.out.println("Total: $" + total);
            //System.out.println("Saldo disponible: " + saldoComprador);
            /*
            if(saldoComprador<total){
                System.out.println("No tienes suficiente saldo para realizar la compra. Elimina productos del carrito, o sal y recarga tu saldo");
            }
                 */
        }
    }
    

    //guardar el carrito en un archivo, se guardara en el formato: id del usuario, id de la compra, total, [Producto1], [Producto2], ... [ProductoN]
    public void guardarEnArchivo() {
        //id del usuario, id de la compra, total, [Producto1], [Producto2], ... [ProductoN]
        //Por cada [Productok] almacenamos idProducto, nombre, cantidad, subtotal
        if(saldoComprador<total){
            //System.out.println("No tienes suficiente saldo para realizar la compra. Elimina productos del carrito, o sal y recarga tu saldo");
            return;
        }

        if(productos_en_carrito.size()==0){
            //System.out.println("El carrito está vacío!");
            return;
        }

        //se escribira la orden en el archivo ArchivoCompras.txt
        try (FileWriter writer = new FileWriter(Utileria.archivoCompras, true)) {
            String idUsuarioString= String.valueOf(idComprador);
            String idCompraString= String.valueOf(idCompra);
            String totalString= String.valueOf(total);
            String stringAguardar= idUsuarioString + "," + idCompraString + "," + totalString + ",";
            for(Producto p: productos_en_carrito){
                //idProducto, nombre, cantidad, subtotal
                stringAguardar+= p.stringRegistrarProductoEnOrden();
            }

            writer.write(stringAguardar + "\n");
            //saldoComprador-=total;
            System.out.println("¡Compra realizada con éxito! Gracias por su compra.");
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
    
    //metodo que se llama al pagar
    public void ordenar(int idCompra) {// regresa el saldo restante del comprador
        this.idCompra=idCompra;
        //imprimir carrito
        imprimirCarrito();
        //guardar carrito al archivo
        guardarEnArchivo();
        
        vaciarCarrito();

        //System.exit(0);
        //double saldoRestante=saldoComprador;
        //return saldoRestante;
    }
}