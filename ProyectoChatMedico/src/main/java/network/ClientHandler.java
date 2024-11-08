package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ChatServer servidor;
    private PrintWriter out;
    private BufferedReader in;
    private String userId;

    public ClientHandler(Socket socket, ChatServer servidor) {
        this.clientSocket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            autenticarUsuario();

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                procesarMensaje(inputLine);
            }
        } catch (IOException e) {
            System.err.println("Error en el manejador de cliente: " + e.getMessage());
        } finally {
            cerrarConexion();
        }
    }

    private void autenticarUsuario() throws IOException {
        // Primera línea debe ser el ID de usuario
        userId = in.readLine();
        if (userId != null) {
            servidor.registrarCliente(userId, this);
            out.println("AUTH_OK");
        } else {
            out.println("AUTH_ERROR");
            throw new IOException("Autenticación fallida");
        }
    }

    private void procesarMensaje(String mensaje) {
        // Formato esperado: DESTINO|MENSAJE
        String[] partes = mensaje.split("\\|", 2);
        if (partes.length == 2) {
            String destinatario = partes[0];
            String contenido = partes[1];
            servidor.enviarMensaje(destinatario, contenido);
        }
    }

    public void enviarMensaje(String mensaje) {
        out.println(mensaje);
    }

    private void cerrarConexion() {
        try {
            servidor.eliminarCliente(userId);
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
