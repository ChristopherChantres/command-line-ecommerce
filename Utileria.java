/*
    -------------------- METODOS DE UTILERIA --------------------
    Los metodos que aparecen aqui pueden ser utilizados en cualquier parte del proyecto
*/

public class Utileria {
    public final static String usuarioComprador = "comprador";
    public final static String usuarioVendedor = "vendedor";
    public final static String archivoCompradores = "ArchivoCompradores.txt";
    public final static String archivoVendedores = "ArchivoVendedores.txt";
    public final static String archivoProductos = "ArchivoProductos.txt";
    public final static String archivoCompras = "ArchivoCompras.txt";


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
        Main.scanner.nextLine();
        limpiarConsola();
    }
}
