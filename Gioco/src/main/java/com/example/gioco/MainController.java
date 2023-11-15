package com.example.gioco;

import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.layout.Background;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.util.Duration;


public class MainController {
    @FXML
    private OvalPaneController ovalPaneController;
    private static BooleanProperty resetHD = new SimpleBooleanProperty(false);
    private static Partita partita;
    private static double centroX = 450.0;
    private static double centroY = 300.0;
    private static double orx = 230.0;
    private static double ory = 250.0;
    private static double irx = 90.0;
    private static double iry = 130.0;
    private static Arc outerArc;
    private static Arc innerArc;
    private static Shape halfDonut;
    @FXML
    private Button turnButton;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        Image sfondo = new Image(getClass().getResource("sfondostelle.jpg").toString());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(sfondo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        anchorPane.setBackground(new Background(backgroundImage));
        partita = new Partita();
        partita.iniziaPartita();
        iniziaHD();
    }

    private void iniziaHD() {
        outerArc = new Arc();
        innerArc = new Arc();
        outerArc.setCenterX(100.0);
        outerArc.setCenterY(100.0);
        outerArc.setRadiusX(orx);
        outerArc.setRadiusY(ory);
        outerArc.setStartAngle(0.0);
        outerArc.setLength(180.0);
        outerArc.setType(ArcType.ROUND);

        innerArc.setCenterX(100.0);
        innerArc.setCenterY(100.0);
        innerArc.setRadiusX(irx);
        innerArc.setRadiusY(iry);
        innerArc.setStartAngle(0.0);
        innerArc.setLength(180.0);
        innerArc.setType(ArcType.ROUND);

        halfDonut = Shape.subtract(outerArc, innerArc);
        anchorPane.getChildren().add(halfDonut);
        halfDonut.fillProperty().addListener((observable, oldValue, newValue) -> {
            anchorPane.getChildren().remove(halfDonut);
            halfDonut.setFill(newValue);
            anchorPane.getChildren().add(halfDonut);
        });
        resetHD.addListener((observable, oldValue, newValue) -> {
            anchorPane.getChildren().remove(halfDonut);
            outerArc.setRadiusX(orx);
            outerArc.setRadiusY(ory);
            innerArc.setRadiusX(irx);
            innerArc.setRadiusY(iry);
            halfDonut = Shape.subtract(outerArc, innerArc);
            halfDonut.setFill(javafx.scene.paint.Color.rgb(0, 191, 255, 0.2));
            /*halfDonut.setOnMouseEntered(event -> {
                halfDonut.setFill(javafx.scene.paint.Color.rgb(0, 191, 255, 0.7));
            });
            halfDonut.setOnMouseExited(event -> {
                halfDonut.setFill(javafx.scene.paint.Color.rgb(0, 191, 255, 0.2));
            });*/
            halfDonut.setMouseTransparent(true);
            anchorPane.getChildren().add(halfDonut);
            resetHD.set(false);
        });
    }

    public static void setDisableHD(boolean b){
        halfDonut.setVisible(b);
    }
    public static void setScenaX(double x){
        centroX=x/2;
        reShape();
    }
    public static void setScenaY(double y){
        centroY=y/2;
        reShape();
    }
    public static void reShape() {
        double h = (60 * Math.min(centroX, centroY)) / 300;
        orx=h + centroX*1/2;
        System.out.println(orx);
        ory=h + centroY*1/1.5;
        System.out.println(ory);
        irx=2*h;
        System.out.println(irx);
        iry=centroY/5+h;
        System.out.println(iry);
        System.out.println("ciao");
        resetHD.set(true);
        halfDonut.setTranslateX(centroX-100);
        halfDonut.setTranslateY(centroY*2-100);
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