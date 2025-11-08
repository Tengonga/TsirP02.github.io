package beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
    private boolean sesionIniciada = false;
    
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
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.sesionIniciada = true;
        }
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
    
    public boolean isSesionIniciada() {
        return sesionIniciada;
    }
    
    public void setSesionIniciada(boolean sesionIniciada) {
        this.sesionIniciada = sesionIniciada;
    }
    
    public boolean isSesionActiva() {
        return sesionIniciada && nombre != null && !nombre.trim().isEmpty();
    }
    
    // Método para login
    public String login() {
        this.sesionIniciada = true;
        if (nombre == null || nombre.trim().isEmpty()) {
            this.nombre = "Cliente Demo";
        }
        
        FacesContext.getCurrentInstance().addMessage(null,
            new javax.faces.application.FacesMessage(
                javax.faces.application.FacesMessage.SEVERITY_INFO,
                "Sesión iniciada",
                "Bienvenido, " + nombre
            ));
        
        return "categorias?faces-redirect=true";
    }
    
    // MÉTODO LOGOUT CORREGIDO (SOLO UNO)
    public String logout() {
        this.sesionIniciada = false;
        this.nombre = "";
        this.apellidos = "";
        this.tarjetaCredito = "";
        this.fechaCaducidad = null;
        this.importePagado = 0.0;
        this.pagoExitoso = false;
        
        // Limpiar carrito también
        if (carritoBean != null) {
            carritoBean.limpiar();
        }
        
        FacesContext.getCurrentInstance().addMessage(null,
            new javax.faces.application.FacesMessage(
                javax.faces.application.FacesMessage.SEVERITY_INFO,
                "Sesión cerrada",
                "Has cerrado sesión correctamente"
            ));
        
        // NO invalidar la sesión aquí, solo limpiar los datos
        return "/bienvenida?faces-redirect=true";
    }
    
    // Cálculos de pago CORREGIDOS
    public double getDiferenciaPago() {
        if (carritoBean == null || carritoBean.getTotalConvertido() == 0) {
            return importePagado;
        }
        double total = carritoBean.getTotalConvertido();
        return importePagado - total;
    }
    
    public String getMensajeDiferenciaPago() {
        double diferencia = getDiferenciaPago();
        String simbolo = carritoBean != null ? carritoBean.getSimboloMoneda() : "€";
        
        if (diferencia > 0) {
            return String.format("+%.2f%s (Cambio a devolver)", diferencia, simbolo);
        } else if (diferencia < 0) {
            return String.format("%.2f%s (Falta por pagar)", diferencia, simbolo);
        } else {
            return String.format("0%s (Pago exacto)", simbolo);
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
        if (carritoBean != null) {
            carritoBean.limpiar();
        }
        this.tarjetaCredito = "";
        this.fechaCaducidad = null;
        this.importePagado = 0.0;
        this.pagoExitoso = false;
    }
}