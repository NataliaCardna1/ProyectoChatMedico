package sockets;

import modelo.*;

import java.io.*;
import java.net.Socket;

public class ClienteChat {
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public ClienteChat(String host, int puerto, Usuario usuario) {
        try {
            socket = new Socket(host, puerto);
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());

            salida.writeObject(usuario);
            salida.flush();

            new Thread(new Listener()).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(Usuario destinatario, String contenido) {
        try {
            Mensaje mensaje = new Mensaje(destinatario, contenido);
            salida.writeObject(mensaje);
            salida.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarDocumento(Documento documento) {
        try {
            salida.writeObject(documento);
            salida.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Listener implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Object respuesta = entrada.readObject();
                    if (respuesta == null) {
                        break;  // No hay más datos por leer
                    }
                    System.out.println("Servidor: " + respuesta);
                }
            } catch (IOException | ClassNotFoundException e) {
                if (e instanceof EOFException) {
                    System.out.println("Conexión cerrada por el servidor.");
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Usuario paciente = new Paciente("1", "Juan Pérez");
        ClienteChat cliente = new ClienteChat("localhost", 12345, paciente);

        cliente.enviarMensaje(new Medico("2", "Dra. Ana López"), "Consulta sobre el tratamiento.");
    }
}