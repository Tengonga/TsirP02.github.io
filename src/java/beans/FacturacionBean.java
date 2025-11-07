package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class FacturacionBean implements Serializable {
    private String nombre;
    private String apellidos;
    private String tarjetaCredito;
    private double importePagado;
    private double cambio;
    private boolean pagoExitoso = false;
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getTarjetaCredito() { return tarjetaCredito; }
    public void setTarjetaCredito(String tarjetaCredito) { this.tarjetaCredito = tarjetaCredito; }
    
    public double getImportePagado() { return importePagado; }
    public void setImportePagado(double importePagado) { this.importePagado = importePagado; }
    
    public double getCambio() { return cambio; }
    public void setCambio(double cambio) { this.cambio = cambio; }
    
    public boolean isPagoExitoso() { return pagoExitoso; }
    public void setPagoExitoso(boolean pagoExitoso) { this.pagoExitoso = pagoExitoso; }
    
    // NUEVO MÉTODO: Procesar pago con validación
    public String procesarPago() {
        CarritoBean carritoBean = getCarritoBean();
        double total = carritoBean.getTotal();
        
        if (importePagado < total) {
            // Pago insuficiente
            FacesContext.getCurrentInstance().addMessage(null, 
                new javax.faces.application.FacesMessage(
                    javax.faces.application.FacesMessage.SEVERITY_ERROR,
                    "Pago insuficiente", 
                    "El importe pagado (" + importePagado + "€) es menor al total (" + total + "€). Por favor, ingrese un importe mayor o igual."
                ));
            return null; // Permanece en la misma página
        }
        
        // Pago exitoso
        this.cambio = importePagado - total;
        this.pagoExitoso = true;
        
        // Limpiar carrito después del pago exitoso
        carritoBean.limpiar();
        
        return "facturacion-exitosa?faces-redirect=true";
    }
    
    // NUEVO MÉTODO: Para la página de confirmación
    public String getResumenCompra() {
        CarritoBean carritoBean = getCarritoBean();
        return "Compra realizada por " + nombre + " " + apellidos + 
               " - Total: " + carritoBean.getTotal() + "€ - Pagado: " + 
               importePagado + "€ - Cambio: " + cambio + "€";
    }
    
    private CarritoBean getCarritoBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(
            context, "#{carritoBean}", CarritoBean.class);
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/bienvenida?faces-redirect=true";
    }
    
    // Agregar este método a tu FacturacionBean
public String irACategorias() {
    // Limpiar datos de la facturación para nueva compra
    this.nombre = null;
    this.apellidos = null;
    this.tarjetaCredito = null;
    this.importePagado = 0;
    this.cambio = 0;
    this.pagoExitoso = false;
    
    return "categorias?faces-redirect=true";
}

public String irABienvenida() {
    // Limpiar datos de la facturación
    this.nombre = null;
    this.apellidos = null;
    this.tarjetaCredito = null;
    this.importePagado = 0;
    this.cambio = 0;
    this.pagoExitoso = false;
    
    return "bienvenida?faces-redirect=true";
}
    
}