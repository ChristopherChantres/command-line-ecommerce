import java.util.Random;

public class UsuarioComprador extends Usuario {
    private double saldo;

    // Constructor
    public UsuarioComprador(int id,String nombre, String contrasena, int saldo) {
        super(nombre, contrasena, id);
    }

    public UsuarioComprador(int id,String nombre, String contrasena) {
        super(nombre, contrasena, id);
    }

    // Getters
    public double getSaldo() {
        return this.saldo;
    }

    // Setters
    public void setSaldo(double saldo){
        this.saldo = saldo;
    }

    /* 
    // Generar un ID positivo
    private static int generarIDPositivo() {
        Random random = new Random();
        return 1 + random.nextInt(999999); // ID positivo entre 1 y 999999
    }
        */
}