package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Locale;

@ManagedBean
@SessionScoped
public class IdiomaBean implements Serializable {
    
    private Locale locale = new Locale("es");
    
    public Locale getLocale() {
        return locale;
    }
    
    public String getIdioma() {
        return locale.getLanguage();
    }
    
    public void setIdioma(String idioma) {
        this.locale = new Locale(idioma);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
    
    public void cambiarIdioma(String idioma) {
        setIdioma(idioma);
    }
}