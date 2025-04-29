public class Usuario {
    private String nombre;
    private String contrasena;
    private final int id;

    // Constructor
    public Usuario(String nombre, String contrasena, int id) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.id = id;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public int getId() {
        return id;
    }

    public void setContrasena(String contrasena){
        this.contrasena=contrasena;
    }

    public String toString() {
        return "Nombre: " + nombre + ", ID: " + id;
    }
}