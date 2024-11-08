package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Medico extends Usuario implements Serializable {

    public Medico(String idUsuario, String nombre) {
        super(idUsuario, nombre);
    }


}
