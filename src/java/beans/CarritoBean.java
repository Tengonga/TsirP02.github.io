package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
    
    @ManagedProperty(value = "#{idiomaBean}")
    private IdiomaBean idiomaBean;
    
    // Tasa de cambio fija (1 EUR = 1.10 USD)
    private static final double TASA_CAMBIO = 1.10;
    
    // Inicialización segura
    public void init() {
        if (productos == null) {
            productos = new ArrayList<>();
        }
    }
    
    public void setIdiomaBean(IdiomaBean idiomaBean) {
        this.idiomaBean = idiomaBean;
    }
    
    public void agregarProducto(Producto producto) {
        init();
        if (producto != null) {
            productos.add(producto);
        }
    }
    
    public void eliminarProducto(Producto producto) {
        if (productos != null && producto != null) {
            productos.remove(producto);
        }
    }
    
    public List<Producto> getProductos() {
        init();
        return productos;
    }
    
    public double getTotal() {
        if (productos == null || productos.isEmpty()) {
            return 0.0;
        }
        return productos.stream()
                .mapToDouble(p -> p != null ? p.getPrecio() : 0.0)
                .sum();
    }
    
    // NUEVO: Total convertido a dólares si está en inglés
    public double getTotalConvertido() {
        double total = getTotal();
        if (isIdiomaIngles()) {
            return total * TASA_CAMBIO;
        }
        return total;
    }
    
    // NUEVO: Obtener símbolo de moneda según idioma
    public String getSimboloMoneda() {
        return isIdiomaIngles() ? "$" : "€";
    }
    
    // NUEVO: Convertir precio individual con validación
    public double convertirPrecio(Double precio) {
        if (precio == null) return 0.0;
        if (isIdiomaIngles()) {
            return precio * TASA_CAMBIO;
        }
        return precio;
    }
    
    public boolean isIdiomaIngles() {
        return idiomaBean != null && "en".equals(idiomaBean.getIdioma());
    }
    
    public int getCantidad() {
        return productos != null ? productos.size() : 0;
    }
    
    public boolean isVacio() {
        return productos == null || productos.isEmpty();
    }
    
    public boolean getTieneTodosLasCategorias() {
        if (productos == null || productos.isEmpty()) {
            return false;
        }
        
        Set<String> categorias = new HashSet<>();
        for (Producto p : productos) {
            if (p != null && p.getCategoria() != null) {
                categorias.add(p.getCategoria());
            }
        }
        return categorias.size() >= 4;
    }
    
    public void limpiar() {
        if (productos != null) {
            productos.clear();
        }
    }
}