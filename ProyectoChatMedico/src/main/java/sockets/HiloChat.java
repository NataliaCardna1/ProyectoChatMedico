package sockets;

import java.io.*;
import java.net.Socket;

public class HiloChat extends Thread {
    private ObjectInputStream oisCliente1;
    private ObjectInputStream oisCliente2;

    private ObjectOutputStream oosCliente1;
    private ObjectOutputStream oosCliente2;

    public HiloChat(Socket cliente1, Socket cliente2) {

        try {
            this.oisCliente1 = new ObjectInputStream(cliente1.getInputStream());
            this.oisCliente2 = new ObjectInputStream(cliente2.getInputStream());

            this.oosCliente1 = new ObjectOutputStream(cliente1.getOutputStream());
            this.oosCliente2 = new ObjectOutputStream(cliente2.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            Thread hilo1 = new Thread(() -> manejarMensajes(oisCliente1, oosCliente2));
            Thread hilo2 = new Thread(() -> manejarMensajes(oisCliente2, oosCliente1));

            hilo1.start();
            hilo2.start();

            hilo1.join();
            hilo2.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void manejarMensajes(ObjectInputStream entrada, ObjectOutputStream salida) {
        try {
            String mensaje;
            while ((mensaje = entrada.readObject().toString()) != null) {
                salida.writeObject(mensaje); // Reenviar mensaje al otro cliente
            }
        } catch (IOException e) {
            System.err.println("Error en la comunicación: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
