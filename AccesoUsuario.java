import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
    private int idUsuario;
    private boolean existeUsuario = false;
    protected UsuarioComprador usuarioComprador;
    protected UsuarioVendedor usuarioVendedor;

    // Constructor
    public AccesoUsuario(String username, String password, String tipoUsuario){
        this.username = username;
        this.password = password;
        this.tipoUsuario = tipoUsuario;

        /*
            Se valida el tipo de usuario, si es comprador o vendedor
            y se cargan los usuarios al ArrayList desde el archivo correspondiente para poder
            verificar si el usuario existe o no, y posteriormente iniciar sesion
        */
        if (this.tipoUsuario.equals(Utileria.usuarioComprador)) {
            cargarCompradoresDesdeArchivo();
            try {
                for (int i=0 ; i < compradores.size(); i++){
                    if (compradores.get(i).getUsername().equals(this.username) && compradores.get(i).getPassword().equals(this.password)){
                        // El usuario comprador existe
                        this.idUsuario = compradores.get(i).getId();
                        this.usuarioComprador = new UsuarioComprador(this.idUsuario, this.username, this.password);
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
                        this.idUsuario= vendedores.get(i).getId();
                        this.usuarioVendedor = new UsuarioVendedor(this.idUsuario, this.username, this.password);
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

    public void cargarVendedoresDesdeArchivo() {
        try {
            Scanner scanner=new Scanner(new File(Utileria.archivoVendedores));
            while(scanner.hasNextLine()){
                String linea= scanner.nextLine();
                String [] partesEntrada=linea.split(",");

                if(partesEntrada.length==3){//comprueba que el arreglo es de tamano 3, tiene todos los atributos necesarios
                    int id= Integer.parseInt(partesEntrada[0]);
                    String usuario=partesEntrada[1];
                    String contrasena=partesEntrada[2];
                    maxIDVendedor=-max(-maxIDVendedor, -id);

                    UsuarioVendedor u=new UsuarioVendedor(id,usuario,contrasena);
                    vendedores.add(u);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e){
            Utileria.mensaje("Archivo no encontrado:"+ e.getMessage(), Utileria.TipoDeMensaje.ERROR);
        }
    }

    public void cargarCompradoresDesdeArchivo() {
        try {
            Scanner scanner=new Scanner(new File(Utileria.archivoCompradores));
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
            scanner.close();
        } catch (FileNotFoundException e){
            Utileria.mensaje("Archivo no encontrado:"+ e.getMessage(), Utileria.TipoDeMensaje.ERROR);
        }
    }

    public boolean registrarComprador(String username, String password) {
        int ultimoIndice = compradores.size() - 1;
        int siguienteId = (compradores.isEmpty() ? 1 : compradores.get(ultimoIndice).getId() + 1);

        try (FileWriter w = new FileWriter(Utileria.archivoCompradores, true)) {
            w.write(siguienteId + "," + username + "," + password + "\n");
            return true;
        } catch (IOException e) {
            Utileria.mensaje("Error al registrar comprador el archivo: " + e.getMessage(), Utileria.TipoDeMensaje.ERROR);
            return false;
        }
    }

    public boolean registrarVendedor(String username, String password){
        int ultimoIndice = vendedores.size() - 1;
        int siguienteId = (vendedores.isEmpty() ? -1 : vendedores.get(ultimoIndice).getId() - 1);

        try (FileWriter w = new FileWriter(Utileria.archivoVendedores, true)) {
            w.write(siguienteId + "," + username + "," + password + "\n");
            return true;
        } catch (IOException e) {
            Utileria.mensaje("Error al registrar vendedor en el archivo: " + e.getMessage(), Utileria.TipoDeMensaje.ERROR);
            return false;
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------
//MODIFICACION DEL USUARIO, contraseña

    public boolean cambiarPassword(String password) {
        // Cambia la contraseña del usuario en el archivo
        boolean funciono = false;
        this.password=password;
        if (tipoUsuario.equals(Utileria.usuarioComprador)) {
            funciono=cambiarPasswordComprador(password);
        } else if (tipoUsuario.equals(Utileria.usuarioVendedor)) {
            funciono=cambiarPasswordVendedor(password);
        }
        return funciono;
    }

    private boolean cambiarPasswordComprador(String password) {
        // Cambia la contraseña del comprador en el archivo
        boolean funciono = false;
        this.password=password;
        try (FileWriter w = new FileWriter(Utileria.archivoCompradores)) {
            for (UsuarioComprador comprador : compradores) {
                if (comprador.getId()==idUsuario) {
                    funciono=comprador.setPassword(password);
                }
            
                w.write(comprador.getId() + "," + comprador.getUsername() + "," + comprador.getPassword() + "\n");

            }
        } catch (IOException e) {
            Utileria.mensaje("Error al cambiar la contraseña del comprador en el archivo: " + e.getMessage(), Utileria.TipoDeMensaje.ERROR);
            return false;
        }
        return funciono;
    }

    private boolean cambiarPasswordVendedor(String password) {
        // Cambia la contraseña del vendedor en el archivo
        boolean funciono = false;
        this.password=password;
        try (FileWriter w = new FileWriter(Utileria.archivoVendedores)) {
            for (UsuarioVendedor vendedor : vendedores) {
                if (vendedor.getId()==idUsuario) {
                    funciono=vendedor.setPassword(password);
                }
            
                w.write(vendedor.getId() + "," + vendedor.getUsername() + "," + vendedor.getPassword() + "\n");

            }
        } catch (IOException e) {
            Utileria.mensaje("Error al cambiar la contraseña del vendedor en el archivo: " + e.getMessage(), Utileria.TipoDeMensaje.ERROR);
            return false;
        }
        return funciono;
    }

//----------------------------------------------------------------------------------------------------------------------------------
//ELIMINACION DEL USUARIO, vendedor o comprador
    public boolean eliminarUsuario() {
        // Elimina el usuario del archivo
        boolean funciono = false;
        if (tipoUsuario.equals(Utileria.usuarioComprador)) {
            funciono=eliminarComprador();
        } else if (tipoUsuario.equals(Utileria.usuarioVendedor)) {
            funciono=eliminarVendedor();
        }
        return funciono;
    }

    private boolean eliminarComprador() {
        // Elimina el comprador del archivo
        boolean funciono = false;
        try (FileWriter w = new FileWriter(Utileria.archivoCompradores)) {
            for (UsuarioComprador comprador : compradores) {
                if (comprador.getId()==idUsuario) {
                    //funciono=comprador.eliminarUsuario();
                    funciono=true;
                } else {
                    w.write(comprador.getId() + "," + comprador.getUsername() + "," + comprador.getPassword() + "\n");
                }
            }
        } catch (IOException e) {
            Utileria.mensaje("Error al eliminar el comprador en el archivo: " + e.getMessage(), Utileria.TipoDeMensaje.ERROR);
            return false;
        }
        return funciono;
    }

    private boolean eliminarVendedor() {
        // Elimina el vendedor del archivo
        boolean funciono = false;
        try (FileWriter w = new FileWriter(Utileria.archivoVendedores)) {
            for (UsuarioVendedor vendedor : vendedores) {
                if (vendedor.getId()==idUsuario) {
                    //funciono=vendedor.eliminarUsuario();
                    funciono=true;
                } else {
                    w.write(vendedor.getId() + "," + vendedor.getUsername() + "," + vendedor.getPassword() + "\n");
                }
            }
        } catch (IOException e) {
            Utileria.mensaje("Error al eliminar el vendedor en el archivo: " + e.getMessage(), Utileria.TipoDeMensaje.ERROR);
            return false;
        }
        return funciono;
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

    // ----------------- Getters ----------------- //
    public UsuarioComprador getUsuarioComprador() {
        return this.usuarioComprador;
    }

    public UsuarioVendedor getUsuarioVendedor() {
        return this.usuarioVendedor;
    }

    public boolean getExisteUsuario() {
        return this.existeUsuario;
    }

    public boolean getExisteUsuario(String username, String tipoDeUsuario) {
        if (tipoDeUsuario.equals(Utileria.usuarioComprador)) {
            for (UsuarioComprador comprador : compradores) {
                if (comprador.getUsername().equals(username)) {
                    return true;
                }
            }
        } else if (tipoDeUsuario.equals(Utileria.usuarioVendedor)) {
            for (UsuarioVendedor vendedor : vendedores) {
                if (vendedor.getUsername().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }
}
