import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AccesoUsuario {
    public void cargarUsuariosDesdeArchivo(){
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
}
