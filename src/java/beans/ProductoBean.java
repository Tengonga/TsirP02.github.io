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
        lista.add(new Producto("Audi RS2 Avant", "Audi", 55000, "https://www.classicandsportscar.com/sites/default/files/styles/article/public/2023-11/Classic-and-Sports-Car-Buying-Guide-Audi-RS2-Avant-14.png?itok=0PAkN2i5"));
        lista.add(new Producto("Audi S4 B5", "Audi", 10000, "resources/images/audi_a4.jpg"));
        lista.add(new Producto("Audi TT Mk1", "Audi", 6000, "resources/images/audi_q5.jpg"));
        return lista;
    }
    
    // Productos BMW
    public List<Producto> getProductosBmw() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto("BMW M3 E36", "BMW", 25000, "resources/images/bmw_3.jpg"));
        lista.add(new Producto("BMW M5 E39", "BMW", 34999, "resources/images/bmw_5.jpg"));
        lista.add(new Producto("BMW Z3 M Coupe", "BMW", 68000, "resources/images/bmw_x5.jpg"));
        return lista;
    }
    
    // Productos Mercedes
    public List<Producto> getProductosMercedes() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto("Mercedes C36 AMG", "Mercedes", 14000, "resources/images/merc_a.jpg"));
        lista.add(new Producto("Mercedes E55 AMG", "Mercedes", 17000, "resources/images/merc_c.jpg"));
        lista.add(new Producto("Mercedes SLK", "Mercedes", 6500, "resources/images/merc_gle.jpg"));
        return lista;
    }
    
    // Productos Toyota
    public List<Producto> getProductosToyota() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto("Toyota Supra Mk4", "Toyota", 70000, "resources/images/toyota_corolla.jpg"));
        lista.add(new Producto("Toyota Celica GT4 Carlos Sainz", "Toyota", 36000, "resources/images/toyota_camry.jpg"));
        lista.add(new Producto("Toyota MR2", "Toyota", 15000, "resources/images/toyota_rav4.jpg"));
        return lista;
    }
    
    public String agregarAlCarrito(Producto producto) {
        carritoBean.agregarProducto(producto);
        return "categorias";
    }
}