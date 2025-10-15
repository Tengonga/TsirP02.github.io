package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class ProductoBean {
    
    @ManagedProperty(value = "#{carritoBean}")
    private CarritoBean carritoBean;
    
    public void setCarritoBean(CarritoBean carritoBean) {
        this.carritoBean = carritoBean;
    }
    
    // Productos Audi
    public List<Producto> getProductosAudi() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto("Audi A3", "Audi", 35000, "resources/images/audi_a3.jpg"));
        lista.add(new Producto("Audi A4", "Audi", 45000, "resources/images/audi_a4.jpg"));
        lista.add(new Producto("Audi Q5", "Audi", 55000, "resources/images/audi_q5.jpg"));
        return lista;
    }
    
    // Productos BMW
    public List<Producto> getProductosBmw() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto("BMW Serie 3", "BMW", 42000, "resources/images/bmw_3.jpg"));
        lista.add(new Producto("BMW Serie 5", "BMW", 58000, "resources/images/bmw_5.jpg"));
        lista.add(new Producto("BMW X5", "BMW", 68000, "resources/images/bmw_x5.jpg"));
        return lista;
    }
    
    // Productos Mercedes
    public List<Producto> getProductosMercedes() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto("Mercedes Clase A", "Mercedes", 38000, "resources/images/merc_a.jpg"));
        lista.add(new Producto("Mercedes Clase C", "Mercedes", 48000, "resources/images/merc_c.jpg"));
        lista.add(new Producto("Mercedes GLE", "Mercedes", 65000, "resources/images/merc_gle.jpg"));
        return lista;
    }
    
    // Productos Toyota
    public List<Producto> getProductosToyota() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto("Toyota Corolla", "Toyota", 25000, "resources/images/toyota_corolla.jpg"));
        lista.add(new Producto("Toyota Camry", "Toyota", 32000, "resources/images/toyota_camry.jpg"));
        lista.add(new Producto("Toyota RAV4", "Toyota", 38000, "resources/images/toyota_rav4.jpg"));
        return lista;
    }
    
    public String agregarAlCarrito(Producto producto) {
        carritoBean.agregarProducto(producto);
        return "categorias";
    }
}