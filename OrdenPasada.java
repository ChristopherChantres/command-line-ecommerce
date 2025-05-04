import java.util.ArrayList;

public class OrdenPasada {
    private int idOrden;
    private int idComprador;
    private ArrayList<Producto> productosComprados = new ArrayList<Producto>(); // Almacena los productos comprados en la orden
    private double total; // Almacena el total de la orden

    public OrdenPasada(int idComprador, int idOrden, double total){
        this.idOrden = idOrden;
        this.idComprador = idComprador;
        this.total = total;
    }
    

    public int getIdOrden() {
        return idOrden;
    }

    public int getIdComprador() {
        return idComprador;
    }

    public double getTotal() {
        return total;
    }


    public ArrayList<Producto> getProductosComprados() {
        return productosComprados;
    }

    public void anadirProducto(Producto producto) {
        productosComprados.add(producto); // Agrega un producto a la lista de productos comprados
    }

    public void imprimirOrden() {
        System.out.println("ID de compra: " + idOrden);
        //System.out.println("Comprador ID: " + idComprador);
        System.out.println("Total: " + total);
        System.out.println("Productos:");
        for (Producto producto : productosComprados) {
            producto.imprimirParaCarrito(); // Imprime los detalles de cada producto el la orden
        }
    }

    public String registrarOrdenString() {
        String idUsuarioString= String.valueOf(getIdComprador());
            String idCompraString= String.valueOf(getIdOrden());
            String totalString= String.valueOf(getTotal());
            String stringAguardar= idUsuarioString + "," + idCompraString + "," + totalString + ",";
            for(Producto p: productosComprados){
            //Por cada [Productok] almacenamos idProducto, nombre, idVendedor, cantidad, subtotal
                    stringAguardar+= p.stringRegistrarProductoEnOrden();
            }
            //stringAguardar+= "\n";
            return stringAguardar; // Devuelve la cadena que representa la orden para ser guardada en el archivo
                
            
    }

    




}