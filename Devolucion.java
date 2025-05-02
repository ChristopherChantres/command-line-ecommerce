import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Devolucion {//no funciona esta clase, no accede a los archivos correctamente.
    public static void procesarDevolucion() {
        Scanner scanner = new Scanner(System.in);
        

        System.out.println("¿Quieres hacer un reembolso?");
        System.out.println("1: Sí");
        System.out.println("2: No");
        System.out.print("Elige una opción: ");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1 -> {
                System.out.println("Procediendo con el reembolso");
                try {
                    devolverProductos("ArchivoCompras.txt", "ArchivoProductos.txt");
                    System.out.println("productos devueltos al catalogo");
                } catch (IOException e) {
                    System.out.println("Error al procesar la devolución: " + e.getMessage());
                }
            }
            case 2 -> System.out.println("Cancelando reembolso");
            default -> System.out.println("Respuesta no valida");
        }

        public static void devolverProductos(String ArchivoCompras, String ArchivoProductos)throws IOException {
            List<String> productos = Files.readAll  Lines(Paths.get(ArchivoCompras));

            FileWriter fw = new FileWriter(ArchivoCompras, true);
            for (String producto : productos) {
                fw.write(producto + System.lineSeparator());
            }
            fw.close();

            new FileWriter(ArchivoCompras).close();
        }
    }
}
