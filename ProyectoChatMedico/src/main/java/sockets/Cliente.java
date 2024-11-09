package sockets;

import modelo.Paciente;
import modelo.Usuario;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {

        try(Socket socket = new Socket("localhost", 12345)){

            Usuario usuario = new Paciente("123", "Juana");

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream((socket.getInputStream()));

            Scanner scanner = new Scanner(System.in);
            os.writeObject(usuario);

            while(true){

                System.out.println("Escriba 1 (consultar conectados), 2 (chatear con alguien), 3 (salir)");
                String opcion= scanner.next();

                if(opcion.equals("1")){
                    os.writeObject("CONSULTA");
                    System.out.println(ois.readObject().toString());
                }else if(opcion.equals("2")){
                    os.writeObject("CHAT");
                }else{
                    os.writeObject("SALIR");
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
