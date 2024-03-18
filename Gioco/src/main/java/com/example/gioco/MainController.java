package com.example.gioco;

import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.Background;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.util.Duration;


public class MainController {
    @FXML
    private OvalPaneController ovalPaneController;
    private static GameData gameData = GameData.getInstance();
    @FXML
    private Button turnButton;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView mazzo;

    @FXML
    public void initialize() {/*
        Image sfondo = new Image(getClass().getResource("SfondoGioco.png").toString());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(sfondo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        borderPane.setBackground(new Background(backgroundImage));*/
    }
    @FXML
    public void handleTurnButton() {
        turnButton.setDisable(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> turnButton.setDisable(false));
        ovalPaneController.cambiaTurno();
        pause.play();
    }
    public void pescataInizialeGiocatore() {
        Giocatore player = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente());
        for(int i = 1; i <= 2; i++) {
            player.addCarta(gameData.getMazzo().pesca());
            System.out.println("Ho pescato");
        }
    }
}