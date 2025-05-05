import java.util.ArrayList;

public interface IGestionProductos {
    public void agregarProducto(Producto producto);
    public void eliminarProducto(int id_producto);
    public ArrayList<Producto> getProductosOfrecidos(int id_vendedor);
}
