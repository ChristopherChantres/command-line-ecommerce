import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

    }

    // Método para registrar un nuevo usuario
    public static String[] registrarUsuario() {
        Scanner scanner = new Scanner(System.in);
        String[] datos_del_usuario = new String[2];
        System.out.println("Ingrese su username:");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre de usuario no puede estar vacio.");
            limpiarConsola();
            registrarUsuario(); // Reintentar registro
        }

        System.out.println("Ingrese su password:");
        String password = scanner.nextLine();
        if (password.isEmpty()) {
            System.out.println("Error: El nombre de usuario y la contraseña no pueden estar vacíos.");
            limpiarConsola();
            registrarUsuario(); // Reintentar registro
        }
        scanner.close();

        // Guardar los datos del usuario en el array
        datos_del_usuario[0] = nombre;
        datos_del_usuario[1] = password;
        return datos_del_usuario;
    }

    public static void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Error al limpiar la consola: " + e.getMessage());
        }
    }

    public enum TipoDeMensaje {
        ERROR,
        INFO,
        EXITO
    }

    public static void mensaje(String mensaje, TipoDeMensaje tipo) {
        switch (tipo) {
            case ERROR:
                System.out.println("[ERROR]: " + mensaje);
                break;
            case INFO:
                System.out.println("[INFO]: " + mensaje);
                break;
            case EXITO:
                System.out.println("[EXITO]: " + mensaje);
                break;
            default:
                System.out.println(mensaje);
        }
    }

    public static void mostrarMenu() {
        System.out.println("========= Bienvenido a OnlineDeal ========");
        System.out.println("================ MENU =========="); 
        System.out.println("1. Agregar producto");
        System.out.println("2. Mostrar productos");
        System.out.println("3. Guardar en archivo");
        System.out.println("4. Pagar");
        System.out.println("5. Salir");
    }
}
