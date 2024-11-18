package sockets;

import modelo.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HiloCliente implements Runnable{

    private Socket socket;
    private Map<Socket, Usuario> conexionesCliente;
    private Map<String, Socket> usuarioSocket;


    public HiloCliente(Socket socket, Map<Socket, Usuario> conexionesCliente, Map<String, Socket> usuarioSocket){
        this.socket = socket;
        this.conexionesCliente = conexionesCliente;
        this.usuarioSocket = usuarioSocket;
    }

    @Override
    public void run() {
        try {

            ObjectInputStream ois = new ObjectInputStream((socket.getInputStream()));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            Usuario usuarioConectado = (Usuario) ois.readObject();
            conexionesCliente.put(socket, usuarioConectado);
            usuarioSocket.put(usuarioConectado.getIdUsuario(), socket);

            boolean ejecucion = true;

            while(ejecucion) {

                String opcion = ois.readObject().toString();
                System.out.println(opcion);
                if(opcion.equals("CONSULTA")){
                    oos.writeObject(getUsuariosConectados());
                }else if(opcion.equals("CHAT")){
                    String destinatarioID = ois.readObject().toString();
                    String mensaje = ois.readObject().toString(); //Cuando un cliente selecciona la opción de chatear, envía el ID del destinatario y el mensaje al servidor. El servidor recibe estos datos, lo que permite identificar al destinatario y saber qué mensaje enviarle.
                    System.out.println(destinatarioID + mensaje);
                    final Socket destinatarioSocket = usuarioSocket.get(destinatarioID);
                    if (destinatarioSocket != null){
                        System.out.println(destinatarioSocket.getOutputStream());
                        new Thread(()->{
                            ObjectOutputStream destinatrioOOS = null;
                            try {
                                destinatrioOOS = new ObjectOutputStream(destinatarioSocket.getOutputStream());
                                destinatrioOOS.writeObject("Mensaje de" + usuarioConectado.getNombre() + ":" + mensaje);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
                    }
                    else {
                        oos.writeObject("El usuario no está conectado.");
                    }
                }else{
                    ejecucion = false;
                    conexionesCliente.remove(socket);
                    socket.close();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getUsuariosConectados(){
        List<String> usuarios = new ArrayList<>();

        for(Usuario usuario : conexionesCliente.values()){
            usuarios.add(usuario.getNombre());
        }

        return usuarios;
    }

}
