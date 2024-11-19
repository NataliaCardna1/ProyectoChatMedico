package sockets;

import modelo.Paciente;
import modelo.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private boolean enChat;

    public void manejarCLiente(){
        try(Socket socket = new Socket("localhost", 12345)){

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream((socket.getInputStream()));

            Scanner scanner = new Scanner(System.in);

            System.out.println("Escriba su id: ");
            String id = scanner.nextLine();

            System.out.println("Escriba su nombre: ");
            String name = scanner.nextLine();
            Usuario usuario = new Paciente(id, name);


            /*new Thread(()->{
                while (true){
                    try {
                        Object obj = ois.readObject();
                        while (obj!=null){
                            System.out.println(obj);
                            obj = ois.readObject();
                        }
                    } catch (IOException e) {
                    } catch (ClassNotFoundException e) {
                    }

                }
            }).start();*/

            Thread escuchar = new Thread(() -> {
                try {
                    while (true) {
                        Object mensaje = ois.readObject();

                        if (mensaje instanceof String) {
                            String texto = (String) mensaje;

                            // Si el servidor indica que se ha iniciado un chat
                            if (texto.startsWith("CHAT_INICIADO")) {
                                enChat = true;
                                System.out.println("Has sido agregado a un chat. Escribe mensajes para comunicarte.");
                            } else {
                                System.out.println(mensaje);
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Conexión cerrada.");
                }
            });
            escuchar.start();

            while(!enChat){

                System.out.println("Escriba 0 (conectarse), 1 (consultar conectados), 2 (chatear con alguien), 3 (salir)");
                String opcion= scanner.next();

                System.out.println(opcion);

                if(opcion.equals("0")){
                    os.writeObject("AGREGAR");
                    os.writeObject(usuario);
                }else if(opcion.equals("1")){
                    os.writeObject("CONSULTA");
                    System.out.println( ois.readObject() );
                }else if(opcion.equals("2")){
                    os.writeObject("CHAT");
                    System.out.println("Ingrese el ID del destinatario");
                    String destinatarioID = scanner.next();
                    os.writeObject(destinatarioID);

                    /*System.out.println("Escriba el mensaje");
                    String mensaje = scanner.next();

                    os.writeObject("CHAT");
                    os.writeObject(destinatarioID);
                    os.writeObject(mensaje);*/
                }else{
                    os.writeObject("SALIR");
                }

            }

            while (enChat) {
                System.out.println("Escribe tu mensaje:");
                String mensaje = scanner.nextLine();
                os.writeObject(mensaje);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Cliente().manejarCLiente();
    }

}
