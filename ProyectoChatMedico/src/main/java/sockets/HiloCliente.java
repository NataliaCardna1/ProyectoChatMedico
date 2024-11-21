package sockets;
import modelo.TipoConsulta;
import modelo.Consulta;
import modelo.Usuario;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class HiloCliente implements Runnable{

    private Socket socket;
    private Map<String, HiloCliente> usuariosConectados;
    private Map<String, Chat> chatsActivos;
    private Usuario usuario;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public HiloCliente(Socket socket, Map<String, HiloCliente> usuariosConectados, Map<String, Chat> chatsActivos){
        try{
            this.socket = socket;
            this.usuariosConectados = usuariosConectados;
            this.chatsActivos = chatsActivos;
            this.ois = new ObjectInputStream((socket.getInputStream()));
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            String opcion = (String) ois.readObject();

            if ("AGREGAR".equals(opcion)) {
                this.usuario = (Usuario) ois.readObject();
                usuariosConectados.put(this.usuario.getIdUsuario(), this);
                oos.writeObject("Conectado como: " + this.getUsuario().getNombre());
                System.out.println(this.getUsuario().getNombre() + " se conectó.");
            }

            while(true) {

                opcion = (String) ois.readObject();

                switch (opcion) {

                    case "CONSULTA":
                        oos.writeObject(getUsuariosConectados());
                        oos.flush();
                        break;

                    case "INICIAR_CHAT":
                        String destinatarioID = (String) ois.readObject();

                        if (usuariosConectados.containsKey(destinatarioID)) {
                            HiloCliente destinatario = usuariosConectados.get(destinatarioID);
                            Chat nuevoChat = new Chat(this, destinatario);
                            chatsActivos.put(UUID.randomUUID().toString(), nuevoChat);
                            oos.writeObject("Chat iniciado con " + destinatario.getUsuario().getNombre());
                            oos.flush();
                        } else {
                            oos.writeObject("El usuario no está conectado.");
                            oos.flush();
                        }

                        break;

                    case "CHATS":

                        List<String> chats = new ArrayList<>();
                        for (Map.Entry<String, Chat> chat : chatsActivos.entrySet()) {
                            if (chat.getValue().getUsuario1().equals(this) || chat.getValue().getUsuario2().equals(this)) {
                                chats.add(chat.getKey());
                            }
                        }
                        oos.writeObject(chats.toString());
                        oos.flush();
                        break;

                    case "ENVIAR_MENSAJE":
                        String chatID = (String) ois.readObject();
                        Chat chat = chatsActivos.get(chatID);
                        if (chat != null) {
                            while (true) {
                                System.out.println("Preparado para enviar mensajes en el chat: " + chatID);
                                String mensaje = (String) ois.readObject();

                                // Check for exit condition
                                if ("SALIR_CHAT".equals(mensaje)) {
                                    chatsActivos.remove(chatID);
                                    oos.writeObject("Chat terminado.");
                                    break;
                                }

                                // Use the Chat class's enviarMensaje method
                                chat.enviarMensaje(mensaje, this.socket);
                            }
                        } else {
                            oos.writeObject("Chat no encontrado.");
                            oos.flush();
                        }

                        break;

                    case "SALIR":
                        usuariosConectados.remove(this.usuario.getIdUsuario());
                        System.out.println(this.usuario.getNombre() + " se ha desconectado.");
                        break;
                    case "SOLICITAR CONSULTA":
                        String tipoConsulta = (String) ois.readObject();  // El tipo de consulta que se solicita
                        Object parametros = ois.readObject();  // Los parámetros necesarios para la consulta (si los hay)
                        solicitarConsulta(tipoConsulta, parametros);  // Llamada al método que maneja la consulta
                        break;

                    default:
                        oos.writeObject("Comando no válido.");
                        break;

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getUsuariosConectados(){
        List<String> usuarios = new ArrayList<>();

        for(HiloCliente usuario : usuariosConectados.values()){
            usuarios.add(usuario.getUsuario().getIdUsuario()+" "+usuario.getUsuario().getNombre());
        }

        return usuarios;
    }

    public Socket getSocket() {
        return socket;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }
    public void solicitarConsulta(String tipoConsulta, Object parametros) {
        try {
            TipoConsulta consulta = TipoConsulta.valueOf(tipoConsulta.toUpperCase());

            realizarConsulta(consulta);
        } catch (IllegalArgumentException e) {
            enviarMensaje("Tipo de consulta no reconocido");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void realizarConsulta(TipoConsulta consulta) throws IOException, ClassNotFoundException {
        enviarMensaje("Iniciando consulta " + consulta.name().toLowerCase());

        procesarConsulta(consulta.getPreguntas());
    }

    private void procesarConsulta(List<String> preguntas) throws IOException, ClassNotFoundException {
        List<String> respuestas = new ArrayList<>();

        for (String pregunta : preguntas) {
            enviarMensaje(pregunta);

            String respuesta = (String) ois.readObject();
            if (respuesta == null || respuesta.trim().isEmpty()) {
                respuesta = "No proporcionada";
            }
            respuestas.add(respuesta);
        }


        guardarConsultaEnArchivo(respuestas,preguntas);
        enviarMensaje("Consulta finalizada");
    }

    private void guardarConsultaEnArchivo( List<String> respuestas,List<String> preguntas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("consultas.txt", true))) {
            writer.write("--- Consulta " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ---\n");
            //writer.write("Tipo de Consulta: " + tipoConsulta + "\n");

            for (int i = 0; i < respuestas.size(); i++) {
                writer.write("Pregunta " + (i + 1) + ": " + preguntas.get(i) + "\n");
                writer.write("Respuesta " + (i + 1) + ": " + respuestas.get(i) + "\n");
            }

            writer.write("---------------------\n\n");
            System.out.println("Consulta guardada exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarMensaje(Object mensaje) {
        try {
            oos.writeObject(mensaje);
            oos.flush();
        } catch (IOException e) {
            System.err.println("Error al enviar mensaje: " + mensaje);
            e.printStackTrace();
        }
    }

}
