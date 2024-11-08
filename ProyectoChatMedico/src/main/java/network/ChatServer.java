package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private final int puerto;
    private ServerSocket serverSocket;
    private final ExecutorService poolConexiones;
    private final ConcurrentHashMap<String, ClientHandler> clientesConectados;
    private volatile boolean ejecutando;

    public ChatServer(int puerto) {
        this.puerto = puerto;
        this.poolConexiones = Executors.newCachedThreadPool();
        this.clientesConectados = new ConcurrentHashMap<>();
        this.ejecutando = false;
    }

    public void iniciar() {
        try {
            serverSocket = new ServerSocket(puerto);
            ejecutando = true;
            System.out.println("Servidor iniciado en puerto " + puerto);

            while (ejecutando) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nueva conexión aceptada: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                poolConexiones.execute(clientHandler);
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        } finally {
            detener();
        }
    }

    public void detener() {
        ejecutando = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error al cerrar el servidor: " + e.getMessage());
        }
        poolConexiones.shutdown();
    }

    public void registrarCliente(String userId, ClientHandler handler) {
        clientesConectados.put(userId, handler);
    }

    public void eliminarCliente(String userId) {
        clientesConectados.remove(userId);
    }

    public void enviarMensaje(String destinatarioId, String mensaje) {
        ClientHandler handler = clientesConectados.get(destinatarioId);
        if (handler != null) {
            handler.enviarMensaje(mensaje);
        }
    }
}
