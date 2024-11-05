package src.main.java.modelo;

import java.time.LocalDate;

public class Documento {
    private int idDocumento;
    private String nombreArchivo;
    private String tipoDocumento;
    private LocalDate fechaSubida;
    private int subidoPor;
    private int pacienteId;
    private String rutaArchivo;

    public Documento(int idDocumento, String nombreArchivo, String tipoDocumento, LocalDate fechaSubida, int subidoPor, int pacienteId, String rutaArchivo) {
        this.idDocumento = idDocumento;
        this.nombreArchivo = nombreArchivo;
        this.tipoDocumento = tipoDocumento;
        this.fechaSubida = fechaSubida;
        this.subidoPor = subidoPor;
        this.pacienteId = pacienteId;
        this.rutaArchivo = rutaArchivo;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public LocalDate getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDate fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public int getSubidoPor() {
        return subidoPor;
    }

    public void setSubidoPor(int subidoPor) {
        this.subidoPor = subidoPor;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
}
