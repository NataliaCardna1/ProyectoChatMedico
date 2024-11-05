package src.main.java.modelo;

import java.util.HashMap;

public class GestorConsultorio {
    private HashMap<Integer, Usuario> usuarios = new HashMap<>();
    private HashMap<Integer, Documento> documentos = new HashMap<>();
    private HashMap<String, Permiso> permisos = new HashMap<>();

    public void agregarUsuario(Usuario usuario) {
        usuarios.put(usuario.getIdUsuario(), usuario);
    }

    public void agregarDocumento(Documento documento) {
        documentos.put(documento.getIdDocumento(), documento);
    }

    public void agregarPermisos(Permiso permiso) {
        String clavePermiso = permiso.getUsuarioId() + "&" + permiso.getDocumentoId();
        permisos.put(clavePermiso, permiso);
    }

    public boolean tienePermisoImpresion(int usuarioId, int documentoId) {
        String clavePermiso = usuarioId + "&" + documentoId;
        Permiso permiso = permisos.get(clavePermiso);
        return permiso != null && permiso.isPermisoImpresion();
    }

    public boolean tienePermisoEdicion(int usuarioId, int documentoId) {
        String clavePermiso = usuarioId + "&" + documentoId;
        Permiso permiso = permisos.get(clavePermiso);
        return permiso != null && permiso.isPermisoEdicion();

    }
}
