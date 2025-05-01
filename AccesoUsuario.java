import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AccesoUsuario {
        protected ArrayList <UsuarioVendedor> vendedores= new ArrayList <UsuarioVendedor>();
        protected ArrayList <UsuarioComprador> compradores= new ArrayList <UsuarioComprador>();
        protected int maxIDVendedor;
        protected int maxIDComprador;


    
    public AccesoUsuario(){
        cargarVendedoresDesdeArchivo();
    }

    public void cargarVendedoresDesdeArchivo(){
        try{

            Scanner scanner=new Scanner(new File("ArchivoVendedores.txt"));
            while(scanner.hasNextLine()){
                String linea= scanner.nextLine();
                String [] partesEntrada=linea.split(",");

                if(partesEntrada.length==3){//comprueba que el arreglo es de tamano 5, tiene todos los atributos bien
                    int id= Integer.parseInt(partesEntrada[0]);
                    String usuario=partesEntrada[1];
                    String contrasena=partesEntrada[2];
                    maxIDVendedor=-max(-maxIDVendedor, -id);

                    UsuarioVendedor u=new UsuarioVendedor(id,usuario,contrasena);
                    
                    vendedores.add(u);
                }

            }

            //cerramos el archivo
            scanner.close();
        }catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado:"+ e.getMessage());
        }
    }

    public void cargarCompradoresDesdeArchivo(){
        try{

            Scanner scanner=new Scanner(new File("ArchivoCompradores.txt"));
            while(scanner.hasNextLine()){
                String linea= scanner.nextLine();
                String [] partesEntrada=linea.split(",");

                if(partesEntrada.length==3){//comprueba que el arreglo es de tamano 5, tiene todos los atributos bien
                    int id= Integer.parseInt(partesEntrada[0]);
                    String usuario=partesEntrada[1];
                    String contrasena=partesEntrada[2];
                    maxIDComprador=max(maxIDComprador, id);

                    UsuarioComprador u=new UsuarioComprador(id,usuario,contrasena);
                    
                    compradores.add(u);
                }

            }

            //cerramos el archivo
            scanner.close();
        }catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado:"+ e.getMessage());
        }
    }

    public void agregarComprador(String usuario,String contrasena){
        maxIDComprador+=1;
        UsuarioComprador u=new UsuarioComprador(maxIDComprador,usuario,contrasena);
        compradores.add(u);
    }

    public void agregarVendedor(String usuario,String contrasena){
        maxIDVendedor-=1;
        UsuarioVendedor u=new UsuarioVendedor(maxIDVendedor, usuario, contrasena);
        vendedores.add(u);
    }

    //agregar verificacion de usuarios

    public int max(int a, int b){
        if(a>b)return a;
        return b;
    }
}
