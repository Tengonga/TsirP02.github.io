package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class ProductoBean {
    
    // Método para obtener CarritoBean sin inyección
    private CarritoBean getCarritoBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(
            context, "#{carritoBean}", CarritoBean.class);
    }
    
    // Productos Audi
    public List<Producto> getProductosAudi() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto("Audi RS2 Avant", "Audi", 55000, "./Imagenes/audi-rs2-avant-1994-02.jpg"));
        lista.add(new Producto("Audi S4 B5", "Audi", 10000, "./Imagenes/audi-s4-b5-1999.jpg"));
        lista.add(new Producto("Audi TT Mk1", "Audi", 6000, "./Imagenes/audi-tt-mk1-1998.jpg"));
        return lista;
    }
    
    // Productos BMW
    public List<Producto> getProductosBmw() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto("BMW M3 E36", "BMW", 25000, "./Imagenes/bmw-m3-e36-1997.jpg"));
        lista.add(new Producto("BMW M5 E39", "BMW", 34999, "./Imagenes/bmw-m5-e39-1999.jpg"));
        lista.add(new Producto("BMW Z3 M Coupe", "BMW", 68000, "./Imagenes/bmw-z3-m-coupe-1998.jpg"));
        return lista;
    }
    
    // Productos Mercedes
    public List<Producto> getProductosMercedes() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto("Mercedes C36 AMG", "Mercedes", 14000, "./Imagenes/Mercedes-Benz-C36-AMG-W202-1995.jpg"));
        lista.add(new Producto("Mercedes E55 AMG", "Mercedes", 17000, "./Imagenes/Mercedes-Benz-E55-AMG-W210-2000.jpg"));
        lista.add(new Producto("Mercedes SLK", "Mercedes", 8500, "./Imagenes/Mercedes-SLK-R170-1996.jpg"));
        return lista;
    }
    
    // Productos Toyota
    public List<Producto> getProductosToyota() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto("Toyota Supra Mk4", "Toyota", 70000, "./Imagenes/toyota-supra-mk4-1997.jpg"));
        lista.add(new Producto("Toyota Celica GT-Four Carlos Sainz", "Toyota", 36000, "./Imagenes/Toyota-celica-GT4-1992-CS.jpg"));
        lista.add(new Producto("Toyota MR2", "Toyota", 20000, "./Imagenes/Toyota-MR2-1994.jpg"));
        return lista;
    }
    
    public String agregarAlCarrito(Producto producto) {
        getCarritoBean().agregarProducto(producto);
        return "categorias?faces-redirect=true";
    }
}