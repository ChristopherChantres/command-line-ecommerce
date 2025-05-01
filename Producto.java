

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

    public Producto(){
        
    }
    //metodos set
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

    public void setPrecioPorSubtotal(double subtotal){
        precio=subtotal/(double)cantidad;

    }

    //metodos get
    public int getID(){
        return id;
    }
    public String getNombre(){
        return nombre;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public double getPrecio(){
        return precio;
    }
    public int getStock(){
        return stock;
    }
    public int getId_vendedor(){
        return id_vendedor;
    }
    public double get_subtotal(){
        subTotal=precio*cantidad;
        return subTotal;
    }

    public int getCantidad(){
        return cantidad;
    }

    public double getSubtotal(){
        subTotal=precio*cantidad;
        return subTotal;
    }



    public void modificarCantidad(int cantidadModificada){
        //comprobar si despues de la operacion el stock es positivo o cero
        if(stock>= cantidadModificada +cantidad){
            cantidad+=cantidadModificada;
        }else{
            cantidad=stock;
        }
        
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



    //metodo que imprime las caracteristicas completas del producto
    public void imprimirDetalles(){
        //refrescar nombre vendedor
        System.out.println("ID: "+id+" "+nombre+" \t$"+precio);
        System.out.println(" \tNombre del vendedor:"+nombreVendedor);
        System.out.println(" \tDisponibilidad de: "+stock+" Piezas");
        System.out.println(" \tDescripcion: "+descripcion);
    }


    //metodo que imprime las caracteristicas esenciales
    public void imprimirBasico(){
        System.out.println(id+" "+nombre+" $"+precio);
    }

    public void imprimirParaCarrito(){
        System.out.println("Cantidad: "+cantidad+"  ID: "+id+" "+nombre+" Subtotal $"+subTotal);
    }

    public void imprimirActualizacionProducto(){
        System.out.println("Ahora tienes "+cantidad+" unidades del producto "+nombre);
    }

    public void imprimirProductoAgregado(){
        System.out.println("Agregaste "+cantidad+" unidades del producto "+nombre);
    }

    public void imprimirEliminarProducto(){
        System.out.println("Eliminaste el producto "+nombre+"con ID: "+id+" del carrito");
    }

}