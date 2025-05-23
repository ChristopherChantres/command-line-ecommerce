import java.util.ArrayList;
import java.util.List;

public class Producto{//Viktor
    protected int id, stock, id_vendedor;
    protected double precio;
    protected String nombre, descripcion, nombreVendedor;
    protected int cantidad;
    protected double subTotal;

    //constructor con las caracteristicas del producto
    public Producto(int id, String nombre, String descripcion, double precio, int stock, int id_vendedor){
        this.id=id;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.precio=precio;
        this.stock=stock;
        this.id_vendedor=id_vendedor;
        cantidad=0;
        //obtener nombre del vendedor a partir de su id
    }

    public Producto(int id, String nombre, String descripcion, double precio, int id_vendedor){
        this.id=id;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.precio=precio;
        this.id_vendedor=id_vendedor;
        cantidad=0;
        //obtener nombre del vendedor a partir de su id
    }

    //Por cada [Productok] almacenamos idProducto, nombre, cantidad, subtotal
    public Producto(int id, String nombre, int idVendedor, int cantidad, double subtotal){//formato para consultar ordenes pasadas y devolver productos
        this.id=id;
        this.nombre=nombre;
        this.id_vendedor=idVendedor;
        this.cantidad=cantidad;
        this.subTotal=subtotal;
        setPrecioPorSubtotal(subtotal);
    }

    public Producto(){
        this.nombre="";
        this.descripcion="";
        this.precio=0;
    }

    //---------------------------------------------------------------------------------------------------------------------
    //METODOS SETTERS
    public void setID(int id){
        this.id=id;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }
    public void setPrecio(double precio){
        this.precio=precio;
    }
    public void setStock(int stock){
        this.stock=stock;
    }

    public void setCantidad(int cantidad){
        this.cantidad=cantidad;
    }

    public void setId_vendedor(int id_vendedor){
        this.id_vendedor=id_vendedor;
    }

    public void setPrecioPorSubtotal(double subtotal){
        precio=subtotal/(double)cantidad;

    }

    //---------------------------------------------------------------------------------------------------------------------
    //METODOS GETTERS
    public int getID(){
        return this.id;
    }
    public String getNombre(){
        return this.nombre;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
    public double getPrecio(){
        return this.precio;
    }
    public int getStock(){
        return this.stock;
    }
    public int getId_vendedor(){
        return this.id_vendedor;
    }

    public int getCantidad(){
        return this.cantidad;
    }

    public double getSubtotal(){
        this.subTotal=this.precio*this.cantidad;
        return subTotal;
    }


    //---------------------------------------------------------------------------------------------------------------------
    //METODOS MODIFICADORES
    public void modificarCantidad(int cantidadModificada){
        //comprobar si despues de la operacion el stock es positivo o cero
        /* 
        if(stock>= cantidadModificada +cantidad){
            cantidad+=cantidadModificada;
        }else{
            cantidad=stock;
        }
        */
        this.cantidad+=cantidadModificada;
        if(this.cantidad<0){
            this.cantidad=0;
        }
        //cantidad mayor o igual a cero
        this.subTotal=(this.precio)*(double)(this.cantidad);//actualiza el subtotal
    }

    public void restarDelStock(){
        stock-=cantidad;
    }

    //public int getNombreVendedor(){
        //llamar a metodo que refresque el nombre del vendedor
    //}

    //modificador del stock
    public void modificarStock(int cambioStock){
        stock+=cambioStock;
    }


    //---------------------------------------------------------------------------------------------------------------------
    //METODOS DE IMPRESION PARA EL COMPRADOR Y EL CARRITO
    //metodo que imprime las caracteristicas completas del producto

    public static void imprimirEncabezado() {
        String line = Utileria.repeat("=", 63);
        System.out.println(line);
        System.out.println(Utileria.centerText("PRODUCTOS DISPONIBLES", 63));
        System.out.println(line);
        System.out.printf("| %-2s | %-20s | %7s | %8s | %-11s |\n",
                          "ID", "Nombre", "Precio", "Vendedor", "Descripción");
        System.out.println("|" + Utileria.repeat("-", 4)
                         + "+" + Utileria.repeat("-", 22)
                         + "+" + Utileria.repeat("-", 9)
                         + "+" + Utileria.repeat("-", 10)
                         + "+" + Utileria.repeat("-", 13)
                         + "|");
    }

    public void imprimirFila() {
        // Establecer el ancho de la tabla
        List<String> descLines = wrapText(descripcion, 30);

        // Imprime la primera línea con el encabezado
        System.out.printf("| %2d | %-20s | %7.2f | %8d | %-11s |\n",
                          id, nombre, precio, id_vendedor, descLines.get(0));

        // Las siguientes líneas de la descripción
        for (int i = 1; i < descLines.size(); i++) {
            System.out.printf("| %-2s | %-20s | %7s | %8s | %-11s |\n",
                              "", "", "", "", descLines.get(i));
        }
    }

    // Divide un texto en líneas de longitud máxima especificada
    private static List<String> wrapText(String text, int maxLineLength) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();

        for (String w : words) {
            if (line.length() + w.length() + 1 > maxLineLength) {
                lines.add(line.toString());
                line = new StringBuilder();
            }
            if (line.length() > 0) line.append(' ');
            line.append(w);
        }
        if (line.length() > 0) lines.add(line.toString());
        return lines;
    }

    public void imprimirDetalles(){
        //refrescar nombre vendedor
        System.out.println("\nID: "+id+" "+nombre+" \t$"+precio);
        System.out.println(" \tID del Vendedor:" + this.id_vendedor);
        // System.out.println(" \tNombre del vendedor:"+nombreVendedor);
        //System.out.println(" \tDisponibilidad de: "+stock+" Piezas");
        System.out.println(" \tDescripcion: "+descripcion);
    }


    //metodo que imprime las caracteristicas esenciales
    public void imprimirBasico(){
        System.out.println(id+" "+nombre+" $"+precio);
    }

    public void imprimirParaCarrito(){//imprime el producto para retroalimentacion del carrito
        System.out.println("Cantidad: "+cantidad+"  ID: "+id+" "+nombre+" Subtotal $"+getSubtotal());
    }

    public void imprimirActualizacionProducto(){//imprime el producto para retroalimentacion del carrito
        System.out.println("Ahora tienes "+cantidad+" unidades del producto "+nombre);
    }

    public void imprimirProductoAgregado(){//imprime el producto gregado al carrito
        Utileria.mensaje("Agregaste "+cantidad+" unidades del producto "+nombre, Utileria.TipoDeMensaje.EXITO);
    }

    public void imprimirEliminarProducto(){//imprime el producto eliminado del carrito
        System.out.println("Eliminaste el producto "+nombre+"con ID: "+id+" del carrito");
    }

    //---------------------------------------------------------------------------------------------------------------------
    //METODOS PARA GENERAR REGISTROS
    //metodo para registrar cada producto del carrito
    public String stringRegistrarProductoEnOrden(){
        //Por cada [Productok] almacenamos idProducto, nombre, idVendedor, cantidad, subtotal
        String idProductoString= String.valueOf(this.id);
        String nombreString= nombre;
        String idVendedorString= String.valueOf(this.id_vendedor);
        String cantidadString= String.valueOf(this.cantidad);
        String subtotalString= String.valueOf(this.subTotal);

        String stringRetornable=idProductoString + "," + nombreString + "," +idVendedorString+ "," + cantidadString + "," + subtotalString + ",";
        return stringRetornable;
    }

    public String stringRegistrarProductoEnArchivo(){
        //formato: idProducto, nombre,descripcion, precio, stock, id_vendedor
        //12,Piguinos,Unos ricos pinguinos,28,10,-4
        String idProductoString= String.valueOf(this.id);
        String nombreString= this.nombre;
        String descripcionString= this.descripcion;
        String precioString= String.valueOf(this.precio);
        //String stockString= String.valueOf(stock);
        String idVendedorString= String.valueOf(this.id_vendedor);

        String stringRetornable=idProductoString + "," + nombreString + "," +descripcionString+ ","+ precioString + ","+idVendedorString;
        return stringRetornable;

    }

}