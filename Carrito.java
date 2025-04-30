import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Carrito {//Clase Carrito, que almacena los productos que el comprador ha agregado a su carrito de compras
    private ArrayList <Producto> productos_en_carrito;
    private ArrayList <Producto> catalogoProductos;
    private int total;
    private int idCompra;
    private int idComprador;
    private double saldoComprador;
    public Carrito(int idComprador, double saldoComprador, int idCompra) { 
        this.idComprador=idComprador;
        this.saldoComprador=saldoComprador;
        this.idCompra=idCompra;

        //inicializamos arreglo el carrito
        productos_en_carrito = new ArrayList <Producto>();
        catalogoProductos= new ArrayList <Producto>();
        this.total=0;
    }

    public void setIdComprador(int idComprador){
        this.idComprador=idComprador;
    }

    public void setSaldo(double saldoComprador){
        this.saldoComprador=saldoComprador;
    }

    public void almacenarCatalogo(ArrayList <Producto> catalogoProductos){
        this.catalogoProductos=catalogoProductos;
    }


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
            }
        }

        //si no esta en carrito y se debe agregar, agregar objeto producto a carrito
        if(!yaEnCarrrito && !eliminarProducto){
        for(Producto p: catalogoProductos){
            if(p.getID()==id&&cantidad>0){
                p.setCantidad(cantidad);
                productos_en_carrito.add(p);
                p.imprimirProductoAgregado();
            }
        }
       }

       //si el producto tiene cantidad 0
       if(eliminarProducto){
            eliminarProducto(id);
       }
        
    }

    public void eliminarProducto(int id){
        for(int i=0;i<productos_en_carrito.size();i++){
            Producto productoTemporal=productos_en_carrito.get(i);
            if(productoTemporal.getID()==id){
                productoTemporal.imprimirEliminarProducto();
                productos_en_carrito.remove(i);
            }
        }
    }

    public void vaciarCarrito(){
        productos_en_carrito.clear();
    }

    public void imprimirCarrito(){
        if(productos_en_carrito.size()==0){
            System.out.println("El carrito está vacío!");
        }else{
            total=0;
            System.out.println("");
            System.out.println("Carrito:");
            for(Producto p: productos_en_carrito){
                p.imprimirParaCarrito();
                total+=p.getSubtotal();
            }
            System.out.println("Total: " + total);
            System.out.println("Saldo disponible: " + saldoComprador);
            if(saldoComprador<total){
                System.out.println("No tienes suficiente saldo para realizar la compra. Elimina productos del carrito, o sal y recarga tu saldo");
            }
        }
    }
    
    public void guardarEnArchivo(int idCompra) {
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
        try (FileWriter writer = new FileWriter("ArchivoCompras.txt", true)) {
            String idUsuarioString= String.valueOf(idComprador);
            String idCompraString= String.valueOf(idCompra);
            String totalString= String.valueOf(total);
            String stringAguardar= idUsuarioString + "," + idCompraString + "," + totalString + ",";
            for(Producto p: productos_en_carrito){
                String idProductoString= String.valueOf(p.getID());
                String nombreString= p.getNombre();
                String cantidadString= String.valueOf(p.getCantidad());
                String subtotalString= String.valueOf(p.getSubtotal());
                stringAguardar+= idProductoString + "," + nombreString + "," + cantidadString + "," + subtotalString + ",";
            }

            writer.write(stringAguardar + "\n");
            saldoComprador-=total;
            System.out.println("¡Compra realizada con éxito! Gracias por su compra.");
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
    

    public double pagar() {//regresa el saldo restante del comprador

    //imprimir carrito
    imprimirCarrito();
    //guardar carrito al archivo
    guardarEnArchivo(idCompra);
    
    //System.exit(0);
    double saldoRestante=saldoComprador;
    return saldoRestante;
    }
}