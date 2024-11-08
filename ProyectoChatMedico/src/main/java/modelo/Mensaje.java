package modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Mensaje implements Serializable {
    private Usuario remitente;
    private Usuario destinatario;
    private String contenido;
    private LocalDateTime timestamp;

    public Mensaje(Usuario remitente, Usuario destinatario, String contenido) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.contenido = contenido;
        this.timestamp = LocalDateTime.now();
    }

    public Mensaje(Usuario destinatario, String contenido) {
        this.destinatario = destinatario;
        this.contenido = contenido;
    }

    // Getters y setters

    public Usuario getRemitente() {
        return remitente;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
