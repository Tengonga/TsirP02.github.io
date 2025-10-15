package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ManagedBean
@SessionScoped
public class CarritoBean implements Serializable {
    
    private List<Producto> productos = new ArrayList<>();
    
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }
    
    public List<Producto> getProductos() {
        return productos;
    }
    
    public double getTotal() {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }
    
    public int getCantidad() {
        return productos.size();
    }
    
    public boolean isVacio() {
        return productos.isEmpty();
    }
    
    public boolean tieneTodosLasCategorias() {
        Set<String> categorias = new HashSet<>();
        for (Producto p : productos) {
            categorias.add(p.getCategoria());
        }
        return categorias.size() >= 4; // 4 marcas mínimo
    }
    
    public void limpiar() {
        productos.clear();
    }
}
