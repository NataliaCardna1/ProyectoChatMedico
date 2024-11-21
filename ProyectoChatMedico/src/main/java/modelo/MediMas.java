package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MediMas implements Serializable {
    private static final long serialVersionUID = 1L; // Para control de versiones en serialización

    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Documento> documentos;
    private List<Mensaje> mensajes;

    // Constructor por defecto
    public MediMas() {
        this.medicos = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.documentos = new ArrayList<>();
        this.mensajes = new ArrayList<>();
    }

    // Métodos para manejar médicos
    public void agregarMedico(Medico medico) {
        medicos.add(medico);
    }

    public void eliminarMedico(Medico medico) {
        medicos.remove(medico);
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }

    // Métodos para manejar pacientes
    public void agregarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public void eliminarPaciente(Paciente paciente) {
        pacientes.remove(paciente);
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    // Métodos para manejar documentos
    public void agregarDocumento(Documento documento) {
        documentos.add(documento);
    }

    public void eliminarDocumento(Documento documento) {
        documentos.remove(documento);
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    // Métodos para manejar mensajes
    public void agregarMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
    }

    public void eliminarMensaje(Mensaje mensaje) {
        mensajes.remove(mensaje);
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    @Override
    public String toString() {
        return "MediMas{" +
                "medicos=" + medicos +
                ", pacientes=" + pacientes +
                ", documentos=" + documentos +
                ", mensajes=" + mensajes +
                '}';
    }
}


