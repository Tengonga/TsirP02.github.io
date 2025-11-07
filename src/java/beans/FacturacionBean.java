package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;

@ManagedBean
@SessionScoped
public class FacturacionBean implements Serializable {
    private String nombre;
    private String apellidos;
    private String tarjetaCredito;
    private Date fechaCaducidad;
    private double importePagado;
    private boolean pagoExitoso = false;
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getTarjetaCredito() { return tarjetaCredito; }
    public void setTarjetaCredito(String tarjetaCredito) { this.tarjetaCredito = tarjetaCredito; }
    
    public Date getFechaCaducidad(){ return fechaCaducidad; }
    public void setFechaCaducidad(Date fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }
    
    public double getImportePagado() { return importePagado; }
    public void setImportePagado(double importePagado) { this.importePagado = importePagado; }
    
    public boolean isPagoExitoso() { return pagoExitoso; }
    public void setPagoExitoso(boolean pagoExitoso) { this.pagoExitoso = pagoExitoso; }
    
    // NUEVO MÉTODO: Procesar pago con validación
    public String procesarPago() {
        
        if (fechaCaducidad == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new javax.faces.application.FacesMessage(
                    javax.faces.application.FacesMessage.SEVERITY_ERROR,
                    "Fecha de caducidad requerida",
                    "Por favor, ingrese la fecha de caducidad de su tarjeta"
                ));
            return null;
        }
        
        if (fechaCaducidad.before(new Date())) {
            FacesContext.getCurrentInstance().addMessage(null,
                new javax.faces.application.FacesMessage(
                    javax.faces.application.FacesMessage.SEVERITY_ERROR,
                    "Tarjeta vencida",
                    "La fecha de caducidad de su tarjeta ha expirado"
                ));
            return null;
        }
        
         this.pagoExitoso = true;
         
         
         FacesContext.getCurrentInstance().addMessage(null,
            new javax.faces.application.FacesMessage(
                javax.faces.application.FacesMessage.SEVERITY_INFO,
                "Pago registrado",
                "Importe pagado: " + importePagado + "€. ¡Gracias por su compra!"
            ));
        
         return "facturacion-exitosa?faces-redirect=true";
         
    }
    
    // NUEVO MÉTODO: Para la página de confirmación
    public String getResumenCompra() {
        CarritoBean carritoBean = getCarritoBean();
        return "Compra realizada por " + nombre + " " + apellidos + 
               " - Total: " + carritoBean.getTotal() + "€ - Pagado: " + importePagado + "€";
        
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
    

public String irACategorias() {
    // Limpiar datos de la facturación para nueva compra
    CarritoBean carritoBean = getCarritoBean();
    carritoBean.limpiar();
    
    this.nombre = null;
    this.apellidos = null;
    this.tarjetaCredito = null;
    this.fechaCaducidad = null;
    this.importePagado = 0;
    this.pagoExitoso = false;
    
    return "categorias?faces-redirect=true";
}

public String irABienvenida() {
    // Limpiar datos de la facturación
    CarritoBean carritoBean = getCarritoBean();
    carritoBean.limpiar();
    this.nombre = null;
    this.apellidos = null;
    this.tarjetaCredito = null;
    this.fechaCaducidad = null;
    this.importePagado = 0;
    this.pagoExitoso = false;
    
    return "bienvenida?faces-redirect=true";
}
    
}