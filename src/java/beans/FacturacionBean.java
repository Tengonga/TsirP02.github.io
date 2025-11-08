package beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;

@ManagedBean
@SessionScoped
public class FacturacionBean implements Serializable {
    private String nombre = "";
    private String apellidos = "";
    private String tarjetaCredito = "";
    private Date fechaCaducidad;
    private double importePagado = 0.0;
    private boolean pagoExitoso = false;
    
    @ManagedProperty(value = "#{carritoBean}")
    private CarritoBean carritoBean;
    
    @ManagedProperty(value = "#{idiomaBean}")
    private IdiomaBean idiomaBean;
    
    @PostConstruct
    public void init() {
        // Inicialización si es necesaria
    }
    
    public void setCarritoBean(CarritoBean carritoBean) {
        this.carritoBean = carritoBean;
    }
    
    public void setIdiomaBean(IdiomaBean idiomaBean) {
        this.idiomaBean = idiomaBean;
    }
    
    // Getters y Setters
    public String getNombre() { 
        return nombre != null ? nombre : ""; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre != null ? nombre : ""; 
    }
    
    public String getApellidos() { 
        return apellidos != null ? apellidos : ""; 
    }
    
    public void setApellidos(String apellidos) { 
        this.apellidos = apellidos != null ? apellidos : ""; 
    }
    
    public String getTarjetaCredito() { 
        return tarjetaCredito != null ? tarjetaCredito : ""; 
    }
    
    public void setTarjetaCredito(String tarjetaCredito) { 
        this.tarjetaCredito = tarjetaCredito != null ? tarjetaCredito : ""; 
    }
    
    public Date getFechaCaducidad(){ 
        return fechaCaducidad; 
    }
    
    public void setFechaCaducidad(Date fechaCaducidad) { 
        this.fechaCaducidad = fechaCaducidad; 
    }
    
    public double getImportePagado() { 
        return importePagado; 
    }
    
    public void setImportePagado(double importePagado) { 
        this.importePagado = importePagado; 
    }
    
    public boolean isPagoExitoso() { 
        return pagoExitoso; 
    }
    
    public void setPagoExitoso(boolean pagoExitoso) { 
        this.pagoExitoso = pagoExitoso; 
    }
    
    // ✅ VERSIÓN MEJORADA DEL LOGOUT - ESTA SÍ FUNCIONA
    public String logout() {
        try {
            // 1. Hacer logout de GlassFish Security
            HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();
            request.logout();
            
            // 2. Invalidar la sesión JSF
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            
            // 3. Limpiar datos del bean
            limpiarDatosCompletos();
            
            // 4. Mensaje de confirmación
            FacesContext.getCurrentInstance().addMessage(null,
                new javax.faces.application.FacesMessage(
                    javax.faces.application.FacesMessage.SEVERITY_INFO,
                    "Sesión cerrada",
                    "Has cerrado sesión correctamente"
                ));
            
            return "/bienvenida?faces-redirect=true";
            
        } catch (Exception e) {
            e.printStackTrace();
            // Si falla, al menos invalidar la sesión
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "/bienvenida?faces-redirect=true";
        }
    }
    
    // ✅ MÉTODO AUXILIAR: Para verificar si hay usuario autenticado
    public boolean isUsuarioAutenticado() {
        try {
            return FacesContext.getCurrentInstance()
                .getExternalContext().getUserPrincipal() != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getUltimosCuatroDigitos() {
        if (tarjetaCredito == null || tarjetaCredito.length() < 4) {
            return "****";
        }
        return tarjetaCredito.substring(tarjetaCredito.length() - 4);
    }
    
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
        return "facturacion-exitosa?faces-redirect=true";
    }
    
    public String irACategorias() {
        limpiarDatos();
        return "categorias?faces-redirect=true";
    }
    
    public String irABienvenida() {
        limpiarDatos();
        return "bienvenida?faces-redirect=true";
    }
    
    private void limpiarDatos() {
        this.tarjetaCredito = "";
        this.fechaCaducidad = null;
        this.importePagado = 0.0;
        this.pagoExitoso = false;
    }
    
    private void limpiarDatosCompletos() {
        limpiarDatos();
        if (carritoBean != null) {
            carritoBean.limpiar();
        }
        this.nombre = "";
        this.apellidos = "";
    }
}