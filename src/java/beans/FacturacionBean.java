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
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String procesarFacturacion() {
        // Lógica de facturación aquí
        return "facturacion-exitosa?faces-redirect=true";
    }
    
   /*public String logout() {
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    return "/bienvenida.xhtml?faces-redirect=true";
   }
   */
    
   public String logout() {
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    return "/bienvenida?faces-redirect=true";
    }
}