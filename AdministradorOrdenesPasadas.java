import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class AdministradorOrdenesPasadas {
    private ArrayList<OrdenPasada> ordenesPasadas = new ArrayList<>(); // Almacena las ordenes pasadas
    private int sigIdCompra;
//-----------------------------------------------------------------------------------------------------------------------
// Constructor de la clase Administrador de Ordenes Pasadas
    AdministradorOrdenesPasadas() {
        cargarOrdenesDesdeArchivo(); // Carga las ordenes desde el archivo al iniciar la clase
    }
    
    // Carga las ordenes desde el archivo
    public void cargarOrdenesDesdeArchivo() {
        ordenesPasadas.clear(); // Limpia la lista de ordenes pasadas
        try {
            Scanner scanner = new Scanner(new File(Utileria.archivoCompras)); // Abre el archivo de ordenes pasadas
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine(); // Lee cada linea del archivo
                //System.out.println(linea); // Imprime la linea leida
                String[] partesOrden = linea.split(","); // Separa los atributos por comas

                if (partesOrden.length >= 3) { // Comprueba que el arreglo es valido en tamaño
                    //id del usuario, id de la compra, total, [Producto1], [Producto2], ... [ProductoN]
                    //Por cada [Productok] almacenamos idProducto, nombre, idVendedor, cantidad, subtotal
                    int idUsuarioComprador = Integer.parseInt(partesOrden[0]);
                    int idCompra = Integer.parseInt(partesOrden[1]);
                    double total = Double.parseDouble(partesOrden[2]);
                    //siguiente id de compra
                    if (idCompra >= sigIdCompra) { // Si el id de compra es mayor al siguiente id de compra
                        sigIdCompra = idCompra+1; // Actualiza el siguiente id de compra
                    }
                    //public OrdenPasada(int idComprador, int idOrden, double total)
                    OrdenPasada orden = new OrdenPasada(idUsuarioComprador,idCompra,total); // Crea una nueva orden pasada
                    
                    //ciclo que añade cada producto a la orden pasada
                    for (int i = 3; i+4 < partesOrden.length; i+=5) {
                        //Por cada [Productok] almacenamos idProducto, nombre, idVendedor, cantidad, subtotal
                        //if (i+3<partesOrden.length) { // Comprueba que el arreglo es valido en tamaño
                            int idProducto = Integer.parseInt(partesOrden[i]);
                            String nombre = partesOrden[i+1];
                            int idVendedor = Integer.parseInt(partesOrden[i+2]);
                            int cantidad = Integer.parseInt(partesOrden[i+3]);
                            double subtotal = Double.parseDouble(partesOrden[i+4]);

                            Producto producto = new Producto(idProducto, nombre, idVendedor, cantidad, subtotal); // Crea un nuevo producto
                            orden.anadirProducto(producto); // Agrega el producto a la orden pasada
                        //}
                    }

                    ordenesPasadas.add(orden); // Agrega la orden a la lista de ordenes pasadas
                    //System.out.println("Orden pasada cargada: " + orden.getIdOrden());
                }
            }
            scanner.close(); // Cierra el archivo
        } catch (FileNotFoundException e) {
            Utileria.mensaje("Archivo no encontrado: " + e.getMessage(), Utileria.TipoDeMensaje.ERROR); // Muestra un mensaje de error
        }
    }

    //Volver a registrar las ordenes pasadas en el archivo
    public void guardarOrdenesEnArchivo() {
        try {
            FileWriter file = new FileWriter(Utileria.archivoCompras,false); // Abre el archivo de ordenes pasadas
            PrintWriter printWriter = new PrintWriter(file); // Crea un nuevo PrintWriter para escribir en el archivo
     
            for (int i=0;i<ordenesPasadas.size();i++) { // Recorre todas las ordenes pasadas
                //System.out.println("Guardando orden pasada: "+ordenesPasadas.get(i).getIdOrden()); // Imprime el id de la orden
                //printWriter.write(orden.getIdComprador() + "," + orden.getIdOrden() + "," + orden.getTotal() + ","); // Escribe el id del comprador, id de la compra y total

                String stringAguardar= ordenesPasadas.get(i).registrarOrdenString(); // Obtiene la cadena que representa la orden para ser guardada en el archivo
                printWriter.write(stringAguardar+"\n"); // Escribe la cadena en el archivo
            }
            printWriter.close(); // Cierra el archivo
            file.close();
        } catch (IOException e) {
            Utileria.mensaje("Error al guardar las ordenes en el archivo: " + e.getMessage(), Utileria.TipoDeMensaje.ERROR); // Muestra un mensaje de error
        }
    }


//-----------------------------------------------------------------------------------------------------------------------
//COMPRADOR
    //get id compra
    public int getSigIdCompra() {
        return sigIdCompra; // Devuelve el siguiente id de compra
    }

    //metodo que da al usuario la lista de ordenes pasadas
    public boolean imprimirOrdenesPasadas(int idComprador) {
        //ArrayList<OrdenPasada> ordenesDelComprador = new ArrayList<OrdenPasada>(); // Almacena las ordenes del comprador
        boolean ordenesDelComprador = false; // Variable que indica si el comprador tiene ordenes pasadas que se imprimieron
        System.out.println("Ordenes pasadas del comprador con ID: " + idComprador); // Imprime el id del comprador
        for (OrdenPasada orden : ordenesPasadas) { // Recorre todas las ordenes pasadas
            if (orden.getIdComprador() == idComprador) { // Si la orden es del comprador
                orden.imprimirOrden(); // Imprime la orden
                ordenesDelComprador = true; // Cambia la variable a true
            }
        }
        return ordenesDelComprador; // Devuelve si se imprimieron ordenes del comprador
    }

    //devolver compra
    public double devolverCompra(int idCompra, int idComprador) {
        boolean ordenEncontrada = false; // Variable que indica si se encontró la orden
        double totalDeOrden = -1; // Almacena el total de la orden
        try {
            for (int i=0; i<ordenesPasadas.size(); i++) { // Recorre todas las ordenes pasadas
                //OrdenPasada orden = ordenesPasadas.get(i); // Obtiene la orden
                if (ordenesPasadas.get(i).getIdOrden() == idCompra && ordenesPasadas.get(i).getIdComprador() == idComprador) { // Si la orden es del comprador
                    totalDeOrden = ordenesPasadas.get(i).getTotal();
                    ordenEncontrada = true; // Cambia la variable a true
                    System.out.println("Se devolvió la compra con ID: " + idCompra + " del comprador con ID: " + idComprador);
                    ordenesPasadas.get(i).imprimirOrden(); // Imprime la orden
                    ordenesPasadas.remove(i); // Elimina la orden de la lista de ordenes pasadas
                    break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Utileria.mensaje("Error al devolver la compra: " + e.getMessage(), Utileria.TipoDeMensaje.ERROR); // Muestra un mensaje de error
        }
        if (ordenEncontrada) guardarOrdenesEnArchivo(); // Guarda las ordenes en el archivo

        return totalDeOrden; // Si no se encuentra la orden, devuelve false
    }
//-----------------------------------------------------------------------------------------------------------------------
//VENDEDOR
    //metodo que retroalimenta al vendedor de las ordenes pasadas
    public ArrayList <Producto> ordenesPasadasVendedor(int idVendedor) {
        ArrayList<Producto> productosVendidos = new ArrayList<Producto>(); // Almacena los productos del vendedor
        //System.out.println("Ordenes pasadas del vendedor con ID: " + idVendedor);
        for (OrdenPasada orden : ordenesPasadas) { // Recorre todas las ordenes pasadas
            for (Producto producto : orden.getProductosComprados()) { // Recorre todos los productos de la orden
                if (producto.getId_vendedor() == idVendedor) { // Si el producto es del vendedor
                    productosVendidos.add(producto );
                }
            }
        }
        productosVendidos = unirProductosDuplicados(productosVendidos); // Une los productos duplicados
        return productosVendidos; // Devuelve la lista de productos del vendedor
    }

    public ArrayList <Producto> unirProductosDuplicados(ArrayList <Producto> productos) {
        ArrayList <Producto> productosSinDuplicar = new ArrayList<>(); // Almacena las ordenes sin duplicados
        for(Producto producto : productos) { // Recorre todos los productos
            boolean existe = false; // Variable que indica si el producto ya existe en la lista
            for (Producto productoSinDuplicar : productosSinDuplicar) { // Recorre todos los productos sin duplicar
                if (producto.getID() == productoSinDuplicar.getID()) { // Si el producto ya existe en la lista
                    existe = true; // Cambia la variable a true
                    productoSinDuplicar.setCantidad(productoSinDuplicar.getCantidad() + producto.getCantidad()); // Suma las cantidades del producto
                    break; // Sale del ciclo
                }
            }
            if (!existe) { // Si el producto no existe en la lista
                productosSinDuplicar.add(producto); // Agrega el producto a la lista de productos sin duplicar
            }
        }
        return productosSinDuplicar; // Actualiza la lista de ordenes pasadas
    }
    
}
