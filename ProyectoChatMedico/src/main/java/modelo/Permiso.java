package src.main.java.modelo;

public class Permiso {
    private int idPermiso;
    private int usuarioId;
    private int documentoId;
    private boolean permisoImpresion;
    private boolean permisoEdicion;

    public Permiso(int idPermiso, int usuarioId, int documentoId, boolean permisoImpresion, boolean permisoEdicion) {
        this.idPermiso = idPermiso;
        this.usuarioId = usuarioId;
        this.documentoId = documentoId;
        this.permisoImpresion = permisoImpresion;
        this.permisoEdicion = permisoEdicion;
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(int documentoId) {
        this.documentoId = documentoId;
    }

    public boolean isPermisoImpresion() {
        return permisoImpresion;
    }

    public void setPermisoImpresion(boolean permisoImpresion) {
        this.permisoImpresion = permisoImpresion;
    }

    public boolean isPermisoEdicion() {
        return permisoEdicion;
    }

    public void setPermisoEdicion(boolean permisoEdicion) {
        this.permisoEdicion = permisoEdicion;
    }
}
