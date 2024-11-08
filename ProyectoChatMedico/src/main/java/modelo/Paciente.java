package modelo;

import java.util.ArrayList;

public class Paciente extends Usuario{

    private ArrayList<Documento> documentos;

    public Paciente(String idUsuario, String nombre, ArrayList<Documento> documentos) {
        super(idUsuario, nombre);
        this.documentos = documentos;
    }

    public void enviarDocumento(Documento documento) {
        documentos.add(documento);
    }
}
