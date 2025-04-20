import java.util.HashMap;
import java.util.Scanner;

public class Usuarios {
    private static HashMap<String, String> baseDeDatos = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void registrarUsuario() {
        System.out.print("Ingrese nombre de usuario: ");
        String usuario = scanner.nextLine();

        if (baseDeDatos.containsKey(usuario)) {
            System.out.println("El usuario ya existe. Intenta con otro nombre.");
            return;
        }

        System.out.print("Ingrese contraseña: ");
        String contrasena = scanner.nextLine();

        baseDeDatos.put(usuario, contrasena);
        System.out.println("Usuario registrado con éxito.");
    }

    private static void iniciarSesion() {
        System.out.print("Ingrese nombre de usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Ingrese contraseña: ");
        String contrasena = scanner.nextLine();

        if (baseDeDatos.containsKey(usuario) && baseDeDatos.get(usuario).equals(contrasena)) {
            System.out.println("¡Inicio de sesión exitoso! Bienvenido, " + usuario + ".");
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }
}
