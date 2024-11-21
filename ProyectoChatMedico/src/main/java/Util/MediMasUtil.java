package Util;

import modelo.MediMas;
import modelo.Medico;
import modelo.Paciente;

import java.util.ArrayList;
import java.util.List;

public class MediMasUtil {

    // Método para crear un objeto MediMas con datos de ejemplo
    public static MediMas crearDatosEjemplo() {
        MediMas mediMas = new MediMas();

        // Crear médicos de ejemplo
        List<Medico> medicos = new ArrayList<>();
        medicos.add(new Medico("1", "Dr. Juan Pérez"));
        medicos.add(new Medico("2", "Dra. Ana Ramírez"));
        medicos.add(new Medico("3", "Dr. Luis García"));

        // Crear pacientes de ejemplo
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(new Paciente("101", "Carlos Hernández"));
        pacientes.add(new Paciente("102", "María López"));
        pacientes.add(new Paciente("103", "Sofía Torres"));

        // Agregar médicos y pacientes al objeto MediMas
        mediMas.setMedicos(medicos);
        mediMas.setPacientes(pacientes);

        return mediMas;
    }

    // Método para guardar un objeto MediMas en un archivo XML
    public static void guardarMediMas(MediMas mediMas) {
        Persistencia.guardarMediMasXML(mediMas);
    }

    // Método para cargar un objeto MediMas desde un archivo XML
    public static MediMas cargarMediMas() {
        return Persistencia.cargarMediMasXML();
    }

    // Main para probar la funcionalidad
    public static void main(String[] args) {
        // Crear datos de ejemplo
        MediMas mediMas = crearDatosEjemplo();

        // Guardar los datos en un archivo XML
        System.out.println("Guardando datos de ejemplo...");
        guardarMediMas(mediMas);

        // Cargar los datos desde el archivo XML
        System.out.println("Cargando datos desde el archivo XML...");
        MediMas mediMasCargado = cargarMediMas();

        // Mostrar los datos cargados
        System.out.println("Datos cargados:");
        System.out.println(mediMasCargado);
    }
}
