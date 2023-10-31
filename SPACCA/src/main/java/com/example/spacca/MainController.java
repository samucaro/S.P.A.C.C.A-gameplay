package com.example.spacca;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;


public class MainController {
    @FXML
    private OvalPaneController ovalPaneController;

    @FXML
    private Button turnButton;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        Image sfondo = new Image("file:sfondostelle.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(sfondo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        anchorPane.setBackground(new Background(backgroundImage));
    }

    @FXML
    public void handleTurnButton() {
        turnButton.setDisable(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> turnButton.setDisable(false));
        ovalPaneController.cambiaTurno();
        pause.play();
    }
}
