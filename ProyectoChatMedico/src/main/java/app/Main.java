package app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.*;

import static javafx.application.Application.launch;


public class Main extends Application {
    public static void main(String[] args) {



        launch(Main.class, args);

/*
        var usuario = new Usuario("U1", "Julian");
        var usuario2 = new Usuario("U2", "Natalia");
        var medico = new Usuario("U1", "Julian");
        var documento = new Documento("1", "His", usuario, "C:\\midocumento.docx", TipoDocumento.RADIOGRAFIAS);
        var documento2 = new Documento("2", "Receta", usuario2, "C:\\mireceta.docx", TipoDocumento.RECETAS_MEDICAS);
        var gestorConsultario = new GestorConsultorio();

        try {
            gestorConsultario.cargarDocumento(usuario, documento, medico);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
//        try {
//            gestorConsultario.enviarDocumento(medico, documento2, usuario2);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }


//        for (Documento documentoUsuario : usuario2.getDocumentos() ) {
//            System.out.println(documentoUsuario.nombre());
//        }*/


    }



    public void start(Stage primaryStage) {
        try {
            // Carga el archivo FXML desde recursos
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("org/example/proyectochatmedico/Login/Login.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);

            primaryStage.setTitle("Mi Ventana");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
