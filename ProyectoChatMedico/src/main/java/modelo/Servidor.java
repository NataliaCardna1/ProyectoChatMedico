package modelo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Servidor {
    private static final int PORT = 12345;
    private static Map<Socket, Usuario> conexionesCliente = new ConcurrentHashMap<>();

    public static void iniciarServidor(){
        try (ServerSocket socketServidor = new ServerSocket(PORT)){
            System.out.println("El servidor esta escuchando en el puerto" + PORT);

            while (true){
                Socket clienteSocket = socketServidor.accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
