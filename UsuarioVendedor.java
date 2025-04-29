import java.util.Random;

public class UsuarioVendedor extends Usuario {
    // Constructor
    public UsuarioVendedor(int id,String nombre, String contrasena){
        super(nombre, contrasena, generarIDNegativo());
    }

    // Generar un ID negativo
    private static int generarIDNegativo() {
        Random random = new Random();
        return -1 * (1 + random.nextInt(999999)); // ID negativo entre -1 y -999999
    }
}