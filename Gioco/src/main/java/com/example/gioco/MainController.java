package com.example.gioco;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    @FXML
    private OvalPaneController ovalPaneController;
    private double centroX = 300;
    private double centroY = 200;
    private GameData gameData = GameData.getInstance();
    @FXML
    private Button turnButton;
    @FXML
    private StackPane stackPane;
    @FXML
    private HBox mazzoEScarti;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    public void initialize() {
        impostaCose();
        mettiCarte();
        /*
        Image sfondo = new Image(getClass().getResource("SfondoGioco.png").toString());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(sfondo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        borderPane.setBackground(new Background(backgroundImage));*/
    }
    public void impostaCose(){
        anchorPane.prefWidthProperty().bind(stackPane.widthProperty());
        anchorPane.prefHeightProperty().bind(stackPane.heightProperty());
        anchorPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            centroX = (double) newValue;
            turnButton.setLayoutX((50) - (88.7 / 2)); //prima il margine poi spazio oggetto
            mazzoEScarti.setLayoutX((newValue.doubleValue() / 2) - 75);
            mettiCarte();
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println(centroX);
            System.out.println(centroY);
        });
        anchorPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            centroY = (double) newValue;
            turnButton.setLayoutY(newValue.doubleValue() - (20) - 25.3 / 2);
            mazzoEScarti.setLayoutY((newValue.doubleValue() / 2) - (49));
            mettiCarte();
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println(centroX);
            System.out.println(centroY);
        });
        mettiCarte();
    }
    public void mettiCarte() {
        for (int i = 0; i < anchorPane.getChildren().size(); i++) {
            if (anchorPane.getChildren().get(i) instanceof VBox) {
                anchorPane.getChildren().remove(i);
                i--;
            }
        }
        try {
            ArrayList<Carta> mano = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano();
            FXMLLoader cardLoader;
            VBox c;
            //for (int i = 0; i< mano.size(); i++)
            cardLoader = new FXMLLoader(getClass().getResource(mano.get(0).getFXML()));
            c = cardLoader.load();
            anchorPane.getChildren().add((VBox) c);
            //RICONTROLLLARE MALAMENTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
            Double scalaFactor = centroX<centroY?(1/((176*12.5)/centroX)):(1/((227.3*68.2)/centroY));
            System.out.println("SCF: "+scalaFactor);
            Scale scale = new Scale(scalaFactor, scalaFactor);
            c.getTransforms().add(scale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        anchorPane.requestLayout(); // Forza l'aggiornamento delle dimensioni
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
        for (int i = 0; i < anchorPane.getChildren().size(); i++) {
            if (anchorPane.getChildren().get(i) instanceof VBox) {
                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                double vboxHeight = ((VBox) anchorPane.getChildren().get(i)).getBoundsInLocal().getHeight();
                double vboxWidth = ((VBox) anchorPane.getChildren().get(i)).getBoundsInLocal().getWidth();
                System.out.println("Altezza effettiva del VBox: " + vboxHeight);
                System.out.println("Larghezza effettiva del VBox: " + vboxWidth);
            }
        }
        Giocatore player = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente());
        for(int i = 1; i <= 2; i++) {
            player.addCarta(gameData.getMazzo().pesca());
            System.out.println("Ho pescato");
        }
    }
}