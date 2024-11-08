package sockets;

import modelo.*;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private GestorConsultorio gestorConsultorio;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Usuario usuario;

    public ClientHandler(Socket socket, GestorConsultorio gestorConsultorio) {
        this.socket = socket;
        this.gestorConsultorio = gestorConsultorio;
    }

    @Override
    public void run() {
        try {
            entrada = new ObjectInputStream(socket.getInputStream());
            salida = new ObjectOutputStream(socket.getOutputStream());

            usuario = (Usuario) entrada.readObject(); // Se recibe el usuario conectado
            System.out.println(usuario.getNombre() + " se ha conectado.");

            while (true) {
                Object recibido = entrada.readObject();

                if (recibido == null) {
                    break;  // Si no hay datos, termina la ejecución
                }

                if (recibido instanceof Mensaje) {
                    Mensaje mensaje = (Mensaje) recibido;
                    gestorConsultorio.enviarMensaje(mensaje.getRemitente(), mensaje.getDestinatario(), mensaje.getContenido());
                    System.out.println("Mensaje de " + mensaje.getRemitente().getNombre() + " para " + mensaje.getDestinatario().getNombre() + ": " + mensaje.getContenido());

                } else if (recibido instanceof Documento) {
                    Documento documento = (Documento) recibido;
                    gestorConsultorio.enviarDocumento((Paciente) documento.getPropietario(), (Medico) usuario, documento);
                    System.out.println("Documento recibido: " + documento.getNombre() + " de " + documento.getPropietario().getNombre());
                }

                salida.writeObject("Mensaje/Documento recibido.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}