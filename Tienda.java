import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tienda {
    protected ArrayList<Producto> productos= new ArrayList<>();

    
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
                }

            }

            //cerramos el archivo
            scanner.close();
        }catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado:"+ e.getMessage());
        }
    }

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
}
