package sockets;

import modelo.Chat;
import modelo.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HiloCliente implements Runnable{

    private Socket socket;
    private Map<String, HiloCliente> usuarioSocket;
    private Usuario usuario;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public HiloCliente(Socket socket, Map<String, HiloCliente> usuarioSocket){
        this.socket = socket;
        this.usuarioSocket = usuarioSocket;
        try{
            ois = new ObjectInputStream((socket.getInputStream()));
            oos = new ObjectOutputStream(socket.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            boolean ejecucion = true;

            while(ejecucion) {

                String opcion = ois.readObject().toString();
                System.out.println(opcion);
                if (opcion.equals("AGREGAR")) {
                    this.usuario = (Usuario) ois.readObject();
                    usuarioSocket.put(this.usuario.getIdUsuario(), this);
                } else if (opcion.equals("CONSULTA")) {
                    oos.writeObject(getUsuariosConectados());
                } else if (opcion.equals("CHAT")) {

                    String destinatarioID = ois.readObject().toString(); //Cuando un cliente selecciona la opción de chatear, envía el ID del destinatario y el mensaje al servidor. El servidor recibe estos datos, lo que permite identificar al destinatario y saber qué mensaje enviarle.
                    System.out.println(destinatarioID);
                    final HiloCliente destinatarioSocket = usuarioSocket.get(destinatarioID);
                    if (destinatarioSocket != null) {
                        destinatarioSocket.getOos().writeObject("CHAT_INICIADO");
                        new HiloChat(socket, destinatarioSocket.getSocket()).start();
                        break;
                    }
                } else if(opcion.equals("SALIR")){
                    ejecucion = false;
                    usuarioSocket.remove(this.usuario.getIdUsuario());
                    socket.close();
                }else{
                    System.out.println(ois.readObject());
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Map<String, HiloCliente> getUsuarioSocket() {
        return usuarioSocket;
    }

    public void setUsuarioSocket(Map<String, HiloCliente> usuarioSocket) {
        this.usuarioSocket = usuarioSocket;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public List<String> getUsuariosConectados(){
        List<String> usuarios = new ArrayList<>();

        for(HiloCliente usuario : usuarioSocket.values()){
            usuarios.add(usuario.getUsuario().getIdUsuario()+" "+usuario.getUsuario().getNombre());
        }

        return usuarios;
    }

}
