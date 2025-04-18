import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in); // Scanner global

    public static void main(String[] args) {
        limpiarConsola();

        // Obtener datos del usuario para el registro
        System.out.println("------------------ REGISTRO DE USUARIO ------------------\n");
        String[] datos_del_usuario_registro = getDatosDelUsuario();
        String username_registro = datos_del_usuario_registro[0];
        String password_registro = datos_del_usuario_registro[1];
        
        /*
         * Aquí puedes agregar el código para insertar el username_registro y password_registro en la base de datos.
         */
        mensaje("Usuario registrado correctamente: " + username_registro, TipoDeMensaje.EXITO);
        continuarEvento();
        
        // Obtener datos del usuario para el login
        System.out.println("------------------ INICIO DE SESION ------------------\n");
        String[] datos_del_usuario_login = getDatosDelUsuario();
        String username_login = datos_del_usuario_login[0];
        String password_login = datos_del_usuario_login[1];

        /*
         * Aquí puedes agregar el código para insertar el username_login y password_login en la base de datos.
         */
        mensaje("Inicio de sesion satisfactorio.", TipoDeMensaje.EXITO);
        continuarEvento();
    }

    // Método para registrar un nuevo usuario
    public static String[] getDatosDelUsuario() {
        String[] datos_del_usuario = new String[2];

        // Solicitar el username
        System.out.print("Username: ");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty()) {
            mensaje("El username no puede estar vacio.", TipoDeMensaje.ERROR);
            continuarEvento();
            getDatosDelUsuario(); // Reintentar registro
        }

        // Solicitar el password
        // System.out.print("\n");
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (password.isEmpty()) {
            mensaje("El password no puede estar vacio.", TipoDeMensaje.ERROR);
            limpiarConsola();
            continuarEvento();
            getDatosDelUsuario(); // Reintentar registro
        }

        // Guardar los datos del usuario en el array
        datos_del_usuario[0] = nombre;
        datos_del_usuario[1] = password;
        return datos_del_usuario;
    }

    // -------------------- FUNCIONES DE UTILERIA -------------------- //
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

    public static void continuarEvento() {
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
        limpiarConsola();
    }

    public static void mostrarMenu() {
        System.out.println("=================================================");
        System.out.println("              Bienvenido a OnlineDeal           ");
        System.out.println("=================================================");
        System.out.println("                     MENU                       ");
        System.out.println("-------------------------------------------------");
        System.out.println("|  1. Carrito                                   |");
        System.out.println("|  2. Mostrar productos                         |");
        System.out.println("|  3. Guardar en archivo                        |");
        System.out.println("|  4. Pagar                                     |");
        System.out.println("|  5. Salir                                     |");
        System.out.println("-------------------------------------------------");
        System.out.print("Seleccione una opción: ");
    }
}
