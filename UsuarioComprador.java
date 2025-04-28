import java.util.Random;

public class UsuariosCompradores extends Usuarios {
    // Constructor
    public UsuariosCompradores(String nombre, String contraseña) {
        super(nombre, contraseña, generarIDPositivo());
    }

    // Generar un ID positivo
    private static int generarIDPositivo() {
        Random random = new Random();
        return 1 + random.nextInt(999999); // ID positivo entre 1 y 999999
    }
}