package com.example.gioco;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class MainController {
    @FXML
    private OvalPaneController ovalPaneController;
    private double centroX = 600;
    private double centroY = 400;
    private double xOffset = 0;
    private double yOffset = 0;
    private ArrayList<Integer> mano;
    private boolean checkInit = false;
    int conta = 0;
    private GameData gameData = GameData.getInstance();
    @FXML
    private Button turnButton;
    @FXML
    private StackPane stackPane;
    @FXML
    private HBox mazzoEScarti;
    @FXML
    private ProgressBar barraVita;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    public void initialize() {
        impostaCose();
        barraVita.toFront();
        mazzoEScarti.toFront();
        mazzoEScarti.getChildren().getFirst().setOnMouseEntered((MouseEvent event) -> {
            mazzoEScarti.getChildren().getFirst().setScaleX(1.1);
            mazzoEScarti.getChildren().getFirst().setScaleY(1.1);
        });
        mazzoEScarti.getChildren().getFirst().setOnMouseExited((MouseEvent event) -> {
            mazzoEScarti.getChildren().getFirst().setScaleX(1.0);
            mazzoEScarti.getChildren().getFirst().setScaleY(1.0);
        });
    }
    public void impostaCose(){
        anchorPane.prefWidthProperty().bind(stackPane.widthProperty());
        anchorPane.prefHeightProperty().bind(stackPane.heightProperty());
        stackPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            centroX = (double) newValue;
            scalaMazzo();
            mazzoEScarti.setLayoutX(centroX/2 - (checkInit?mazzoEScarti.getWidth():140)/2);
            mettiCarte(false);
            if (mazzoEScarti.getHeight()!=0)
                checkInit=true;
        });
        stackPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            centroY = (double) newValue;
            scalaMazzo();
            mazzoEScarti.setLayoutY(centroY/2 - 15 - (checkInit?mazzoEScarti.getHeight():90)/2);
            mettiCarte(false);
            if (mazzoEScarti.getHeight()!=0)
                checkInit=true;
        });
        mettiCarte(true);
        mettiVita();
        ovalPaneController.getMc(this);
    }
    public void mettiCarte(boolean b) {
        mano = new ArrayList<>();
            for (int i = 0; i < anchorPane.getChildren().size(); i++) {
                if (anchorPane.getChildren().get(i) instanceof ImageView) {
                    if (b) {
                        anchorPane.getChildren().remove(i);
                        i--;
                    } else {
                        mano.add(i);
                    }
                }
            }
        ImageView c;
        if (b) {
            ArrayList<Carta> manoCorrente = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano();
            for (int i = 0; i< manoCorrente.size(); i++) {
                c = manoCorrente.get(i).getImage();
                scala(c);
                anchorPane.getChildren().add(c);
                listenerCarta(c, i);
                mano.add(anchorPane.getChildren().indexOf(c));
            }
        } else {
            for (int i : mano) {
                c = (ImageView) anchorPane.getChildren().get(i);
                scala(c);
            }
        }
        spostaCarta();
    }
    public void spostaCarta(){
        double cX = centroX/2;
        double cY = centroY-20;
        double semilarghezza = centroX/5;
        double altezza = 10 + centroY/5;
        int numOggetti = mano.size();
        double[][] coordinate = calcolaCoordinateArco(cX, cY, semilarghezza, altezza, numOggetti);
        double[] angoli = new double[numOggetti];
        for (int i = 0; i < mano.size(); i++) {
            angoli[i] = -(-30 + i * ((double) 60 /(numOggetti-1)));
            ImageView iv = (ImageView) anchorPane.getChildren().get(mano.get(i));
            iv.setLayoutX(coordinate[i][0] - iv.getFitWidth()/2);
            iv.setLayoutY(coordinate[i][1] - iv.getFitWidth()*1.29);
            iv.setRotate(angoli[i]);
            conta++;
            iv.toBack();
        }
    }
    public void listenerCarta(ImageView iv, int i){
        Carta cartaAttuale = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().get(i);
        iv.setOnMouseEntered((MouseEvent event) -> {
            iv.setScaleX(1.1);
            iv.setScaleY(1.1);
            iv.toFront();
        });
        iv.setOnMouseExited((MouseEvent event) -> {
            iv.setScaleX(1.0);
            iv.setScaleY(1.0);
            iv.toBack();
        });
        iv.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX() - iv.getTranslateX();
            yOffset = event.getSceneY() - iv.getTranslateY();
        });
        iv.setOnMouseDragged((MouseEvent event) -> {
            iv.setTranslateX(event.getSceneX() - xOffset);
            iv.setTranslateY(event.getSceneY() - yOffset);
        });
        iv.setOnMouseReleased((MouseEvent event) -> {
            double finalX = event.getSceneX();
            double finalY = event.getSceneY();
            iv.setTranslateX(0);
            iv.setTranslateY(0);
            System.out.println("Coordinate di rilascio: X = " + finalX + ", Y = " + finalY);
            if (finalX<centroX/2+100&&finalX>centroX/2-100&&finalY<centroY/2+100&&finalY>centroY/2-100){
                cartaAttuale.usaAbilita(ovalPaneController, this);
            }
        });
    }
    public double getScala(){
        return 0.01*(10 * Math.min(centroX, centroY)) / 42;
    }
    public void scalaMazzo(){
        double scala = getScala();
        mazzoEScarti.setScaleX(scala);
        mazzoEScarti.setScaleY(scala);
    }
    public void scala(ImageView c){
        double dim = (5 * Math.min(centroX, centroY)) / 37;
        c.setFitWidth(dim);
    }
    public void mettiVita(){
        int vita = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getHpRimanente();
        barraVita.setProgress((double) vita/5);
    }
    public double[][] calcolaCoordinateArco(double cX, double cY, double semilarghezza, double altezza, int numOggetti) {
        double[][] coordinate = new double[numOggetti][2];
        double angoloStep = Math.PI / (numOggetti - 1);
        for (int i = 0; i < numOggetti; i++) {
            double angolo = i * angoloStep;
            double x = cX + semilarghezza * Math.cos(angolo);
            double y = cY - altezza * Math.sin(angolo);
            coordinate[i][0] = x;
            coordinate[i][1] = y;
        }
        return coordinate;
    }

    @FXML
    public void handleTurnButton() {
        turnButton.setDisable(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        ovalPaneController.cambiaTurno();
        pause.setOnFinished(event -> {
            turnButton.setDisable(false);
            mettiCarte(true);
            mettiVita();
        });
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
    public void startSelectionMC(){
        anchorPane.setDisable(true);
        anchorPane.setVisible(false);
    }
    public void stopSelectionMC(){
        anchorPane.setDisable(false);
        anchorPane.setVisible(true);
        mettiCarte(true);
        System.out.println("FINESeLEZIONEMC");
    }
}