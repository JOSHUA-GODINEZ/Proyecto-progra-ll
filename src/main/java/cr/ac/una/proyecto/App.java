package cr.ac.una.proyecto;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.layout.StackPane;
public class App extends Application {

    private static StackPane root;

   @Override
public void start(Stage stage) throws IOException {
    root = new StackPane();
    root.getChildren().add(loadFXML("LoginView"));
    Scene scene = new Scene(root, 640, 480);
    stage.setScene(scene); 
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