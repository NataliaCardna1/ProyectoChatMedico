package sockets;

import modelo.Paciente;
import modelo.TipoConsulta;
import modelo.Usuario;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Cliente {

    private Socket socket;
    private ObjectOutputStream os;
    private ObjectInputStream ois;
    private final Scanner scanner;
    private Usuario usuario;
    private boolean chatActivo;

    public Cliente(String host, int port) {
        this.scanner = new Scanner(System.in);
        this.chatActivo = true;

        try {
            this.socket = new Socket(host, port);
            this.os = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Conectado al servidor.");
        } catch (IOException e) {
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }

    public void iniciar() {
        try {
            // Solicitar datos del usuario
            System.out.print("Ingrese su id: ");
            String id = scanner.nextLine();

            System.out.print("Ingrese su nombre de usuario: ");
            String nombre = scanner.nextLine();

            usuario = new Usuario(id, nombre);

            // Enviar comando AGREGAR y registrar usuario
            os.writeObject("AGREGAR");
            os.writeObject(usuario);
            System.out.println((String) ois.readObject()); // Confirmación del servidor

            boolean continuar = true;
            while (continuar) {
                // Menú de opciones
                System.out.println("\nMenú:");
                System.out.println("1 - Consultar usuarios conectados");
                System.out.println("2 - Crear una sala de chat");
                System.out.println("3 - Consultar salas de chat");
                System.out.println("4 - Empezar a chatear");
                System.out.println("5 - Salir");
                System.out.println("6 - Solicitar Consulta");
                System.out.print("Seleccione una opción: ");
                String opcion = scanner.nextLine();

                switch (opcion) {
                    case "1": // Consultar usuarios conectados
                        os.writeObject("CONSULTA");
                        System.out.println("Usuarios conectados: " + ois.readObject());
                        break;

                    case "2": // Iniciar sala de chat
                        os.writeObject("INICIAR_CHAT");
                        System.out.print("Ingrese el nombre del usuario con quien desea chatear: ");
                        String destinatario = scanner.nextLine();
                        os.writeObject(destinatario);

                        String respuesta = (String) ois.readObject();
                        System.out.println(respuesta);
                        break;

                    case "3": // Consultar salas de chat
                        os.writeObject("CHATS");
                        String chats = (String) ois.readObject();
                        System.out.println(chats);
                        break;

                    case "4": // Empezar a chatear
                        os.writeObject("ENVIAR_MENSAJE");

                        System.out.print("Ingrese el ID del chat: ");
                        String chatID = scanner.nextLine();
                        os.writeObject(chatID);

                        iniciarChat();
                        break;

                    case "5": // Salir
                        os.writeObject("SALIR");
                        continuar = false;
                        break;

                    case "6": // Solicitar consulta
                        System.out.println("Tipos de Consulta:");
                        for (TipoConsulta tipo : TipoConsulta.values()) {
                            System.out.println((tipo.ordinal() + 1) + " - " + tipo.getDescripcion());
                        }

                        System.out.print("Seleccione el tipo de consulta: ");
                        int opcionConsulta = Integer.parseInt(scanner.nextLine());

                        if (opcionConsulta < 1 || opcionConsulta > TipoConsulta.values().length) {
                            System.out.println("Opción inválida.");
                            break;
                        }

                        TipoConsulta tipoConsulta = TipoConsulta.values()[opcionConsulta - 1];

                        // Enviar solicitud al servidor
                        os.writeObject("SOLICITAR CONSULTA");
                        os.writeObject(tipoConsulta.name());
                        os.writeObject(null); // Si no hay parámetros adicionales

                        // Manejo del flujo interactivo
                        while (true) {
                            String mensajeServidor = (String) ois.readObject();

                            if ("Consulta finalizada".equals(mensajeServidor)) {
                                System.out.println("Consulta finalizada exitosamente.");
                                break;
                            }

                            if (mensajeServidor.startsWith("¿")) { // Si el mensaje es una pregunta
                                System.out.println(mensajeServidor);
                                String respuestaConsulta = scanner.nextLine();
                                os.writeObject(respuestaConsulta); // Enviar respuesta al servidor
                            } else {
                                System.out.println(mensajeServidor);
                            }
                        }

                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

    private void iniciarChat() {
        try {
            System.out.println("Escriba sus mensajes. Escriba 'SALIR_CHAT' para terminar.");

            // Hilo para recibir mensajes
            new Thread(() -> {
                try {
                    while (chatActivo) {
                        String mensajeRecibido = (String) ois.readObject();
                        System.out.println(mensajeRecibido);

                        if (mensajeRecibido.equals("Chat terminado.")) {
                            chatActivo = false;
                            System.out.println("El chat ha terminado.");
                            break;
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error al recibir mensajes: " + e.getMessage());
                }
            }).start();

            // Hilo principal para enviar mensajes
            while (chatActivo) {
                String mensaje = scanner.nextLine();
                os.writeObject(mensaje);

                if ("SALIR_CHAT".equals(mensaje)) {
                    chatActivo = false;
                    os.writeObject("Chat terminado.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cerrarConexion() {
        try {
            if (socket != null) socket.close();
            if (os != null) os.close();
            if (ois != null) ois.close();
            System.out.println("Conexión cerrada.");
        } catch (IOException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }

    }


    // Método principal para ejecutar el cliente
    public static void main(String[] args) {
        Cliente cliente = new Cliente("localhost", 12345);
        cliente.iniciar();
    }
}
