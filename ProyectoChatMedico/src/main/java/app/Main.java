package app;
import modelo.*;


public class Main {
    public static void main(String[] args) {
        var usuario = new Usuario("U1", "Julian", TipoUsuario.NATURAL);
        var usuario2 = new Usuario("U2", "Natalia", TipoUsuario.NATURAL);
        var medico = new Usuario("U1", "Julian", TipoUsuario.MEDICO);
        var documento = new Documento("1", "His", usuario, "C:\\midocumento.docx", TipoDocumento.RADIOGRAFIAS);
        var documento2 = new Documento("2", "Receta", usuario2, "C:\\mireceta.docx", TipoDocumento.RECETAS_MEDICAS);
        var gestorConsultario = new GestorConsultorio();

        try {
            gestorConsultario.cargarDocumento(usuario, documento, medico);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            gestorConsultario.enviarDocumento(medico, documento2, usuario2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        for (Documento documentoUsuario : usuario2.getDocumentos() ) {
            System.out.println(documentoUsuario.nombre());
        }


    }

}
