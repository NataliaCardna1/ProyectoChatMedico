package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private final String serverHost;
    private final int serverPort;
    private final String userId;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private final MessageListener messageListener;
    private volatile boolean conectado;

    public ChatClient(String serverHost, int serverPort, String userId, MessageListener listener) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.userId = userId;
        this.messageListener = listener;
        this.conectado = false;
    }

    public void conectar() throws IOException {
        socket = new Socket(serverHost, serverPort);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Enviar ID de usuario para autenticación
        out.println(userId);
        String response = in.readLine();

        if ("AUTH_OK".equals(response)) {
            conectado = true;
            // Iniciar hilo de escucha
            new Thread(this::escucharMensajes).start();
        } else {
            throw new IOException("Error de autenticación");
        }
    }

    private void escucharMensajes() {
        try {
            String mensajeEntrante;
            while (conectado && (mensajeEntrante = in.readLine()) != null) {
                messageListener.onMessageReceived(mensajeEntrante);
            }
        } catch (IOException e) {
            System.err.println("Error al recibir mensajes: " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    public void enviarMensaje(String destinatarioId, String mensaje) throws IOException {
        if (conectado) {
            out.println(destinatarioId + "|" + mensaje);
        }
        else{
            conectar();
        }
    }

    public void desconectar() {
        conectado = false;
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error al desconectar: " + e.getMessage());
        }
    }
}

interface MessageListener {
    void onMessageReceived(String mensaje);
}

