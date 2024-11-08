package modelo;

import java.util.ArrayList;

public class Usuario {
    private String idUsuario;
    private String nombre;
    private ArrayList<Documento> documentos;
    private TipoUsuario tipoUsuario;


    public Usuario(String idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
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


    public ArrayList<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(ArrayList<Documento> documentos) {
        this.documentos = documentos;
    }


    public void enviarDocumento(Documento documento) {
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void cargarDocumento(Documento documento) {

    }
}