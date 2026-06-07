package cr.ac.una.proyecto.util;
import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Validations {
   public static void showMessage(Label lbl, String message, Pane container) {
    container.setVisible(true);
    lbl.setText(message);
    lbl.setTranslateY(150);
    lbl.getStyleClass().add("label-error");
    TranslateTransition moveUp = new TranslateTransition(Duration.millis(1500), lbl);
    moveUp.setFromY(0);
    moveUp.setToY(-400);
    FadeTransition fade = new FadeTransition(Duration.millis(1500), lbl);
    fade.setFromValue(1);
    fade.setToValue(0);
    ParallelTransition animation = new ParallelTransition(moveUp, fade);
    animation.setOnFinished(e -> {container.setVisible(false);});
    animation.play();
}
}
