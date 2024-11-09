package sockets;

import modelo.Usuario;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Servidor {
    private static final int PORT = 12345;
    private Map<Socket, Usuario> conexionesCliente = new ConcurrentHashMap<>();

    public void iniciarServidor(){
        try (ServerSocket socketServidor = new ServerSocket(PORT)){
            System.out.println("El servidor esta escuchando en el puerto" + PORT);

            while (true){
                Socket clienteSocket = socketServidor.accept();
                new Thread(new HiloCliente(clienteSocket, conexionesCliente)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Servidor().iniciarServidor();
    }
}