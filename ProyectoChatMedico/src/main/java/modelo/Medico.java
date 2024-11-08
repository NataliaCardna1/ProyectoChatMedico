package modelo;

import java.util.ArrayList;

public class Medico extends Usuario{
private String codigoUser ;
private String nombre;
private ArrayList<Documento> documentosM;

    public Medico(String idUsuario, String nombre) {
        super(idUsuario, nombre);
    }

    public void cargarDocumento(Documento documento) {
        documentosM.add(documento);
    }
}
