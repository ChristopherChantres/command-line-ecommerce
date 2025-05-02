/*Busca un producto por su ID dentro del archivo de compras (compras.csv).
 * Si lo encuentra y no ha sido devuelto todavía, marca esa línea como DEVUELTO
 * y aumenta en 1 la cantidad de stock del producto correspondiente en el inventario.
 * El buffer abre el archivo original para lectura y el temporal para escritura
 * y reescribe el archivo de compras con los cambios.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.ioimport java.io.File;
import java.util.Arraylist;


public class Devolucion(){
    public static void procesarDevolucion() {
        File nombreArchivo = new File(nombreArchivo);
        File archivoTemp = new File("compras_temp.cvs");
        
        boolean productoEncontrado = false;

        try(BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo)); BufferedWriter = new BufferedWriter(new FileWriter(archivoTemp))) {

            String linea;
            while((linea = reader.readLine()) != null){
                String[] partes = linea.split(",");

                if (partes[0].equals("ID")) {
                    Writer.writer(linea + "\n");
                    continue;
                }

                int id = Integer.parseInt(partes[0]);
                String estado = partes.length >= 6 ? partes[5] : "COMPRADO";
                
                if (id == idProducto && !estado.equalsIgnoreCase("DEVUELTO")) {
                    productoEncontrado = true;

                    for (Producto producto : inventario) {
                        if (producto.getId() == idProducto) {
                            producto.setStock(producto.getStock() + 1);
                            break;
                        }
                    }

                    writer.write(partes[0] + "," + partes[1] + "," + partes[2] + "," +
                                 partes[3] + "," + partes[4] + ",DEVUELTO\n");
                    System.out.println("Devolución procesada para producto ID: " + idProducto);
                } else {
                    // Reescribir línea sin cambios
                    writer.write(linea + "\n");
            }
        }
    } catch (IOException e) {
            System.out.println("Error al procesar la devolución: " + e.getMessage());
            return;
        }
        if (!nombreArchivo.delete() || !archivoTemp.renameTo(nombreArchivo)) {
            System.out.println("Error al reemplazar el archivo de compras.");
        }

        if (!productoEncontrado) {
            System.out.println("Producto no encontrado o ya devuelto.");
        }
    }
}
