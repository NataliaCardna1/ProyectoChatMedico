package modelo;

import java.io.Serializable;

public class Documento implements Serializable {
    private String idDocumento;
    private String nombre;
    private Usuario propietario;
    private String ruta;
    private TipoDocumento tipoDocumento;

    public Documento(String idDocumento, String nombre, Usuario propietario, String ruta, TipoDocumento tipoDocumento) {
        this.idDocumento = idDocumento;
        this.nombre = nombre;
        this.propietario = propietario;
        this.ruta = ruta;
        this.tipoDocumento = tipoDocumento;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}




