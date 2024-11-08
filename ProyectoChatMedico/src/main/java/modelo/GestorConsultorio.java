package modelo;
import java.util.HashMap;

public class GestorConsultorio {
    private HashMap <String, Usuario> usuarios;
    private HashMap <String, Documento> documentos;

    public GestorConsultorio() {
        usuarios = new HashMap<>();
        documentos = new HashMap<>();
    }


    public void adicionarUsuario(Usuario usuario) {
        usuarios.put(usuario.getIdUsuario(), usuario);
    }

    public void adicionarDocumentos(Documento documento) {
        documentos.put(documento.idDocumento(), documento);
    }


    public void enviarDocumento(Medico usuarioOrigen, Documento documento, Usuario usuarioDestino) throws Exception {
        if (usuarioOrigen != null) {
            throw new Exception("Solo los pacientes pueden enviar documentos.");
        } else if (documento.tipoDocumento() == TipoDocumento.HISTORIAS_CLINICAS ||
                documento.tipoDocumento() == TipoDocumento.RECETAS_MEDICAS ||
                documento.tipoDocumento() == TipoDocumento.OTROS) {
                     adicionarDocumentos(documento);
                     usuarioOrigen.enviarDocumento(documento);  // Aquí asumo que 'enviarDocumento' era un error y debería ser 'recibirDocumento'
        } else {
            throw new Exception("El tipo de documento no es válido para el paciente.");
        }
        }

    public void cargarDocumento (Usuario usuarioOrigen, Documento documento, Usuario usuarioDestino) throws Exception{
        if(usuarioOrigen.getTipoUsuario()== TipoUsuario.PACIENTE){
            if(documento.tipoDocumento()== TipoDocumento.RADIOGRAFIAS || documento.tipoDocumento()== TipoDocumento.INFORME_LABORATORIO){
                adicionarDocumentos(documento);
                usuarioOrigen.cargarDocumento(documento);
            }else{
                throw new Exception("El paciente no puede cargar ese tipo de documento");
            }

        }
    }





}