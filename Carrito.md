import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Carrito {
    private ArrayList<Producto> productos;
    
    public Carrito() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void guardarEnArchivo(String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("ID,Nombre,Descripcion,Precio,Stock\n");
            for (Producto producto : productos) {
                writer.write(producto.toCSV() + "\n");
            }
            System.out.println("Productos guardados correctamente en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}
