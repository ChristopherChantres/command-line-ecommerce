public class UsuarioComprador extends Usuario {
    private double saldo;
    private Carrito carrito;
    private TiendaAdministradoraCatalogo tienda;
    private AdministradorOrdenesPasadas administradorOrdenesPasadas; // Instancia del administrador de ordenes pasadas

    // Constructor
    public UsuarioComprador(int idUsuario,String username, String password, int saldo) {
        super(username, password, idUsuario);
        this.saldo = saldo;
        // ******** Implementar la logica para generar el id de compra ******** //
        this.carrito = new Carrito(idUsuario);
        this.tienda = new TiendaAdministradoraCatalogo();
        this.administradorOrdenesPasadas = new AdministradorOrdenesPasadas();
    }

    public UsuarioComprador(int idUsuario, String usernmae, String contrasena) {
        super(usernmae, contrasena, idUsuario);
        // ******** Implementar la logica para generar el id de compra ******** //
        this.carrito = new Carrito(idUsuario);
        this.tienda = new TiendaAdministradoraCatalogo();
    }

    // Getters
    public double getSaldo() {
        return this.saldo;
    }

    public Carrito getCarrito() {
        return this.carrito;
    }

    // Metodos
    public void agregarProductoOQuitarDelCarrito(int idProducto, int cantidad) {
        this.carrito.agregarOQuitarCantidadProducto(idProducto, cantidad);
    }
    
    public boolean eliminarProductoDelCarrito(int idProducto) {
        return this.carrito.eliminarProducto(idProducto);
    }
    
    public void mostrarCarrito() {
        this.carrito.imprimirCarrito();
    }

    public void vaciarCarrito() {
        this.carrito.vaciarCarrito();
    }

    public void mostrarProductosDeLaTienda() {
        this.tienda.impirmirCatalogoConDetalles();
    }

    // Setters
    // ******** Implementar la logica para actualizar el saldo ******** //
    public boolean setSaldo(double saldo){
        this.saldo = saldo;
        return true;
    }


    //-----------------------------------------------------------------------------------------------
    // MANEJO DE ORDENES PASADAS

    public boolean imprimirOrdenesPasadas() {
        if (this.administradorOrdenesPasadas.imprimirOrdenesPasadas(super.getId())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean devolverOrdenPasada(int idOrden) {
        if (this.administradorOrdenesPasadas.devolverCompra(idOrden, super.getId())) {
            return true;
        } else {
            return false;
        }
    }

    /* 
    // Generar un ID positivo
    private static int generarIDPositivo() {
        Random random = new Random();
        return 1 + random.nextInt(999999); // ID positivo entre 1 y 999999
    }
        */
}