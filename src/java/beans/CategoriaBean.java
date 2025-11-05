package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CategoriaBean {
    // Agrega al menos algún método básico
    public String getMensaje() {
        return "Gestión de categorías";
    }
}