import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in); // Scanner global

    public static void main(String[] args) {
        String tipoUsuario = obtenerTipoUsuario();
        Utileria.limpiarConsola();
        boolean tieneCuenta = tieneCuenta();
        AccesoUsuario user; // Usado para almacenar el usuario (objeto real)
        UsuarioComprador comprador;
        UsuarioVendedor vendedor;
        Optional<AccesoUsuario> userLoggedIn; // Usado para almacenar el Opcional(usuario logueado)

        // Iniciar sesión o registrar usuario
        if (tieneCuenta) {
            userLoggedIn = iniciarSesion(tipoUsuario);
        } else {
            registrarUsuario(tipoUsuario);
            userLoggedIn = iniciarSesion(tipoUsuario);
        }

        // Verificar si el usuario se ha logueado correctamente (que el usuario este dentro de un Optional)
        if (userLoggedIn.isPresent()) {
            user = userLoggedIn.get(); // Usuario que sera usado en el flujo
        } else {
            Utileria.mensaje("Error al iniciar sesión. Saliendo del programa.", Utileria.TipoDeMensaje.ERROR);
            System.exit(0);
            return;
        }

        // Bifurcación de la lógica según el tipo de usuario
        if (tipoUsuario.equals(Utileria.usuarioComprador)) {
            comprador = user.getUsuarioComprador();
            gestionarFlujoComprador(comprador); // Comprador
        } else if (tipoUsuario.equals(Utileria.usuarioVendedor)) {
            vendedor = user.getUsuarioVendedor();
            gestionarFlujoVendedor(vendedor); // Vendedor
        }
    }

    // -------------------- METODOS GENERALES -------------------- //

    // Método para registrar un nuevo usuario
    public static String[] getDatosDelUsuario(String accionEjecutar) {
        Utileria.limpiarConsola();
        System.out.println("=================================================");
        System.out.println("              Bienvenido a OnlineDeal           ");
        System.out.println("=================================================");
        System.out.println("                  " + accionEjecutar);
        System.out.println("-------------------------------------------------");
        String[] datos_del_usuario = new String[2];

        // Solicitar el username
        System.out.print("Username: ");
        String username = scanner.nextLine();
        if (username.isEmpty()) {
            Utileria.mensaje("El username no puede estar vacio.", Utileria.TipoDeMensaje.ERROR);
            Utileria.continuarEvento();
            return getDatosDelUsuario(accionEjecutar); // Reintentar registro
        }

        // Solicitar el password
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (password.isEmpty()) {
            Utileria.mensaje("El password no puede estar vacio.", Utileria.TipoDeMensaje.ERROR);
            Utileria.limpiarConsola();
            Utileria.continuarEvento();
            return getDatosDelUsuario(accionEjecutar); // Reintentar registro
        }

        // Guardar los datos del usuario en el array
        datos_del_usuario[0] = username;
        datos_del_usuario[1] = password;
        return datos_del_usuario;
    }

    public static String obtenerTipoUsuario() {
        Utileria.limpiarConsola();
        System.out.println("=================================================");
        System.out.println("              Bienvenido a OnlineDeal           ");
        System.out.println("=================================================");
        System.out.println("                 Seleccione su rol               ");
        System.out.println("-------------------------------------------------");
        System.out.println("1. Comprador (Cliente)");
        System.out.println("2. Vendedor");
        System.out.println("-------------------------------------------------");
        System.out.print("Seleccione una opción (1-2): ");
        
        int opcion = 0;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
            Utileria.continuarEvento();
            obtenerTipoUsuario();
        }
        
        switch (opcion) {
            case 1:
                return Utileria.usuarioComprador;
            case 2:
                return Utileria.usuarioVendedor;
            default:
                Utileria.mensaje("Opción no válida. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                return obtenerTipoUsuario();
        }
    }

    public static boolean tieneCuenta() {
        Utileria.limpiarConsola();
        // Validar si el usuario tiene una cuenta
        System.out.println("=================================================");
        System.out.println("              Bienvenido a OnlineDeal           ");
        System.out.println("=================================================");
        System.out.println("|  Ya tienes una cuenta creada?");
        System.out.println("|  1. Si");
        System.out.println("|  2. No");
        System.out.println("-------------------------------------------------");
        System.out.print("Seleccione una opción (1-2): ");
        int opcion = 0;

        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
            Utileria.continuarEvento();
            return tieneCuenta();
        }

        if (opcion == 1) {
            return true; // El usuario tiene cuenta
        } else if (opcion == 2) {
            return false; // El usuario no tiene cuenta
        } else {
            Utileria.mensaje("Opción no válida. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
            Utileria.continuarEvento();
            return tieneCuenta();
        }
    }

    public static Optional<AccesoUsuario> iniciarSesion(String tipoUsuario) {
        int intentos = 0;

        while (intentos < 3) {
            String[] datos_del_usuario = getDatosDelUsuario("Iniciar Sesion");
            String username_login = datos_del_usuario[0];
            String password_login = datos_del_usuario[1];
            AccesoUsuario accesoUsuario = new AccesoUsuario(username_login, password_login, tipoUsuario);
            
            if (accesoUsuario.getExisteUsuario()) {
                if (tipoUsuario.equals(Utileria.usuarioComprador)) {
                    // Lógica para el flujo del comprador
                    Utileria.mensaje("Bienvenido " + username_login, Utileria.TipoDeMensaje.EXITO);
                    Utileria.continuarEvento();
                    return Optional.of(accesoUsuario);
                } else if (tipoUsuario.equals(Utileria.usuarioVendedor)) {
                    // Lógica para el flujo del vendedor
                    Utileria.mensaje("Bienvenido " + username_login + " (Perfil de Vendedor)", Utileria.TipoDeMensaje.EXITO);
                    Utileria.continuarEvento();
                    return Optional.of(accesoUsuario);
                }
            } else {
                Utileria.mensaje("Usuario o contraseña incorrectos. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                intentos++;
            }
        }

        Utileria.mensaje("Demasiados intentos fallidos. Saliendo del programa por razones de seguridad.", Utileria.TipoDeMensaje.INFO);
        System.exit(0);
        return Optional.empty(); // Si no se encuentra el usuario, retornar vacío
    }

    public static void registrarUsuario(String tipoUsuario) {
        int intentos = 0;

        while (intentos < 3) {
            String[] datos_del_usuario = getDatosDelUsuario("Registro de Usuario");
            String username_registro = datos_del_usuario[0];
            String password_registro = datos_del_usuario[1];
            AccesoUsuario accesoUsuario = new AccesoUsuario(username_registro, password_registro, tipoUsuario);

            // Verificar si el usuario ya existe
            if (accesoUsuario.getExisteUsuario(username_registro, tipoUsuario)) {
                Utileria.mensaje("El usuario ya existe. Intente con otro nombre de usuario.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                intentos++;
                continue;
            }

            // Dependiendo del tipo de usuario, se registra como comprador o vendedor en el sistema
            boolean registroExitoso = false;
            if (tipoUsuario.equals(Utileria.usuarioComprador)) {
                registroExitoso = accesoUsuario.registrarComprador(username_registro, password_registro);
            } else if (tipoUsuario.equals(Utileria.usuarioVendedor)) {
                registroExitoso = accesoUsuario.registrarVendedor(username_registro, password_registro);
            }

            if (!registroExitoso) {
                Utileria.mensaje("Error al registrar el usuario. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                intentos++;
                continue;
            }

            Utileria.mensaje("Usuario registrado correctamente: " + username_registro, Utileria.TipoDeMensaje.EXITO);
            Utileria.continuarEvento();
            return;
        }

        Utileria.mensaje("Demasiados intentos fallidos. Saliendo del programa por razones de seguridad.", Utileria.TipoDeMensaje.INFO);
        System.exit(0);
    }

    // -------------------- METODOS PARA COMPRADOR  -------------------- //

    public static void mostrarMenuComprador(double saldo) {
        System.out.println("=================================================");
        System.out.println("              Bienvenido a OnlineDeal           ");
        System.out.println("=================================================");
        System.out.println("                     MENU       " + "Saldo: $" + saldo);
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
    public static void gestionarFlujoComprador(UsuarioComprador comprador) {
        boolean salir = false;
        
        while (!salir) {
            mostrarMenuComprador(comprador.getSaldo());
            int opcion = 0;
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                continue;
            }
            
            switch (opcion) {
                case 1:
                    // Lógica para Carrito
                    Utileria.limpiarConsola();
                    carritoDelComprador(comprador);
                    break;
                case 2:
                    // Lógica para Mostrar productos
                    Utileria.limpiarConsola();
                    mostrarProductosComprador(comprador);
                    break;
                case 3:
                    // Lógica para Pagar
                    Utileria.limpiarConsola();
                    realizarPagoComprador(comprador);
                    break;
                case 4:
                    // Lógica para Mi cuenta
                    Utileria.limpiarConsola();
                    consultarMiCuentaComprador(comprador);
                    break;
                case 5:
                    // Salir
                    Utileria.mensaje("Gracias por usar OnlineDeal. ¡Hasta pronto!", Utileria.TipoDeMensaje.EXITO);
                    salir = true;
                    break;
                default:
                    Utileria.mensaje("Opción no válida. Intente nuevamente.",Utileria.TipoDeMensaje.ERROR);
                    Utileria.continuarEvento();
            }
        }
    }

    public static void carritoDelComprador(UsuarioComprador comprador) {
        boolean salirDeCarrito = false;
        
        while (!salirDeCarrito) {
            // Limpiar la pantalla para mostrar información actualizada
            Utileria.limpiarConsola();
            
            // Lógica para mostrar el carrito
            System.out.println("=================================================");
            System.out.println("             Carrito de Compras                 ");
            System.out.println("=================================================");
        
            // Mostrar los productos en el carrito
            comprador.mostrarCarrito();
            System.out.println("-------------------------------------------------");
            System.out.println("1. Eliminar producto del carrito");
            System.out.println("2. Vaciar todo el carrito");
            System.out.println("3. Regresar");
            System.out.println("-------------------------------------------------");
    
            int opcion = 0;
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                continue; // Salir del método
            }
    
            switch (opcion) {
                case 1:
                    // Lógica para eliminar producto del carrito
                    System.out.print("Ingrese el ID del producto a eliminar: ");
                    int idProducto = 0;
                    try {
                        idProducto = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                        continue; // Salir del método
                    }
                    
                    boolean productoEliminado = comprador.eliminarProductoDelCarrito(idProducto);
                    if (productoEliminado) {
                        Utileria.mensaje("Producto eliminado del carrito con éxito.", Utileria.TipoDeMensaje.EXITO);
                        Utileria.continuarEvento();
                        break;
                    } else {
                        Utileria.mensaje("No se encontró el id del producto en el carrito.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                        continue; // Salir del método
                    }
                case 2:
                    // Lógica para vaciar el carrito
                    comprador.vaciarCarrito();
                    Utileria.mensaje("Carrito vaciado.", Utileria.TipoDeMensaje.EXITO);
                    Utileria.continuarEvento();
                    break;
                case 3:
                    // Regresar
                    salirDeCarrito = true;
                    break;
                default:
                    Utileria.mensaje("Opción no válida. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                    Utileria.continuarEvento();
                    break;
            }
        }
        Utileria.limpiarConsola();
    }

    public static void mostrarProductosComprador(UsuarioComprador comprador) {
        boolean salirDeProductos = false;

        while (!salirDeProductos) {
            // Limpiar la pantalla para mostrar información actualizada
            Utileria.limpiarConsola();
            
            // Lógica para mostrar        
            comprador.mostrarProductosDeLaTienda();
            System.out.println("-------------------------------------------------");
            System.out.println("1. Agregar producto al carrito");
            System.out.println("2. Regresar");
            System.out.println("-------------------------------------------------");
    
            int opcion = 0;
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                return; // Salir del método
            }
    
            switch (opcion) {
                case 1:
                    // Lógica para agregar producto al carrito
                    System.out.print("Ingrese el ID del producto a agregar: ");
                    int idProducto = 0;
                    try {
                        idProducto = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                        continue;
                    }
                    
                    System.out.print("Ingrese la cantidad a agregar (positivo) o quitar (negativo): ");
                    int cantidad = 0;
                    try {
                        cantidad = Integer.parseInt(scanner.nextLine());
                        if (cantidad == 0) {
                            Utileria.mensaje("La cantidad no puede ser cero.", Utileria.TipoDeMensaje.ERROR);
                            Utileria.continuarEvento();
                            continue; // Salir del método
                        }
                    } catch (NumberFormatException e) {
                        Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                        continue; // Salir del método
                    }

                    comprador.agregarProductoOQuitarDelCarrito(idProducto, cantidad);
                    if (cantidad > 0) {
                        Utileria.mensaje("Producto agregado al carrito con éxito.", Utileria.TipoDeMensaje.EXITO);
                        // Incrementar el total del carrito
                        Utileria.continuarEvento();
                    } else {
                        Utileria.mensaje("Producto eliminado del carrito con éxito.", Utileria.TipoDeMensaje.EXITO);
                        Utileria.continuarEvento();
                    }
                    break;
                case 2:
                    // Regresar
                    salirDeProductos = true;
                    break;
                default:
                    Utileria.mensaje("Opción no válida. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                    Utileria.continuarEvento();
                    break;
            }
        }
        Utileria.limpiarConsola();
    }

    public static void realizarPagoComprador(UsuarioComprador comprador) {
        boolean salirDePago = false;
        double totalPagar = comprador.getCarrito().getTotal();

        while (!salirDePago) {
            // Limpiar la pantalla para mostrar información actualizada
            Utileria.limpiarConsola();
            
            // Lógica para realizar el pago
            System.out.println("=================================================");
            System.out.println("             Realizar Pago                       ");
            System.out.println("=================================================");
            System.out.println("Total a pagar: " + totalPagar);
            System.out.println("-------------------------------------------------");
            System.out.println("1. Pagar Total");
            System.out.println("2. Salir");
            System.out.println("-------------------------------------------------");
            System.out.print("Seleccione una opción: ");
            int opcion = 0;
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                Utileria.mensaje("Por favor, ingrese una opción válida.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                continue; // Continuar el ciclo sin salir
            }

            switch (opcion) {
                case 1:
                    // Validamos si total a pagar es 0
                    if (totalPagar == 0) {
                        Utileria.mensaje("No hay productos en el carrito para pagar.", Utileria.TipoDeMensaje.INFO);
                        Utileria.continuarEvento();
                        continue;
                    }

                    // Lógica para pagar el total
                    if (comprador.getSaldo() < totalPagar) {
                        Utileria.mensaje("No tienes suficiente saldo para realizar el pago.", Utileria.TipoDeMensaje.INFO);
                        Utileria.continuarEvento();
                        continue; // Continuar el ciclo sin salir
                    } else {
                        boolean pagoExitoso = comprador.getCarrito().ordenar();
                        if (pagoExitoso) {
                            double nuevoSaldo = comprador.getSaldo() - totalPagar;
                            comprador.setSaldo(nuevoSaldo);
                            Utileria.mensaje("Pago realizado con éxito.", Utileria.TipoDeMensaje.EXITO);
                            Utileria.continuarEvento();
                        } else {
                            Utileria.mensaje("Error al realizar el pago. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                            Utileria.continuarEvento();
                            continue; // Continuar el ciclo sin salir
                        }
                    }
                    salirDePago = true; // Salir del bucle de pago
                    break;
                case 2:
                    salirDePago = true; // Salir del bucle de pago
                    break;
                default:
                    Utileria.mensaje("Opción no válida. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                    Utileria.continuarEvento();
                    break;
            }
        }
        Utileria.limpiarConsola();
    }

    public static void consultarMiCuentaComprador(UsuarioComprador comprador) {
        boolean salirDeCuenta = false;
        
        while (!salirDeCuenta) {
            // Limpiar la pantalla para mostrar información actualizada
            Utileria.limpiarConsola();
            
            // Lógica para mostrar la cuenta del usuario
            System.out.println("=================================================");
            System.out.println("             Mi Cuenta                          ");
            System.out.println("=================================================");
            System.out.println("Usuario: " + comprador.getUsername());
            System.out.println("Contraseña: " + comprador.getPassword());
            System.out.println("Saldo: $" + comprador.getSaldo());
            System.out.println("-------------------------------------------------");
            System.out.println("1. Cambiar contraseña");
            System.out.println("2. Depositar saldo");
            System.out.println("3. Ver mis compras");
            System.out.println("4. Regresar");
            System.out.println("-------------------------------------------------");
            System.out.print("Seleccione una opción: ");
            int opcion = 0;
    
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                continue; // Continuar el ciclo sin salir
            }
    
            switch (opcion) {
                case 1:
                    // Lógica para cambiar contraseña
                    System.out.print("Ingrese la nueva contraseña: ");
                    String nuevaPassword = scanner.nextLine();
    
                    if (nuevaPassword.isEmpty()) {
                        Utileria.mensaje("Contraseña password no puede estar vacía.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                        continue;
                    }
                    
                    AccesoUsuario accesoUsuario = new AccesoUsuario(comprador.getUsername(), comprador.getPassword(), Utileria.usuarioComprador);
                    boolean passwordCambiada = accesoUsuario.cambiarPassword(nuevaPassword);
                    if (passwordCambiada) {
                        Utileria.mensaje("Contraseña cambiada con éxito.", Utileria.TipoDeMensaje.EXITO);
                        comprador.setPassword(nuevaPassword); // Actualizar la contraseña en el objeto UsuarioComprador
                        Utileria.continuarEvento();
                    } else {
                        Utileria.mensaje("Error al cambiar la contraseña. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                    }
                    break;
                    
                case 2:
                    // Lógica para depositar saldo
                    System.out.print("Ingrese la cantidad a depositar: ");
                    double cantidadADepositar = 0;
    
                    try {
                        cantidadADepositar = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                        continue; // Continuar en Mi Cuenta
                    }
    
                    if (cantidadADepositar <= 0) {
                        Utileria.mensaje("La cantidad a depositar debe ser mayor que cero.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                        continue; // Continuar en Mi Cuenta
                    }
    
                    boolean saldoDepositado = comprador.setSaldo(comprador.getSaldo() + cantidadADepositar);
                    if (saldoDepositado) {
                        Utileria.mensaje("Nuevo saldo: $" + comprador.getSaldo(), Utileria.TipoDeMensaje.EXITO);
                        Utileria.continuarEvento();
                    } else {
                        Utileria.mensaje("Error al depositar el saldo. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                    }
                    break;
                    
                case 3:
                    mostrarComprasComprador(comprador);
                    break;
                
                case 4:
                    salirDeCuenta = true; // Establecer la bandera para salir del bucle
                    Utileria.limpiarConsola();
                    break;
                default:
                    Utileria.mensaje("Opción no válida. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                    Utileria.continuarEvento();
                    break;
            }
        }
    }

    public static void mostrarComprasComprador(UsuarioComprador comprador) {
        boolean salirDeCompras = false;
        while (!salirDeCompras) {
            Utileria.limpiarConsola();
            System.out.println("=================================================");
            System.out.println("             Mis Compras                        ");
            System.out.println("=================================================");

            boolean hayOrdenesPasadas = comprador.imprimirOrdenesPasadas();
            if (!hayOrdenesPasadas) {
                Utileria.mensaje("No tienes compras pasadas.", Utileria.TipoDeMensaje.INFO);
                Utileria.continuarEvento();
                salirDeCompras = true; // Salir del bucle
                break;
            }

            System.out.println("-------------------------------------------------");
            System.out.println("1. Devolver compra");
            System.out.println("2. Regresar");
            System.out.println("-------------------------------------------------");

            System.out.print("Seleccione una opción: ");
            int opcion = 0;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                continue; // Continuar el ciclo sin salir
            }
            switch (opcion) {
                case 1:
                    // Lógica para devolver compra
                    System.out.println("------------------------------------------------");
                    System.out.print("Ingrese el ID de la compra a devolver: ");
                    int idCompra = 0;
                    try {
                        idCompra = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                        continue; // Salir del método
                    }
                    
                    double totalOrdenDevuelta = comprador.devolverOrdenPasada(idCompra);
                    if (totalOrdenDevuelta == -1) {
                        Utileria.mensaje("Error al devolver la compra. Verifica que el ID es correcto.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                    } else {
                        double nuevoSaldo = comprador.getSaldo() + totalOrdenDevuelta;
                        comprador.setSaldo(nuevoSaldo);
                        Utileria.mensaje("Compra devuelta!", Utileria.TipoDeMensaje.EXITO);
                        Utileria.continuarEvento();
                    }
                    break;
                case 2:
                    salirDeCompras = true; // Salir del bucle
                    break;
                default:
                    Utileria.mensaje("Opción no válida. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                    Utileria.continuarEvento();
            }
        }
        Utileria.limpiarConsola();
    }

    // -------------------- METODOS PARA VENDEDOR -------------------- //

    public static void mostrarMenuVendedor() {
        System.out.println("=================================================");
        System.out.println("            Bienvenido a OnlineDeal             ");
        System.out.println("                 (Portal Vendedor)              ");
        System.out.println("=================================================");
        System.out.println("                     MENU                       ");
        System.out.println("-------------------------------------------------");
        System.out.println("|  1. Agregar nuevo producto                    |");
        System.out.println("|  2. Ver mis productos                         |");
        System.out.println("|  3. Ver mis ventas                            |");
        System.out.println("|  4. Mi cuenta                                 |");
        System.out.println("|  5. Salir                                     |");
        System.out.println("-------------------------------------------------");
        System.out.print("Seleccione una opción: ");
    }

    // Método para gestionar el flujo de un vendedor
    public static void gestionarFlujoVendedor(UsuarioVendedor vendedor) {
        boolean salir = false;
        
        while (!salir) {
            mostrarMenuVendedor();
            int opcion = 0;
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                continue;
            }
            
            switch (opcion) {
                case 1:
                    // Lógica para Agregar nuevo producto
                    Utileria.limpiarConsola();
                    agregarNuevoProductoVendedor(vendedor);
                    break;
                case 2:
                    // Lógica para Ver mis productos
                    Utileria.limpiarConsola();
                    verMisProductosVendedor(vendedor);
                    break;
                case 3:
                    // Lógica para Ver mis ventas
                    Utileria.limpiarConsola();
                    verMisVentasVendedor(vendedor);
                    break;

                case 4:
                    // Lógica para Mi cuenta
                    Utileria.limpiarConsola();
                    consultarMiCuentaVendedor(vendedor);
                    break;

                case 5:
                    // Salir
                    Utileria.mensaje("Gracias por usar OnlineDeal. ¡Hasta pronto!", Utileria.TipoDeMensaje.EXITO);
                    salir = true;
                    break;
                default:
                    Utileria.mensaje("Opción no válida. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                    Utileria.continuarEvento();
            }
        }
    }

    public static void agregarNuevoProductoVendedor(UsuarioVendedor vendedor) {
        boolean salirDeAgregarProducto = false;

        while (!salirDeAgregarProducto) {
            Utileria.limpiarConsola();
            System.out.println("=================================================");
            System.out.println("             Agregar Nuevo Producto             ");
            System.out.println("=================================================");
            System.out.print("Nombre del producto: ");
            String nombreProducto = scanner.nextLine();
    
            System.out.print("Descripción del producto: ");
            String descripcionProducto = scanner.nextLine();
    
            System.out.print("Precio del producto: ");
            double precioProducto = 0;
            try {
                precioProducto = Double.parseDouble(scanner.nextLine());
                if (precioProducto <= 0) {
                    Utileria.mensaje("El precio debe ser mayor que cero.", Utileria.TipoDeMensaje.ERROR);
                    Utileria.continuarEvento();
                    continue; // Salir del método
                }
            } catch (NumberFormatException e) {
                Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                continue; // Salir del método
            }
    
            boolean seAgregoProducto = vendedor.agregarProducto(nombreProducto, descripcionProducto, precioProducto);
            if (seAgregoProducto) {
                Utileria.mensaje("Producto agregado!", Utileria.TipoDeMensaje.EXITO);
                Utileria.continuarEvento();
                salirDeAgregarProducto = true; // Salir del bucle
            } else {
                Utileria.mensaje("Error al agregar el producto. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                salirDeAgregarProducto = false; // No salir del bucle
                continue; // Salir del método
            }
        }
        Utileria.limpiarConsola();
    }

    public static void verMisProductosVendedor(UsuarioVendedor vendedor) {
        boolean salirDeProductos = false;

        while (!salirDeProductos) {
            System.out.println("=================================================");
            System.out.println("             Mis Productos                       ");
            System.out.println("=================================================");
            vendedor.imprimirProductosOfrecidos();
            System.out.println("-------------------------------------------------");
            System.out.println("1. Eliminar producto");
            System.out.println("2. Regresar");
            System.out.println("-------------------------------------------------");
            System.out.print("Seleccione una opción: ");
            int opcion = 0;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                continue; // Salir del método
            }
            switch (opcion) {
                case 1:
                    // Lógica para eliminar un producto
                    System.out.println("------------------------------------------------");
                    System.out.print("ID del producto a eliminar: ");
                    int idProductoEliminar = 0;
                    try {
                        idProductoEliminar = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                        continue; // Salir del método
                    }
                    
                    boolean productoEliminado = vendedor.eliminarProducto(idProductoEliminar);
                    if (productoEliminado) {
                        Utileria.mensaje("Producto eliminado con éxito.", Utileria.TipoDeMensaje.EXITO);
                        Utileria.continuarEvento();
                    } else {
                        Utileria.mensaje("No se encontró el producto con el ID especificado.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                    }
                    break;
                case 2:
                    salirDeProductos = true; // Salir del bucle
                    break;
                default:
                    Utileria.mensaje("Opción no válida. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                    Utileria.continuarEvento();
            }

        }
        Utileria.limpiarConsola();
    }

    public static void verMisVentasVendedor(UsuarioVendedor vendedor) {
        boolean salirDeVentas = false;

        while (!salirDeVentas) {
            System.out.println("=================================================");
            System.out.println("             Mis Ventas                          ");
            System.out.println("=================================================");
            vendedor.imprimirVentas();
            System.out.println("-------------------------------------------------");
            Utileria.continuarEvento();
            salirDeVentas = true; // Salir del bucle
        }
        Utileria.limpiarConsola();
    }

    public static void consultarMiCuentaVendedor(UsuarioVendedor vendedor) {
        boolean salirDeCuenta = false;
        
        while (!salirDeCuenta) {
            Utileria.limpiarConsola();
            System.out.println("=================================================");
            System.out.println("             Mi Cuenta                          ");
            System.out.println("=================================================");
            System.out.println("Usuario: " + vendedor.getUsername());
            System.out.println("Contraseña: " + vendedor.getPassword());
            System.out.println("-------------------------------------------------");
            System.out.println("1. Cambiar contraseña");
            System.out.println("2. Borrar cuenta");
            System.out.println("3. Eliminar todos los productos");
            System.out.println("4. Regresar");
            System.out.println("-------------------------------------------------");
    
            int opcion = 0;
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                Utileria.continuarEvento();
                continue; // Continuar el ciclo sin salir
            }
    
            switch (opcion) {
                case 1:
                    // Lógica para cambiar contraseña
                    System.out.print("Ingrese la nueva contraseña: ");
                    String nuevaPassword = scanner.nextLine();
    
                    if (nuevaPassword.isEmpty()) {
                        Utileria.mensaje("Contraseña no puede estar vacía.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                        continue;
                    }
                    
                    AccesoUsuario accesoUsuario = new AccesoUsuario(vendedor.getUsername(), vendedor.getPassword(), Utileria.usuarioVendedor);
                    boolean passwordCambiada = accesoUsuario.cambiarPassword(nuevaPassword);
                    vendedor.setPassword(nuevaPassword); // Actualizar la contraseña en el objeto UsuarioVendedor
                    if (passwordCambiada) {
                        Utileria.mensaje("Contraseña cambiada con éxito.", Utileria.TipoDeMensaje.EXITO);
                        Utileria.continuarEvento();
                    } else {
                        Utileria.mensaje("Error al cambiar la contraseña. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                    }
                    break;
                    
                case 2:
                    // Borrar vendedor
                    System.out.println("------------------------------------------------");
                    System.out.println("¿Está seguro de que desea borrar su cuenta?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");
                    System.out.println("-------------------------------------------------");
                    System.out.print("Seleccione una opción: ");
                    int opcionBorrar = 0;
                    try {
                        opcionBorrar = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                        continue; // Continuar el ciclo sin salir
                    }
                    switch (opcionBorrar) {
                        case 1:
                            // Lógica para borrar cuenta
                            Utileria.limpiarConsola();
                            System.out.println("------------------------------------------------");
                            System.out.print("Ingrese su contraseña para confirmar: ");
                            String passwordConfirmacion = scanner.nextLine();
                            if (!vendedor.getPassword().equals(passwordConfirmacion)) {
                                Utileria.mensaje("Contraseña incorrecta. No se puede borrar la cuenta.", Utileria.TipoDeMensaje.ERROR);
                                Utileria.continuarEvento();
                                continue; // Continuar el ciclo sin salir
                            }

                            AccesoUsuario accesoUsuarioBorrar = new AccesoUsuario(vendedor.getUsername(), vendedor.getPassword(), Utileria.usuarioVendedor);
                            boolean cuentaBorrada = accesoUsuarioBorrar.eliminarUsuario();
                            if (cuentaBorrada) {
                                vendedor.eliminarTodosLosProductos();
                                Utileria.mensaje("Cuenta borrada con éxito. Adios vaquero!", Utileria.TipoDeMensaje.EXITO);
                                Utileria.continuarEvento();
                                salirDeCuenta = true; // Salir del bucle
                                System.exit(0); // Salir del programa
                            } else {
                                Utileria.mensaje("Error al borrar la cuenta. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                                Utileria.continuarEvento();
                            }
                            break;
                        case 2:
                            break;
                        default:
                            Utileria.mensaje("Opción no válida. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                            Utileria.continuarEvento();
                    }
                    break;

                case 3:
                    // Lógica para eliminar todos los productos
                    System.out.println("------------------------------------------------");
                    System.out.println("¿Está seguro de que desea eliminar todos los productos?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");
                    System.out.println("-------------------------------------------------");
                    System.out.print("Seleccione una opción: ");
                    int opcionEliminar = 0;

                    try {
                        opcionEliminar = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        Utileria.mensaje("Por favor, ingrese un número válido.", Utileria.TipoDeMensaje.ERROR);
                        Utileria.continuarEvento();
                        continue; // Continuar el ciclo sin salir
                    }

                    switch (opcionEliminar) {
                        case 1:
                            // Lógica para eliminar todos los productos
                            vendedor.eliminarTodosLosProductos();
                            Utileria.mensaje("Todos los productos han sido eliminados.", Utileria.TipoDeMensaje.EXITO);
                            Utileria.continuarEvento();
                            break;
                        case 2:
                            break;
                        default:
                            Utileria.mensaje("Opción no válida. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                            Utileria.continuarEvento();
                    }
                    break;

                case 4:
                    salirDeCuenta = true; // Establecer la bandera para salir del bucle
                    break;
                default:
                    Utileria.mensaje("Opción no válida. Intente nuevamente.", Utileria.TipoDeMensaje.ERROR);
                    Utileria.continuarEvento();
            }
        }
        Utileria.limpiarConsola();
    }
}