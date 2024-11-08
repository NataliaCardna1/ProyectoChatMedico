package modelo;
import java.io.Serializable;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorConsultorio implements Serializable {
    private Map<Usuario, List<Mensaje>> mensajes = new HashMap<>();
    private Map<Usuario, List<Documento>> documentos = new HashMap<>();

    public synchronized void enviarMensaje(Usuario remitente, Usuario destinatario, String contenido) {
        Mensaje mensaje = new Mensaje(remitente, destinatario, contenido);
        mensajes.computeIfAbsent(destinatario, k -> new ArrayList<>()).add(mensaje);
        mensajes.computeIfAbsent(remitente, k -> new ArrayList<>()).add(mensaje);
    }

    public synchronized void enviarDocumento(Paciente paciente, Medico medico, Documento documento) {
        documentos.computeIfAbsent(medico, k -> new ArrayList<>()).add(documento);
        documentos.computeIfAbsent(paciente, k -> new ArrayList<>()).add(documento);
    }

    public List<Mensaje> obtenerHistorialMensajes(Usuario usuario) {
        return mensajes.getOrDefault(usuario, new ArrayList<>());
    }

    public List<Documento> obtenerDocumentos(Usuario usuario) {
        return documentos.getOrDefault(usuario, new ArrayList<>());
    }
}