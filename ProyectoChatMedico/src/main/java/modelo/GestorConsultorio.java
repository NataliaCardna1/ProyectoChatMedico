package modelo;
import java.util.HashMap;

public class GestorConsultorio {
    private HashMap <String, Usuario> usuarios;
    private HashMap <String, Documento> documentos;

    public GestorConsultoriorio() {
        usuarios = new HashMap<>();
        documentos = new HashMap<>();
    }

    public void adionarUsuario(Usuario usuario) {
        usuarios.put(usuario.getIdUsuario(), usuario);
    }

    public void adicionarDocumentos(Documento documento) {
        documentos.put(documento.idDocumento(), documento);
    }


    public void enviarDocumento(Usuario usuarioOrigen, Documento documento, Usuario usuarioDestino) throws Exception {
        if (usuarioOrigen.getTipoUsuario() == TipoUsuario.MEDICO ) {
            if (documento.tipoDocumento() == TipoDocumento.HISOTRIAS_CLINICAS ||
                    documento.tipoDocumento() == TipoDocumento.RECETAS_MEDICAS ||
                    documento.tipoDocumento() == TipoDocumento.OTROS) {
                adicionarDocumentos(documento);
                usuarioDestino.enviarDocumento(documento);
            } else {
                throw new Exception("El medico no puede enviar ese tipo de documento");
            }
        }
    }
    public void cargarDocumento (Usuario usuarioOrigen, Documento documento, Usuario usuarioDestino) throws Exception{
        if(usuarioOrigen.getTipoUsuario()== TipoUsuario.NATURAL){
            if(documento.tipoDocumento()== TipoDocumento.RADIOGRAFIAS || documento.tipoDocumento()== TipoDocumento.INFORME_LABORATORIO){
                adicionarDocumentos(documento);
                usuarioOrigen.cargarDocumento(documento);
            }else{
                throw new Exception("El paciente no puede cargar ese tipo de documento");
            }

        }
    }





}