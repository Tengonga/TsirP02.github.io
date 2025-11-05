
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class FacturacionBean {
    private String mensaje = "Facturación completada exitosamente";
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String procesarFacturacion() {
        // Lógica de facturación aquí
        return "facturacion-exitosa";
    }
}