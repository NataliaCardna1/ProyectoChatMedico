package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Paciente extends Usuario implements Serializable{


    public Paciente(String idUsuario, String nombre) {
        super(idUsuario, nombre);
    }


}
