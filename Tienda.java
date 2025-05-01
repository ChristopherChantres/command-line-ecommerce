import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tienda {
    protected ArrayList<Producto> productos= new ArrayList<>();
    protected int idUsuario;
    protected int proximoIdProducto;

    //----------------------------------------------------------------------------------------------------------------------------------
    //INICIALIZACION DE LA TIENDA
    // Constructor de la clase Tienda, inicializa el ArrayList de productos y carga los productos desde el archivo de productos
    public Tienda(){
        productos=new ArrayList<>();
        cargarProductosDesdeArchivo();
    }

    //metodo que extrae en una arraylist todos los productos de la tienda desde un archivo
    public void cargarProductosDesdeArchivo(){
        try{

            Scanner scanner=new Scanner(new File("ArchivoProductos.txt"));
            while(scanner.hasNextLine()){
                String linea= scanner.nextLine();
                String [] partesProducto=linea.split(",");

                if(partesProducto.length==6){//comprueba que el arreglo es de tamano 5, tiene todos los atributos bien
                    int id= Integer.parseInt(partesProducto[0]);
                    String nombre=partesProducto[1];
                    String descripcion=partesProducto[2];
                    double precio=Double.parseDouble(partesProducto[3]);
                    int stock= Integer.parseInt(partesProducto[4]);
                    int id_vendedor= Integer.parseInt(partesProducto[5]);
                    
                    Producto p= new Producto(id, nombre, descripcion, precio, stock,id_vendedor);
                    productos.add(p);

                    if(id>=proximoIdProducto){
                        proximoIdProducto=id+1;//si el id es mayor al proximo id, lo actualizamos
                    }
                }

            }

            //cerramos el archivo
            scanner.close();
        }catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado:"+ e.getMessage());
        }
    }
    //----------------------------------------------------------------------------------------------------------------------------------
    //METODOS RELACIONADOS AL COMPRADOR

    //metodo que devuelve la lista de productos de la tienda, se utiliza para que el comprador pueda ver los productos disponibles, y los pueda agregar al carrito
    public ArrayList <Producto> getListaDeProductos(){
        cargarProductosDesdeArchivo();
        return productos;
    }

    //por defecto se muestra el catalogo basico( solo id, nombre y precio de cada producto), si se requieren detalles se puede imprimir el catalogo mas avanzado
    public void impirmirCatalogoBasico(){

        System.out.println("---------------------------------------------------------------");//separador
        System.out.println("El catalogo de la tienda es el siguiente: ");
        for(Producto p: productos){
            p.imprimirBasico();
        }
    }


    //imprime todo de cada producto, incluyendo nombre, descripcion, stock...
    public void impirmirCatalogoConDetalles(){

        System.out.println("---------------------------------------------------------------");//separador
        System.out.println("El catalogo detallado de la tienda es el siguiente: ");
        for(Producto p: productos){
            p.imprimirDetalles();
        }
    }




    
    //devuelve el producto con el ID requerido, aunque cuidado, devuelve un Producto vacio si no encuentra ninguno con el id requerido
    public Producto getProductoConID(int id){
        Producto productoTemporal=new Producto();
        boolean bandera=false;
        for(Producto p: productos){
            if(p.getID()==id){
                productoTemporal=p;
                bandera=true;
            }
        }
        if(!bandera){
            System.out.println("El producto no fue encontrado, el id no existe");
        }
        return productoTemporal;
    }

    //----------------------------------------------------------------------------------------------------------------------------------
    //METODOS RELACIONADOS AL VENDEDOR


    //metodo que devuelve la lista de productos de un vendedor de la tienda
    public ArrayList <Producto> getProductosOfrecidos(int id_vendedor){
        ArrayList <Producto> productosVendedor= new ArrayList<>();
        for(Producto p: productos){
            if(p.getId_vendedor()==id_vendedor){
                productosVendedor.add(p);
            }
        }
        return productosVendedor;
    }

    public int getProximoIdProducto(){
        return proximoIdProducto;
    }

    //metodo para agregar un producto a la tienda, se ejecuta al agregar un producto por parte del vendedor
    public void agregarProducto(Producto producto){
        if(producto != null && !productos.contains(producto)){
            productos.add(producto);
        }
    }

    //metodo para eliminar un producto de la tienda, se ejecuta al eliminar un producto del vendedor


    //metodo para reescribir el archivo de productos de la tienda, se ejecuta al cerrar sesion, o guardar cambios
}
