package modelo;

import java.time.LocalDate;

public record Documento(String idDocumento,
                        String nombre,
                        Usuario propietario,
                        String ruta,
                        TipoDocumento tipoDocumento) {

}




