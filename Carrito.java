import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Carrito {// estaba en md, lo pase a Java
    private ArrayList <Producto> productos_en_carrito;
    private ArrayList <Producto> catalogoProductos;
    public Carrito() {
        productos_en_carrito = new ArrayList <Producto>();
        catalogoProductos= new ArrayList <Producto>();
    }

    public void almacenarCatalogo(ArrayList <Producto> catalogoProductos){
        this.catalogoProductos=catalogoProductos;
    }


    public void agregarProducto(Producto producto) {
        productos_en_carrito.add(producto);
    }
    
    public void guardarEnArchivo(String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("ID,Nombre,Descripcion,Precio,Stock\n");
            for (Producto producto : productos_en_carrito) {
                writer.write(producto.toCSV() + "\n");
            }
            System.out.println("Productos guardados correctamente en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
    

    public void pagar() {
    System.out.println("¡Compra realizada con éxito! Gracias por su compra.");
    System.exit(0);
    }
}