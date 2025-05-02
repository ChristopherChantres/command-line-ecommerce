public class Usuario {
    private String username;
    private String password;
    private final int id;

    // Constructor
    public Usuario(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    // Setters
    public boolean setPassword(String password){
        // Implementar la logica para guaradar el password en el archivo
        this.password=password;
        return true;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String toString() {
        return "username: " + username + ", ID: " + id;
    }
}