package Util;

import modelo.MediMas;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;

public class Persistencia {

    private static final String RUTA_ARCHIVO_MEDIMAS_XML = "MediMasDatos.xml";

    // Método para guardar el objeto MediMas en un archivo XML
    public static void guardarMediMasXML(MediMas mediMas) {
        try (XMLEncoder codificador = new XMLEncoder(new FileOutputStream(RUTA_ARCHIVO_MEDIMAS_XML))) {
            codificador.writeObject(mediMas);
            System.out.println("Datos guardados en " + RUTA_ARCHIVO_MEDIMAS_XML);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar el objeto MediMas desde un archivo XML
    public static MediMas cargarMediMasXML() {
        MediMas mediMas = null;

        try (XMLDecoder decodificador = new XMLDecoder(new FileInputStream(RUTA_ARCHIVO_MEDIMAS_XML))) {
            mediMas = (MediMas) decodificador.readObject();
            System.out.println("Datos cargados desde " + RUTA_ARCHIVO_MEDIMAS_XML);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return mediMas;
    }
}


