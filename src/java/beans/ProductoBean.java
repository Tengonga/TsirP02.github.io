package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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
    
    public CarritoBean getCarritoBean() {
        if (carritoBean == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            carritoBean = context.getApplication().evaluateExpressionGet(
                context, "#{carritoBean}", CarritoBean.class);
        }
        return carritoBean;
    }
    
    // ... tus métodos getProductosAudi(), getProductosBmw(), etc. ...
    
    public String agregarAlCarrito(Producto producto) {
        getCarritoBean().agregarProducto(producto);
        return "categorias?faces-redirect=true";
    }
}