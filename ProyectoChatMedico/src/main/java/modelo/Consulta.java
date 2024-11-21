package modelo;

import sockets.HiloCliente;

import java.io.IOException;
import java.util.List;

public class Consulta {
    private HiloCliente hiloCliente;
    private String tipoConsulta;
    private Object parametros;

    public Consulta(HiloCliente hiloCliente, String tipoConsulta, Object parametros) {
        this.hiloCliente = hiloCliente;
        this.tipoConsulta = tipoConsulta;
        this.parametros = parametros;
    }

    public void ejecutar() {
        try {
            // Enviar el tipo de consulta al servidor
            hiloCliente.getOos().writeObject("CONSULTA");
            hiloCliente.getOos().writeObject(tipoConsulta);
            hiloCliente.getOos().writeObject(parametros);

            // Leer la respuesta del servidor
            Object respuesta = hiloCliente.getOis().readObject();

            // Procesar la respuesta
            if (respuesta instanceof List) {
                List<?> resultados = (List<?>) respuesta;
                procesarResultados(resultados);
            } else if (respuesta instanceof String) {
                System.out.println("Mensaje del servidor: " + respuesta);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al solicitar consulta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void procesarResultados(List<?> resultados) {
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron resultados.");
            return;
        }

        for (Object resultado : resultados) {
            // Procesar cada resultado según su tipo
            if (resultado instanceof Usuario) {
                Usuario usuario = (Usuario) resultado;
                System.out.println("Usuario: " + usuario.getNombre());
            }
            // Agrega más condiciones según los tipos de objetos que puedas consultar
        }
    }
}
