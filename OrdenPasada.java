import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrdenPasada {
    private int idOrden;
    private int idComprador;
    private ArrayList<Producto> productosComprados = new ArrayList<Producto>(); // Almacena los productos comprados en la orden
    private double total; // Almacena el total de la orden

    public OrdenPasada(int idOrden, int idComprador, double total){
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

}