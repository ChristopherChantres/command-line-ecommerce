public class Usuarios {
    private final String nombre;
    private final String contraseña;
    private final int id;

    // Constructor
    public Usuarios(String nombre, String contraseña, int id) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.id = id;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", ID: " + id;
    }
}