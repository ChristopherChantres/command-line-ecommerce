import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in); // Scanner global

    public static void main(String[] args) {
        String tipoUsuario = obtenerTipoUsuario();
        limpiarConsola();
        
        String username = "";
        boolean tieneCuenta = tieneCuenta();
        if (tieneCuenta) {
            username = iniciarSesion(tipoUsuario);
        } else {
            registrarUsuario(tipoUsuario);
            username = iniciarSesion(tipoUsuario);
        }

        // Bifurcación de la lógica según el tipo de usuario
        if (tipoUsuario.equals("comprador")) {
            // LÓGICA PARA COMPRADORES
            gestionarFlujoComprador(username);
        } else if (tipoUsuario.equals("vendedor")) {
            // LÓGICA PARA VENDEDORES
            gestionarFlujoVendedor(username);
        }
    }

    // -------------------- METODOS DE UTILERIA -------------------- //
    public static void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            mensaje("No se pudo limpiar la consola: " + e.getMessage(), TipoDeMensaje.INFO);
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

    // -------------------- METODOS GENERALES -------------------- //

    // Método para registrar un nuevo usuario
    public static String[] getDatosDelUsuario() {
        String[] datos_del_usuario = new String[2];

        // Solicitar el username
        System.out.print("Username: ");
        String username = scanner.nextLine();
        if (username.isEmpty()) {
            mensaje("El username no puede estar vacio.", TipoDeMensaje.ERROR);
            continuarEvento();
            getDatosDelUsuario(); // Reintentar registro
        }

        // Solicitar el password
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (password.isEmpty()) {
            mensaje("El password no puede estar vacio.", TipoDeMensaje.ERROR);
            limpiarConsola();
            continuarEvento();
            getDatosDelUsuario(); // Reintentar registro
        }

        // Guardar los datos del usuario en el array
        datos_del_usuario[0] = username;
        datos_del_usuario[1] = password;
        return datos_del_usuario;
    }

    public static String obtenerTipoUsuario() {
        limpiarConsola();
        System.out.println("=================================================");
        System.out.println("              Bienvenido a OnlineDeal           ");
        System.out.println("=================================================");
        System.out.println("                 Seleccione su rol               ");
        System.out.println("-------------------------------------------------");
        System.out.println("1. Comprador (Cliente)");
        System.out.println("2. Vendedor");
        System.out.print("Seleccione una opción (1-2): ");
        
        int opcion = 0;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            mensaje("Por favor, ingrese un número válido.", TipoDeMensaje.ERROR);
            continuarEvento();
            obtenerTipoUsuario();
        }
        
        switch (opcion) {
            case 1:
                return "comprador";
            case 2:
                return "vendedor";
            default:
                mensaje("Opción no válida. Intente nuevamente.", TipoDeMensaje.ERROR);
                continuarEvento();
                return obtenerTipoUsuario();
        }
    }

    public static boolean tieneCuenta() {
        limpiarConsola();
        // Validar si el usuario tiene una cuenta
        System.out.println("=================================================");
        System.out.println("              Bienvenido a OnlineDeal           ");
        System.out.println("=================================================");
        System.out.println("|  Ya tienes una cuenta creada?");
        System.out.println("|  1. Si");
        System.out.println("|  2. No");
        System.out.print("Seleccione una opción (1-2): ");
        int opcion = 0;

        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            mensaje("Por favor, ingrese un número válido.", TipoDeMensaje.ERROR);
            continuarEvento();
            tieneCuenta();
        }

        if (opcion == 1) {
            return true; // El usuario tiene cuenta
        } else if (opcion == 2) {
            return false; // El usuario no tiene cuenta
        } else {
            mensaje("Opción no válida. Intente nuevamente.", TipoDeMensaje.ERROR);
            continuarEvento();
            return tieneCuenta();
        }
    }

    public static String iniciarSesion(String tipoUsuario) {
        limpiarConsola();
        System.out.println("=================================================");
        System.out.println("              Bienvenido a OnlineDeal           ");
        System.out.println("=================================================");
        System.out.println("                 Inicio de Sesion                ");
        System.out.println("-------------------------------------------------");
        
        String[] datos_del_usuario = getDatosDelUsuario();
        String username_login = datos_del_usuario[0];
        String password_login = datos_del_usuario[1];

        mensaje("Inicio de sesion satisfactorio.", TipoDeMensaje.EXITO);
        continuarEvento();

        /*

            Aquí puedes agregar la lógica para verificar el usuario y la contraseña en la base de datos
        
        */

        return username_login; // Retorna el username para usarlo en el flujo del comprador o vendedor
    }

    public static void registrarUsuario(String tipoUsuario) {
        limpiarConsola();
        System.out.println("=================================================");
        System.out.println("              Bienvenido a OnlineDeal           ");
        System.out.println("=================================================");
        System.out.println("                Registro de Usuario              ");
        System.out.println("-------------------------------------------------");
        
        String[] datos_del_usuario = getDatosDelUsuario();
        String username_registro = datos_del_usuario[0];
        String password_registro = datos_del_usuario[1];

        mensaje("Usuario registrado correctamente: " + username_registro, TipoDeMensaje.EXITO);
        continuarEvento();
        
        /*

            Aquí puedes agregar el código para insertar el username_registro y password_registro en la base de datos.
        
        */
    }

    // -------------------- METODOS PARA COMPRADOR  -------------------- //

    public static void mostrarMenuComprador() {
        System.out.println("=================================================");
        System.out.println("              Bienvenido a OnlineDeal           ");
        System.out.println("=================================================");
        System.out.println("                     MENU                       ");
        System.out.println("-------------------------------------------------");
        System.out.println("|  1. Carrito                                   |");
        System.out.println("|  2. Mostrar productos                         |");
        System.out.println("|  3. Pagar                                     |");
        System.out.println("|  4. Mi cuenta                                 |");
        System.out.println("|  5. Salir                                     |");
        System.out.println("-------------------------------------------------");
        System.out.print("Seleccione una opción: ");
    }

    // Método para gestionar el flujo del comprador
    public static void gestionarFlujoComprador(String username) {
        boolean salir = false;
        
        while (!salir) {
            mostrarMenuComprador();
            int opcion = 0;
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                mensaje("Por favor, ingrese un número válido.", TipoDeMensaje.ERROR);
                continuarEvento();
                continue;
            }
            
            switch (opcion) {
                case 1:
                    // Lógica para Carrito
                    limpiarConsola();
                    carritoDelComprador();
                    break;
                case 2:
                    // Lógica para Mostrar productos
                    limpiarConsola();
                    mostrarProductosComprador();
                    break;
                case 3:
                    // Lógica para Pagar
                    limpiarConsola();
                    realizarPagoComprador();
                    break;
                case 4:
                    // Lógica para Mi cuenta
                    limpiarConsola();
                    consultarMiCuentaComprador();
                    break;
                case 5:
                    // Salir
                    mensaje("Gracias por usar OnlineDeal. ¡Hasta pronto!", TipoDeMensaje.EXITO);
                    salir = true;
                    break;
                default:
                    mensaje("Opción no válida. Intente nuevamente.", TipoDeMensaje.ERROR);
                    continuarEvento();
            }
        }
    }

    public static void carritoDelComprador() {
        // Hacer fetch de los productos del carrito
        // Mostrar los productos en el carrito
        // Permitir al usuario eliminar productos o proceder a pagar
        // Lógica para gestionar el carrito
        // ...
        mensaje("Este es el carrito |--|/", TipoDeMensaje.INFO);
        continuarEvento();
    }

    public static void mostrarProductosComprador() {
        // Hacer fetch de los productos disponibles
        // Mostrar los productos al usuario
        // Lógica para mostrar productos
        // ...
        mensaje("Estos son los productos disponibles |--|/", TipoDeMensaje.INFO);
        continuarEvento();
    }

    public static void realizarPagoComprador() {
        // Lógica para gestionar el pago
        // ...
        mensaje("Realizando el pago |--|/", TipoDeMensaje.INFO);
        continuarEvento();
    }

    public static void consultarMiCuentaComprador() {
        // Lógica para mostrar la cuenta del usuario
        // ...
        mensaje("Esta es tu cuenta |--|/", TipoDeMensaje.INFO);
        continuarEvento();
    }

    // -------------------- METODOS PARA VENDEDOR -------------------- //

    public static void mostrarMenuVendedor() {
        System.out.println("=================================================");
        System.out.println("            Bienvenido a OnlineDeal             ");
        System.out.println("                 Portal Vendedor                ");
        System.out.println("=================================================");
        System.out.println("                     MENU                       ");
        System.out.println("-------------------------------------------------");
        System.out.println("|  1. Agregar nuevo producto                    |");
        System.out.println("|  2. Ver mis productos                         |");
        System.out.println("|  3. Ver mis ventas                            |");
        System.out.println("|  4. Editar detalles de productos              |");
        System.out.println("|  5. Mi cuenta                                 |");
        System.out.println("|  6. Salir                                     |");
        System.out.println("-------------------------------------------------");
        System.out.print("Seleccione una opción: ");
    }

    // Método para gestionar el flujo de un vendedor
    public static void gestionarFlujoVendedor(String username) {
        boolean salir = false;
        
        while (!salir) {
            mostrarMenuVendedor();
            int opcion = 0;
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                mensaje("Por favor, ingrese un número válido.", TipoDeMensaje.ERROR);
                continuarEvento();
                continue;
            }
            
            switch (opcion) {
                case 1:
                    // Lógica para Agregar nuevo producto
                    limpiarConsola();
                    agregarNuevoProductoVendedor();
                    continuarEvento();
                    break;
                case 2:
                    // Lógica para Ver mis productos
                    limpiarConsola();
                    verMisProductosVendedor();
                    continuarEvento();
                    break;
                case 3:
                    // Lógica para Ver mis ventas
                    limpiarConsola();
                    verMisVentasVendedor();
                    continuarEvento();
                    break;
                case 4:
                    // Lógica para Editar detalles de productos
                    limpiarConsola();
                    editarDetallesProductoVendedor();
                    continuarEvento();
                    break;
                case 5:
                    // Lógica para Mi cuenta
                    limpiarConsola();
                    consultarMiCuentaVendedor();
                    continuarEvento();
                    break;
                case 6:
                    // Salir
                    mensaje("Gracias por usar OnlineDeal. ¡Hasta pronto!", TipoDeMensaje.EXITO);
                    salir = true;
                    break;
                default:
                    mensaje("Opción no válida. Intente nuevamente.", TipoDeMensaje.ERROR);
                    continuarEvento();
            }
        }
    }

    public static void agregarNuevoProductoVendedor() {
        // Lógica para agregar un nuevo producto
        // ...
        mensaje("Agregando nuevo producto |--|/", TipoDeMensaje.INFO);
    }

    public static void verMisProductosVendedor() {
        // Lógica para ver los productos del vendedor
        // ...
        mensaje("Estos son tus productos |--|/", TipoDeMensaje.INFO);
    }

    public static void verMisVentasVendedor() {
        // Lógica para ver las ventas del vendedor
        // ...
        mensaje("Estas son tus ventas |--|/", TipoDeMensaje.INFO);
    }

    public static void editarDetallesProductoVendedor() {
        // Lógica para editar los detalles de un producto
        // ...
        mensaje("Editando detalles del producto |--|/", TipoDeMensaje.INFO);
    }

    public static void consultarMiCuentaVendedor() {
        // Lógica para mostrar la cuenta del vendedor
        // ...
        mensaje("Esta es tu cuenta |--|/", TipoDeMensaje.INFO);
    }
}