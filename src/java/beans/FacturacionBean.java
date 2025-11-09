package beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;  // ✅ CORRECTO
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletException;

@ManagedBean
@SessionScoped
public class FacturacionBean implements Serializable {
    
    // ✅ Declarar el logger correctamente
    private static final Logger logger = Logger.getLogger(FacturacionBean.class.getName());
    
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
    
    public String logout() {
        try {
            HttpServletRequest request = (HttpServletRequest) 
                FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
            request.logout();
            
            FacesContext.getCurrentInstance()
                .getExternalContext().invalidateSession();
            
            limpiarDatosCompletos();
            
         
            FacesContext.getCurrentInstance()
                .getExternalContext().getFlash()
                .put("logoutSuccess", true);
            
            logger.log(Level.INFO, "Usuario cerró sesión correctamente"); // ✅ CORRECTO
            
            return "/bienvenida?faces-redirect=true";
        } catch (ServletException e) {
         
            logger.log(Level.SEVERE, "Error durante logout", e);
            FacesContext.getCurrentInstance()
                .getExternalContext().invalidateSession();
            return "/bienvenida?faces-redirect=true";
        }
    }
    
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
                new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Fecha de caducidad requerida",
                    "Por favor, ingrese la fecha de caducidad de su tarjeta"
                ));
            return null;
        }
        
        if (fechaCaducidad.before(new Date())) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Tarjeta vencida",
                    "La fecha de caducidad de su tarjeta ha expirado"
                ));
            return null;
        }
        
        this.pagoExitoso = true;
        logger.log(Level.INFO, "Pago procesado para: {0} {1}", 
                   new Object[]{nombre, apellidos});
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
        carritoBean.limpiar();
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
    
    public void validarFechaCaducidad(FacesContext context, 
                                      UIComponent component, 
                                      Object value) {
        if (value == null) {
            throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Fecha requerida", 
                    "La fecha de caducidad es obligatoria"));
        }
        
        Date fecha = (Date) value;
        Calendar hoy = Calendar.getInstance();
        Calendar caducidad = Calendar.getInstance();
        caducidad.setTime(fecha);
        
        // Comparar solo año y mes
        if (caducidad.get(Calendar.YEAR) < hoy.get(Calendar.YEAR) ||
            (caducidad.get(Calendar.YEAR) == hoy.get(Calendar.YEAR) &&
             caducidad.get(Calendar.MONTH) < hoy.get(Calendar.MONTH))) {
            
            logger.log(Level.WARNING, "Intento de usar tarjeta vencida: {0}/{1}", 
                       new Object[]{caducidad.get(Calendar.MONTH) + 1, 
                                    caducidad.get(Calendar.YEAR)});
            
            throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Tarjeta vencida", 
                    "La fecha de caducidad ha expirado"));
        }
    }
    
    // En FacturacionBean.java

    public double getImportePagadoConvertido() {
        if (carritoBean.isIdiomaIngles() && carritoBean != null) {
            return importePagado * 1.10; // Convertir a dólares
        }
        return importePagado; // Mantener en euros
    }

    private boolean isIdiomaIngles() {
        return idiomaBean != null && "en".equals(idiomaBean.getIdioma());
}
}