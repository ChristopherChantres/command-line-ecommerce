

public class Producto{//Viktor
    protected int id, stock;
    protected double precio;
    protected String nombre, descripcion;

    //constructor con las caracteristicas del producto
    public Producto(int id, String nombre, String descripcion, double precio, int stock){
        this.id=id;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.precio=precio;
        this.stock=stock;
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
    
    //modificador del stock
    public void modificarStock(int cambioStock){
        stock+=cambioStock;
    }


    //metodo que imprime las caracteristicas completas del producto
    public void imprimirDetalles(){
        System.out.println("ID: "+id+" "+nombre+" \t$"+precio);
        System.out.println(" \tDisponibilidad de: "+stock+" Piezas");
        System.out.println(" \tDescripcion: "+descripcion);
    }


    //metodo que imprime las caracteristicas esenciales
    public void imprimirBasico(){
        System.out.println(id+" "+nombre+" $"+precio);
    }




}