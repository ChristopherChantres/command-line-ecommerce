import java.util.ArrayList;

public interface IGestionProductos {
    void agregarProducto(Producto producto);
    void eliminarProducto(int id_producto);
    ArrayList<Producto> getProductosOfrecidos(int id_vendedor);
}
