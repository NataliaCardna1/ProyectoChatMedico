package modelo;

import java.util.ArrayList;

public class Usuario {
    private String idUsuario;
    private String nombre;
    private TipoUsuario tipoUsuario;
    private ArrayList<Documento> documentos;

    public Usuario(String idUsuario, String nombre, TipoUsuario tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.tipoUsuario = tipoUsuario;
        this.documentos = new ArrayList<Documento>();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public ArrayList<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(ArrayList<Documento> documentos) {
        this.documentos = documentos;
    }

    public void enviarDocumento(Documento documento) {
        documentos.add(documento);
    }

    public void cargarDocumento(Documento documento) {
        documentos.add(documento);
    }
}