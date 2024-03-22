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
    private ArrayList<Carta> manoCorrente;
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
    private ImageView immagineSfondo;
    @FXML
    public void initialize() {
        //impostaCose();
        //mettiCarte(true);
        /*
        Image sfondo = new Image(getClass().getResource("SfondoGioco.png").toString());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(sfondo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        borderPane.setBackground(new Background(backgroundImage));*/
    }
    /*public void impostaCose(){
        anchorPane.prefWidthProperty().bind(stackPane.widthProperty());
        anchorPane.prefHeightProperty().bind(stackPane.heightProperty());
        anchorPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            AnchorPane.setTopAnchor(turnButton, 20.0);
            AnchorPane.setLeftAnchor(turnButton, 20.0);
            AnchorPane.setTopAnchor(mazzoEScarti, 0.5);
            AnchorPane.setLeftAnchor(mazzoEScarti, 0.5);
            //centroX = (double) newValue;
            //turnButton.setLayoutX((50) - (88.7 / 2)); //prima il margine poi spazio oggetto
            //mazzoEScarti.setLayoutX((newValue.doubleValue() / 2) - 75);
            mettiCarte(false);
        });
        anchorPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            AnchorPane.setTopAnchor(turnButton, 20.0);
            AnchorPane.setLeftAnchor(turnButton, 20.0);
            AnchorPane.setTopAnchor(mazzoEScarti, 0.5);
            AnchorPane.setLeftAnchor(mazzoEScarti, 0.5);
            //centroY = (double) newValue;
            //turnButton.setLayoutY(newValue.doubleValue() - (20) - 25.3 / 2);
            //mazzoEScarti.setLayoutY((newValue.doubleValue() / 2) - (49));
            mettiCarte(false);
        });
        mettiCarte(true);
    }*/
    public void mettiCarte(boolean b) {
        ArrayList<Integer> v = new ArrayList<>();
            for (int i = 0; i < anchorPane.getChildren().size(); i++) {
                if (anchorPane.getChildren().get(i) instanceof VBox) {
                    if (b) {
                        anchorPane.getChildren().remove(i);
                        i--;
                    } else {
                        v.add(i);
                    }
                }
            }
        try {
            VBox c;
            if (b) {
                manoCorrente = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano();
                FXMLLoader cardLoader;
                //for (int i = 0; i< mano.size(); i++)
                cardLoader = new FXMLLoader(getClass().getResource(manoCorrente.get(0).getFXML()));
                c = cardLoader.load();
                anchorPane.getChildren().add(c);
                scalaESPOSTA(c);
            } else {
                for (int i : v) {
                    c = (VBox) anchorPane.getChildren().get(i);
                    c.getTransforms().clear();
                    scalaESPOSTA(c);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //anchorPane.requestLayout();
    }
    public void scalaESPOSTA(VBox c){
        double scalaFactor = 0.01*((4 * Math.min(centroX, centroY)) / 50);
        Scale scale = new Scale(scalaFactor, scalaFactor);
        c.getTransforms().add(scale);
    }
    @FXML
    public void handleTurnButton() {
        turnButton.setDisable(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> turnButton.setDisable(false));
        ovalPaneController.cambiaTurno();
        mettiCarte(true);
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