package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Usuario implements Serializable {
    private List<Documento> documentos;

    public Paciente(String idUsuario, String nombre) {

        super(idUsuario, nombre);
    }
    public void agregarDocumento(Documento documento) {
        this.documentos.add(documento);
    }

    public void enviarDocumento(Documento documento) {
        //documentos.add(documento);
    }

}
