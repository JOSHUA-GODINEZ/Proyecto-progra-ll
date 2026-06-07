package cr.ac.una.proyecto;

import cr.ac.una.proyecto.util.AssetManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.layout.StackPane;
public class App extends Application {

    private static StackPane root; // contenedor global

   @Override
public void start(Stage stage) throws IOException {

    // 🚀 HILO DE FONDO: Cargamos todas las imágenes y audios en la RAM al puro inicio
    Thread hiloCarga = new Thread(() -> {
        AssetManager.precargarTodo();
    });
    hiloCarga.setDaemon(true); // Se cierra de forma segura si cierras el juego
    hiloCarga.start();

    // Tu estructura original intacta:
    root = new StackPane();
    root.getChildren().add(loadFXML("LoginView"));

    Scene scene = new Scene(root, 640, 480);
    stage.setScene(scene);
    
    // stage.setFullScreen(true);
    // stage.setFullScreenExitHint(""); 
    stage.setMaximized(true);
    stage.show();
}

    public static void setRoot(String fxml) throws IOException {
        root.getChildren().setAll(loadFXML(fxml));
    }

public static Parent loadFXML(String fxml) throws IOException {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(
            App.class.getResource(
                "/cr/ac/una/proyecto/view/" + fxml + ".fxml"
            )
        );

        return fxmlLoader.load();

    } catch (Exception e) {
        System.err.println("Error cargando: " + fxml);
        e.printStackTrace();
        throw e;
    }
}

    public static StackPane getRoot() {
        return root;
    }

    public static void main(String[] args) {
        launch();
    }
}