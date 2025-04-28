import java.util.ArrayList;

puvlic class Vndedor extends Usuario{
  private String tienda;
  private ArrayList <Producto> productosVendidos;

  public Vendedor(String nombre, String correo, String contrasena, String tienda){
    super(nombre, correo, contrasena);
    this.tienda = tienda;
    this.productosVendidos = new ArrayList<>();
  }

  public void AgragarProducto(Producto producto){
    if(producto != null && ptoductosVendidos.contains(producto){
      productosVendidos.add(producto);
    }

    
  }
}
