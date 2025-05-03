import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdministradorOrdenesPasadas {
    private ArrayList<OrdenPasada> ordenesPasadas = new ArrayList<>(); // Almacena las ordenes pasadas

    AdministradorOrdenesPasadas() {
        cargarOrdenesDesdeArchivo(); // Carga las ordenes desde el archivo al iniciar la clase
    }
    // Carga las ordenes desde el archivo

    public void cargarOrdenesDesdeArchivo() {
        try {
            Scanner scanner = new Scanner(new File(Utileria.archivoCompras)); // Abre el archivo de ordenes pasadas
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine(); // Lee cada linea del archivo
                String[] partesOrden = linea.split(","); // Separa los atributos por comas

                if (partesOrden.length >= 3) { // Comprueba que el arreglo es valido en tamaño
                    //id del usuario, id de la compra, total, [Producto1], [Producto2], ... [ProductoN]
                    //Por cada [Productok] almacenamos idProducto, nombre, cantidad, subtotal
                    int idUsuarioComprador = Integer.parseInt(partesOrden[0]);
                    int idCompra = Integer.parseInt(partesOrden[1]);
                    double total = Double.parseDouble(partesOrden[2]);

                    OrdenPasada orden = new OrdenPasada(idUsuarioComprador,idCompra,total); // Crea una nueva orden pasada
                    //ciclo que añade cada producto a la orden pasada
                    for (int i = 3; i < partesOrden.length; i+=4) {
                        //Por cada [Productok] almacenamos idProducto, nombre, cantidad, subtotal
                        if (i+3<partesOrden.length) { // Comprueba que el arreglo es valido en tamaño
                            int idProducto = Integer.parseInt(partesOrden[i]);
                            String nombre = partesOrden[i+1];
                            int cantidad = Integer.parseInt(partesOrden[i+2]);
                            double subtotal = Double.parseDouble(partesOrden[i+3]);

                            Producto producto = new Producto(idProducto, nombre, cantidad, subtotal); // Crea un nuevo producto
                            orden.getProductosComprados().add(producto); // Agrega el producto a la orden pasada
                        }
                    }

                    ordenesPasadas.add(orden); // Agrega la orden a la lista de ordenes pasadas
                }
            }
            scanner.close(); // Cierra el archivo
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        }
    }

    //metodo que retroalimenta al vendedor de las ordenes pasadas
    
}
