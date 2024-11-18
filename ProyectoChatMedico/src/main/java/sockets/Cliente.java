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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine();
        String name = sc.nextLine();
        try(Socket socket = new Socket("localhost", 12345)){

            Usuario usuario = new Paciente(id, name);

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream((socket.getInputStream()));

            Scanner scanner = new Scanner(System.in);
            os.writeObject(usuario);

            new Thread(()->{
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
            }).start();
            while(true){

                System.out.println("Escriba 1 (consultar conectados), 2 (chatear con alguien), 3 (salir)");
                String opcion= scanner.next();

                if(opcion.equals("1")){
                    os.writeObject("CONSULTA");
                }else if(opcion.equals("2")){
                    System.out.println("Ingrese el ID del destinatario");
                    String destinatarioID = scanner.next();
                    System.out.println("Escriba el mensaje");
                    String mensaje = scanner.next();

                    os.writeObject("CHAT");
                    os.writeObject(destinatarioID);
                    os.writeObject(mensaje);
                }else{
                    os.writeObject("SALIR");
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
