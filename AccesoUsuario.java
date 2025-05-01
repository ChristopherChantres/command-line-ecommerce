import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AccesoUsuario {
    protected ArrayList <UsuarioVendedor> vendedores= new ArrayList <UsuarioVendedor>();
    protected ArrayList <UsuarioComprador> compradores= new ArrayList <UsuarioComprador>();
    protected int maxIDVendedor;
    protected int maxIDComprador;
    private String username;
    private String password;
    private String tipoUsuario;
    private boolean existeUsuario = false;
    protected UsuarioComprador usuarioComprador;
    protected UsuarioVendedor usuarioVendedor;

    // Constructor
    public AccesoUsuario(String username, String password, String tipoUsuario){
        this.username = username;
        this.password = password;
        this.tipoUsuario = tipoUsuario;

        /*
            Se valida el tiposde suario, si es comprador o vendedor
            y se cargan los usuarios al ArrayList desde el archivo correspondiente para poder
            verificar si el usuario existe o no, y posteriormente iniciar sesion
        */
        if (this.tipoUsuario.equals(Utileria.usuarioComprador)) {
            cargarCompradoresDesdeArchivo();
            try {
                for (int i=0 ; i < compradores.size(); i++){
                    if (compradores.get(i).getUsername().equals(this.username) && compradores.get(i).getPassword().equals(this.password)){
                        // El usuario comprador existe
                        setUsuarioComprador(usuarioComprador);
                        setExisteUsuario(true);
                    }
                }
            } catch (Exception e) {
                Utileria.mensaje("Error al verificar el usuario en el ArrayList: " + e.getMessage(), Utileria.TipoDeMensaje.ERROR);
            }
        } else if (this.tipoUsuario.equals(Utileria.usuarioVendedor)){
            cargarVendedoresDesdeArchivo();
            try {
                for (int i=0 ; i < vendedores.size(); i++){
                    if (vendedores.get(i).getUsername().equals(this.username) && vendedores.get(i).getPassword().equals(this.password)){
                        // El usuario vendedor existe
                        setUsuarioVendedor(usuarioVendedor);
                        setExisteUsuario(true);
                    }
                }
            } catch (Exception e) {
                Utileria.mensaje("Error al verificar el usuario en el ArrayList: " + e.getMessage(), Utileria.TipoDeMensaje.ERROR);
            }
        } else {
            return;
        }
    }

    public void cargarVendedoresDesdeArchivo(){
        try {
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
        } catch (FileNotFoundException e){
            Utileria.mensaje("Archivo no encontrado:"+ e.getMessage(), Utileria.TipoDeMensaje.ERROR);
        }
    }

    public void cargarCompradoresDesdeArchivo(){
        try{

            Scanner scanner=new Scanner(new File("ArchivoCompradores.txt"));
            while (scanner.hasNextLine()) {
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
        } catch (FileNotFoundException e){
            Utileria.mensaje("Archivo no encontrado:"+ e.getMessage(), Utileria.TipoDeMensaje.ERROR);
        }
    }

    public void agregarComprador(String usuario,String contrasena){
        maxIDComprador += 1;
        UsuarioComprador u = new UsuarioComprador(maxIDComprador,usuario,contrasena);
        compradores.add(u);
    }

    public void agregarVendedor(String usuario,String contrasena){
        maxIDVendedor -= 1;
        UsuarioVendedor u = new UsuarioVendedor(maxIDVendedor, usuario, contrasena);
        vendedores.add(u);
    }

    //agregar verificacion de usuarios

    public int max(int a, int b){
        if (a > b) return a;
        return b;
    }

    // ----------------- Setters ----------------- //
    public void setExisteUsuario(boolean existeUsuario) {
        this.existeUsuario = existeUsuario;
    }

    private void setUsuarioComprador(UsuarioComprador usuarioComprador) {
        this.usuarioComprador = usuarioComprador;
    }

    private void setUsuarioVendedor(UsuarioVendedor usuarioVendedor) {
        this.usuarioVendedor = usuarioVendedor;
    }

    // ----------------- Getters ----------------- //
    public UsuarioComprador getUsuarioComprador() {
        return usuarioComprador;
    }

    public UsuarioVendedor getUsuarioVendedor() {
        return usuarioVendedor;
    }

    public boolean getExisteUsuario() {
        return this.existeUsuario;
    }
}
