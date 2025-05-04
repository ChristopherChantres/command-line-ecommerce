import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
        try {
            Scanner scanner = new Scanner(new File(Utileria.archivoCompras)); // Abre el archivo de ordenes pasadas
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine(); // Lee cada linea del archivo
                String[] partesOrden = linea.split(","); // Separa los atributos por comas

                if (partesOrden.length >= 3) { // Comprueba que el arreglo es valido en tamaño
                    //id del usuario, id de la compra, total, [Producto1], [Producto2], ... [ProductoN]
                    //Por cada [Productok] almacenamos idProducto, nombre, idVendedor, cantidad, subtotal
                    int idUsuarioComprador = Integer.parseInt(partesOrden[0]);
                    int idCompra = Integer.parseInt(partesOrden[1]);
                    double total = Double.parseDouble(partesOrden[2]);
                    //siguiente id de compra
                    if (idCompra > sigIdCompra) { // Si el id de compra es mayor al siguiente id de compra
                        sigIdCompra = idCompra+1; // Actualiza el siguiente id de compra
                    }
                    OrdenPasada orden = new OrdenPasada(idUsuarioComprador,idCompra,total); // Crea una nueva orden pasada
                    //ciclo que añade cada producto a la orden pasada
                    for (int i = 3; i < partesOrden.length; i+=5) {
                        //Por cada [Productok] almacenamos idProducto, nombre, idVendedor, cantidad, subtotal
                        if (i+3<partesOrden.length) { // Comprueba que el arreglo es valido en tamaño
                            int idProducto = Integer.parseInt(partesOrden[i]);
                            String nombre = partesOrden[i+1];
                            int idVendedor = Integer.parseInt(partesOrden[i+2]);
                            int cantidad = Integer.parseInt(partesOrden[i+3]);
                            double subtotal = Double.parseDouble(partesOrden[i+4]);

                            Producto producto = new Producto(idProducto, nombre, idVendedor, cantidad, subtotal); // Crea un nuevo producto
                            orden.anadirProducto(producto); // Agrega el producto a la orden pasada
                        }
                    }

                    ordenesPasadas.add(orden); // Agrega la orden a la lista de ordenes pasadas
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
            FileWriter writer = new FileWriter(Utileria.archivoCompras); // Abre el archivo de ordenes pasadas
            for (OrdenPasada orden : ordenesPasadas) { // Recorre todas las ordenes pasadas
                writer.write(orden.getIdComprador() + "," + orden.getIdOrden() + "," + orden.getTotal() + ","); // Escribe el id del comprador, id de la compra y total
                for (Producto producto : orden.getProductosComprados()) { // Recorre todos los productos de la orden
                    writer.write(producto.stringRegistrarProductoEnOrden()); // Escribe el id del producto, nombre, id del vendedor, cantidad y subtotal
                }
                writer.write("\n"); // Salto de linea
            }
            writer.close(); // Cierra el archivo
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
    public ArrayList<OrdenPasada> getOrdenesPasadas(int idComprador) {
        ArrayList<OrdenPasada> ordenesDelComprador = new ArrayList<OrdenPasada>(); // Almacena las ordenes del comprador
        for (OrdenPasada orden : ordenesPasadas) { // Recorre todas las ordenes pasadas
            if (orden.getIdComprador() == idComprador) { // Si la orden es del comprador
                ordenesDelComprador.add(orden); // Agrega la orden a la lista de ordenes del comprador
            }
        }
        return ordenesDelComprador; // Devuelve la lista de ordenes del comprador
    }

    //devolver compra
    public boolean devolverCompra(int idCompra, int idComprador) {
        boolean seDevolvio = false; // Variable que indica si se devolvió la compra
        for (OrdenPasada orden : ordenesPasadas) { // Recorre todas las ordenes pasadas
            if (orden.getIdOrden() == idCompra && orden.getIdComprador() == idComprador) { // Si la orden es del comprador
                ordenesPasadas.remove(orden); // Elimina la orden de la lista de ordenes pasadas
                guardarOrdenesEnArchivo();// Guarda las ordenes en el archivo
                seDevolvio = true; // Cambia la variable a true
                return seDevolvio; // Devuelve true, se devolvio la compra

            }
        }


        return seDevolvio; // Si no se encuentra la orden, devuelve false
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
