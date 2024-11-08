package sockets;

import modelo.GestorConsultorio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorChat {
    private static final int PUERTO = 12345;
    private ExecutorService pool;
    private GestorConsultorio gestorConsultorio;

    public ServidorChat() {
        pool = Executors.newFixedThreadPool(10);
        gestorConsultorio = new GestorConsultorio();
    }

    public void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            while (true) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + socketCliente.getInetAddress());

                ClientHandler clienteHandler = new ClientHandler(socketCliente, gestorConsultorio);
                pool.execute(clienteHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServidorChat servidor = new ServidorChat();
        servidor.iniciarServidor();
    }
}