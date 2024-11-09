package sockets;

import modelo.Usuario;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HiloCliente implements Runnable{

    private Socket socket;
    private Map<Socket, Usuario> conexionesCliente;

    public HiloCliente(Socket socket, Map<Socket, Usuario> conexionesCliente){
        this.socket = socket;
        this.conexionesCliente = conexionesCliente;
    }

    @Override
    public void run() {
        try {

            ObjectInputStream ois = new ObjectInputStream((socket.getInputStream()));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            Usuario usuarioConectado = (Usuario) ois.readObject();
            conexionesCliente.put(socket, usuarioConectado);

            boolean ejecucion = true;

            while(ejecucion) {

                String opcion = ois.readObject().toString();

                if(opcion.equals("CONSULTA")){
                    oos.writeObject(getUsuariosConectados());
                }else if(opcion.equals("CHAT")){
                    //Se debe instanciar el CHat con los dos usuarios
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
