

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
    public Producto(int id, String nombre, int cantidad, double subtotal){//formato para consultar ordenes pasadas y devolver productos
        this.id=id;
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.subTotal=subtotal;
        setPrecioPorSubtotal(subtotal);
    }

    public Producto(){
        
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
        subTotal=precio*cantidad;
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
        if(cantidad<0){
            cantidad=0;
        }
        //cantidad mayor o igual a cero
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
    public void imprimirDetalles(){
        //refrescar nombre vendedor
        System.out.println("ID: "+id+" "+nombre+" \t$"+precio);
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
        System.out.println("Cantidad: "+cantidad+"  ID: "+id+" "+nombre+" Subtotal $"+subTotal);
    }

    public void imprimirActualizacionProducto(){//imprime el producto para retroalimentacion del carrito
        System.out.println("Ahora tienes "+cantidad+" unidades del producto "+nombre);
    }

    public void imprimirProductoAgregado(){//imprime el producto gregado al carrito
        System.out.println("Agregaste "+cantidad+" unidades del producto "+nombre);
    }

    public void imprimirEliminarProducto(){//imprime el producto eliminado del carrito
        System.out.println("Eliminaste el producto "+nombre+"con ID: "+id+" del carrito");
    }

    //---------------------------------------------------------------------------------------------------------------------
    //METODOS PARA GENERAR REGISTROS
    //metodo para registrar cada producto del carrito
    public String stringRegistrarProductoEnOrden(){
        String idProductoString= String.valueOf(id);
        String nombreString= nombre;
        String cantidadString= String.valueOf(cantidad);
        String subtotalString= String.valueOf(subTotal);

        String stringRetornable=idProductoString + "," + nombreString + "," + cantidadString + "," + subtotalString + ",";
        return stringRetornable;
    }

    public String stringRegistrarProductoEnArchivo(){
        //formato: idProducto, nombre,descripcion, precio, stock, id_vendedor
        //12,Piguinos,Unos ricos pinguinos,28,10,-4
        String idProductoString= String.valueOf(id);
        String nombreString= nombre;
        String descripcionString= descripcion;
        String precioString= String.valueOf(precio);
        //String stockString= String.valueOf(stock);
        String idVendedorString= String.valueOf(id_vendedor);

        String stringRetornable=idProductoString + "," + nombreString + "," +descripcionString+ ","+ precioString + ","+idVendedorString;
        return stringRetornable;

    }

}