package network;

import java.io.IOException;

public class Ejemplo {
    public static void main(String[] args) {
        new Thread(() -> {
            ChatServer servidor = new ChatServer(5000);
            servidor.iniciar();
        }).start();

        try {
            ChatClient clienteMedico = new ChatClient("localhost", 5000, "medico1",
                    mensaje -> System.out.println("Médico recibió: " + mensaje));
            clienteMedico.conectar();

            ChatClient clientePaciente = new ChatClient("localhost", 5000, "paciente1",
                    mensaje -> System.out.println("Paciente recibió: " + mensaje));
            clientePaciente.conectar();

            clientePaciente.enviarMensaje("medico1", "Hola doctor, tengo una consulta");

            Thread.sleep(1000);

            clienteMedico.enviarMensaje("paciente1", "Hola, ¿en qué puedo ayudarle?");

            Thread.sleep(5000);

            clienteMedico.desconectar();
            clientePaciente.desconectar();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
