import java.security.PublicKey;
import java.util.ArrayList;

public class Vendedor extends Usuarios{
  private String tienda;
  private ArrayList <Producto> productosVendidos;

  public Vendedor(String nombre, String correo, String contrasena, String tienda){
    super(nombre, correo, contrasena);
    this.tienda = tienda;
    this.productosVendidos = new ArrayList<>();
  }

  public void agregarProducto(Producto producto){
    if(producto != null && productosVendidos.contains(producto)){
      productosVendidos.add(producto);
    }
  }

    public void eliminarProducto(Producto producto){
      if(producto ! = null){
        productosVendidos.remove(producto);
      }
    }
  
}
