import java.util.Random;

public class UsuariosVendedores extends Usuarios {
    // Constructor
    public UsuariosVendedores(String nombre, String contraseña) {
        super(nombre, contraseña, generarIDNegativo());
    }

    // Generar un ID negativo
    private static int generarIDNegativo() {
        Random random = new Random();
        return -1 * (1 + random.nextInt(999999)); // ID negativo entre -1 y -999999
    }
}