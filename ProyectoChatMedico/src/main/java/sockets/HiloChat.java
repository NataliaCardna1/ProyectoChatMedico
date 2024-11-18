package sockets;

import java.io.*;
import java.net.Socket;

public class HiloChat extends Thread {
   /* private final Socket remitente;
    private final Socket destinatario;
    private ObjectInputStream entradaRemitente;
    private ObjectOutputStream salidaDestinatario;

    public HiloChat(Socket remitente, Socket destinatario) {
        this.remitente = remitente;
        this.destinatario = destinatario;

        try {
            this.entradaRemitente = new ObjectInputStream(remitente.getInputStream());
            this.salidaDestinatario = new ObjectOutputStream(destinatario.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Lee el mensaje desde el remitente
                Object mensaje = entradaRemitente.readObject();

                // Envía el mensaje al destinatario
                salidaDestinatario.writeObject(mensaje);
                salidaDestinatario.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // Cierra los streams y sockets cuando el hilo termina
                entradaRemitente.close();
                salidaDestinatario.close();
                remitente.close();
                destinatario.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
}
