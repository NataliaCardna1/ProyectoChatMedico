package modelo;

import java.io.Serializable;
import java.util.List;

public class Chat implements Serializable {

    private Medico medico;
    private Paciente paciente;
    private List<Documento> documentos;
    private List<Mensaje> mensajes;
}
