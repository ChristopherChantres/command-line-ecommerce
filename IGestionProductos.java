import java.util.List;

public interface IGestionProductos {
    void agregarProducto(Producto producto);
    void eliminarProducto(int id_producto);
    List<Producto> getProductosOfrecidos(int id_vendedor);
}
