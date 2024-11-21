package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Medico extends Usuario implements Serializable {

    private ArrayList<Documento> documentosM;



    public Medico(String idUsuario, String nombre) {
        super(idUsuario, nombre);
    }


    public void cargarDocumento(Documento documento) {
        documentosM.add(documento);
    }
}
