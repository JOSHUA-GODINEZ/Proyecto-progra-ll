package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.App;
import cr.ac.una.proyecto.model.GameState;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class UpgradesController implements Initializable {
      GameState state = GameState.getInstance();
    @FXML
    private Label LblLevelHealth;
    @FXML
    private Label LblNumberHealth;
    @FXML
    private Label LblCost;
    @FXML
    private ProgressBar progressHealth;
      
@FXML
private void onActionHealth(ActionEvent event) {
    state.upgradeHealth();
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //  Nivel
    LblLevelHealth.textProperty().bind(state.upgradeLevelProperty().asString());

    //  Vida total (base + bonus)
LblNumberHealth.textProperty().bind(
    state.baseHealthProperty().asString()
        .concat(" + ")
        .concat(state.bonusHealthProperty().asString())
);

    //  Costo (coins / costo)
    LblCost.textProperty().bind(
        state.coinsProperty().asString()
            .concat("/")
            .concat(state.upgradeCostProperty().asString())
    );
    progressHealth.progressProperty().bind(
    state.coinsProperty().multiply(1.0).divide(state.upgradeCostProperty())
);
    }    

    @FXML
    private void onActionGame(ActionEvent event) {
        
 try {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/cr/ac/una/proyecto/view/GameView.fxml")
    );

    Parent newView = loader.load();

    // obtener controller
  //  UpgradesController controller = loader.getController();

    // pasar la misma base
    //controller.setBase(this.base);

    StackPane root = App.getRoot();

    newView.setTranslateY(-root.getHeight());

    root.getChildren().add(newView);

    TranslateTransition slide = new TranslateTransition(Duration.millis(800), newView);
    slide.setToY(0);

    slide.setOnFinished(e -> {
        root.getChildren().remove(0);
    });

    slide.play();

} catch (IOException e) {
    e.printStackTrace();
}
}
    

    @FXML
    private void onActionHome(ActionEvent event) {
         try {
        Parent newView = App.loadFXML("HomeView");

        StackPane root = App.getRoot();

        newView.setTranslateX(-root.getWidth());

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


}
