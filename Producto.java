

public class Producto{//Viktor
    protected int id, stock, id_vendedor;
    protected double precio;
    protected String nombre, descripcion, nombreVendedor;
    protected int cantidad;
    protected double precioParcial;

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

    public void modificarCantidad(int cantidadModificada){
        //comprobar si despues de la operacion el stock es positivo o cero
        if(stock>= cantidadModificada +cantidad){
            cantidad+=cantidadModificada;
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




}