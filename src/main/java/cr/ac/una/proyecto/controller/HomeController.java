package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class HomeController implements Initializable {

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
    }    

    @FXML
    private void onActionGame(ActionEvent event) {
        try {
        Parent newView = App.loadFXML("GameView");

        StackPane root = App.getRoot();

         newView.setTranslateX(root.getWidth());

        root.getChildren().add(newView);

        TranslateTransition slide = new TranslateTransition(javafx.util.Duration.millis(800), newView);
        slide.setToX(0);

        slide.setOnFinished(e -> {
            root.getChildren().remove(0); // quita la anterior
        });

        slide.play();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void onActionUpgrades(ActionEvent event) {
            try {
        Parent newView = App.loadFXML("UpgradesView");

        StackPane root = App.getRoot();

        newView.setTranslateY(-root.getHeight());

        root.getChildren().add(newView);

        TranslateTransition slide = new TranslateTransition(Duration.millis(800), newView);
        slide.setToY(0);

        slide.setOnFinished(e -> {
            root.getChildren().remove(0); // quita la anterior
        });

        slide.play();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
}
