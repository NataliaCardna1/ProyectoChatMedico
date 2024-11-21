package sockets;

import modelo.Paciente;
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
                        os.writeObject("SOLICITAR CONSULTA");
                        String pregunta = (String) ois.readObject(); // El servidor envía la pregunta
                        System.out.print(pregunta);
                        respuesta = scanner.nextLine(); // El cliente responde
                        os.writeObject(respuesta); // Enviar respuesta al servidor
                        break;

                    default:
                        System.out.println("Opción no válida.");
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
    public void solicitarConsulta(String tipoConsulta, Object parametros) {
        if ("SOLICITAR CONSULTA".equals(tipoConsulta)) {
            try {
                // Realizar las preguntas y guardar las respuestas en un archivo
                os.writeObject("Iniciando la consulta...");
                os.flush();

                // Aquí puedes agregar preguntas específicas para el cliente
                String respuesta1 = hacerPregunta("¿Cuál es su nombre?");
                String respuesta2 = hacerPregunta("¿Cuál es su edad?");
                String respuesta3 = hacerPregunta("¿Cuál es su problema médico?");

                // Guardar las respuestas en un archivo
                guardarRespuestasEnArchivo(respuesta1, respuesta2, respuesta3);

                // Enviar una confirmación al cliente
                os.writeObject("Consulta completada y respuestas guardadas.");
                os.flush();
            } catch (IOException e) {
                try {
                    os.writeObject("Error al procesar la consulta: " + e.getMessage());
                    os.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String hacerPregunta(String pregunta) throws IOException, ClassNotFoundException {
        // Enviar la pregunta al cliente
        os.writeObject(pregunta);
        os.flush();

        // Leer la respuesta del cliente
        String respuesta = (String) ois.readObject();
        System.out.println("Respuesta recibida: " + respuesta);
        return respuesta;
    }

    private void guardarRespuestasEnArchivo(String respuesta1, String respuesta2, String respuesta3) {
        // Guardar las respuestas en un archivo (por ejemplo, un archivo de texto)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("consultas.txt", true))) {
            writer.write("Consulta de usuario: \n");
            writer.write("Nombre: " + respuesta1 + "\n");
            writer.write("Edad: " + respuesta2 + "\n");
            writer.write("Problema médico: " + respuesta3 + "\n");
            writer.write("--------\n");
            System.out.println("Respuestas guardadas en el archivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método principal para ejecutar el cliente
    public static void main(String[] args) {
        Cliente cliente = new Cliente("localhost", 12345);
        cliente.iniciar();
    }
}
