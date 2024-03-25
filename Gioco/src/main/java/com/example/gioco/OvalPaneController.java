package com.example.gioco;

import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import javax.swing.*;

public class OvalPaneController {
    @FXML
    private Pane ovalPane;
    private static GameData gameData = GameData.getInstance();
    private static Group[] spheres;
    private Cylinder ring;
    private boolean isPaused = false;
    private static double centroX;
    private static double centroY;
    private static BooleanProperty resetHD = new SimpleBooleanProperty(false);
    private static double orx = 230.0;
    private static double ory = 250.0;
    private static double irx = 90.0;
    private static double iry = 130.0;
    private static Shape halfDonut;
    private static final int n = gameData.getNumero();
    private static int turnoDi = 0;
    @FXML
    public void initialize() {
        spheres = new Group[n];
        for (int i = 0; i < n; i++) {
            spheres[i] = new Group();
            spheres[i].getChildren().addFirst(new Sphere(60));
            PhongMaterial material = new PhongMaterial();
            Image texture = textures(i);
            material.setDiffuseMap(texture);
            ((Sphere) spheres[i].getChildren().getFirst()).setMaterial(material);
            spheres[i].getChildren().getFirst().setRotationAxis(Rotate.Y_AXIS);
            rotazionePianeti(i);
            if (i==2){
                ring = new Cylinder(80, 3);
                PhongMaterial materialS = new PhongMaterial();
                materialS.setDiffuseColor(Color.BEIGE);
                ring.setMaterial(materialS);
                ring.setRotationAxis(Rotate.Y_AXIS);
                ring.setRotate(75);
                spheres[i].getChildren().add(ring);
            }
            int indice = ((n - 1) - i) % n;
            System.out.println(indice);
            Text nome = new Text(gameData.getGiocatoriPartita().get(indice).getNome());
            nome.setFont(Font.font("Game of Thrones", 17));
            nome.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 0.5");
            double centerX = - nome.getBoundsInLocal().getWidth()/2;
            double centerY = -5 -nome.getBoundsInLocal().getHeight()/2;
            nome.setLayoutX(centerX);
            nome.setLayoutY(centerY);
            spheres[i].getChildren().add(nome);
            ovalPane.getChildren().add(spheres[i]);
        }
        iniziaHD();
    }

    //INIZIO HALF DONUT
    private void iniziaHD() {
        Arc outerArc = new Arc(100.0, 100.0, orx, ory, 0.0, 180.0);
        outerArc.setType(ArcType.ROUND);
        Arc innerArc = new Arc(100.0, 100.0, irx, iry, 0.0, 180.0);
        innerArc.setType(ArcType.ROUND);
        halfDonut = Shape.subtract(outerArc, innerArc);
        ovalPane.getChildren().add(halfDonut);
        halfDonut.fillProperty().addListener((observable, oldValue, newValue) -> {
            ovalPane.getChildren().remove(halfDonut);
            halfDonut.setFill(newValue);
            ovalPane.getChildren().add(halfDonut);
        });
        resetHD.addListener((observable, oldValue, newValue) -> {
            ovalPane.getChildren().remove(halfDonut);
            outerArc.setRadiusX(orx);
            outerArc.setRadiusY(ory);
            innerArc.setRadiusX(irx);
            innerArc.setRadiusY(iry);
            halfDonut = Shape.subtract(outerArc, innerArc);
            halfDonut.setFill(javafx.scene.paint.Color.rgb(0, 191, 255, 0.2));
            ovalPane.getChildren().add(halfDonut);
            resetHD.set(false);
        });
    }
    public static void reShape() {
        double h = (60 * Math.min(centroX, centroY)) / 300;
        orx=h + centroX*1/2;
        ory=h + centroY*1/1.5;
        irx=2*h;
        iry=centroY/5+h;
        resetHD.set(true);
        halfDonut.setTranslateX(centroX-100);
        halfDonut.setTranslateY(centroY*2-100);
    }
    //FINE HALF DONUTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
    //INIZIO ANIMAZIONE E ABBELLIMENTO SFEREEEEEEEEEEEEEEEEEEEEEEEEEEEEE
    private void rotazionePianeti(int i) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!isPaused) {
                    spheres[i].getChildren().getFirst().rotateProperty().set(spheres[i].getChildren().getFirst().getRotate() + 0.2);
                }
            }
        };
        timer.start();
    }

    private Image textures(int i){
        return switch (i) {
            case 7 -> new Image(getClass().getResource("mercury.jpg").toString());
            case 6 -> new Image(getClass().getResource("venus.jpg").toString());
            case 5 -> new Image(getClass().getResource("earth.jpg").toString());
            case 4 -> new Image(getClass().getResource("mars.jpg").toString());
            case 3 -> new Image(getClass().getResource("jupiter.jpg").toString());
            default -> new Image(getClass().getResource("saturn.jpg").toString());
            case 1 -> new Image(getClass().getResource("uranus.jpg").toString());
            case 0 -> new Image(getClass().getResource("neptune.jpg").toString());
        };
    }
    private static void posizionaSfere() {
        for (int i = 0; i < n; i++) {
            int indice = ((n - turnoDi) + i) % n;
            if (indice == 2) {
                ((Cylinder) spheres[indice].getChildren().get(1)).setRadius((60 * Math.min(centroX, centroY)) / 300 + 30);
            }
            ((Sphere) spheres[indice].getChildren().get(0)).setRadius((60 * Math.min(centroX, centroY)) / 300);
            spheres[indice].setTranslateX((centroX - ((Sphere) spheres[indice].getChildren().getFirst()).getRadius() * 2 + 50) * Math.cos((2 * Math.PI * (i + 1) / n)+(Math.PI/2)) + centroX);
            spheres[indice].setTranslateY((centroY - ((Sphere) spheres[indice].getChildren().getFirst()).getRadius() * 2 + 40) * Math.sin((2 * Math.PI * (i + 1) / n)+(Math.PI/2)) + centroY);
            spheres[indice].setScaleX(i == n-1 ? 0.6 : 1);
            spheres[indice].setScaleY(i == n-1 ? 0.6 : 1);
        }
    }
    //FINE SFEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
    public static void setScenaX(double x){
        centroX=x/2;
        posizionaSfere();
        reShape();
    }
    public static void setScenaY(double y){
        centroY=y/2;
        posizionaSfere();
        reShape();
    }
    public void cambiaTurno() {
        Timeline timeline = new Timeline();
        turnoDi=(turnoDi==(n-1))?0:turnoDi+1;
        gameData.setTurnoCorrente(turnoDi);
        for (int ind = 0; ind < n-1; ind++) {
            int i = ind % n;
            KeyValue kv1 = new KeyValue(spheres[i].translateXProperty(), spheres[i+1].getTranslateX());
            KeyValue kv2 = new KeyValue(spheres[i].translateYProperty(), spheres[i+1].getTranslateY());
            KeyValue kv3 = new KeyValue(spheres[i].scaleXProperty(), spheres[i+1].getScaleX());
            KeyValue kv4 = new KeyValue(spheres[i].scaleYProperty(), spheres[i+1].getScaleY());
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv1, kv2, kv3, kv4);
            timeline.getKeyFrames().add(kf);
        }
        KeyValue kv1 = new KeyValue(spheres[n-1].translateXProperty(), spheres[0].getTranslateX());
        KeyValue kv2 = new KeyValue(spheres[n-1].translateYProperty(), spheres[0].getTranslateY());
        KeyValue kv3 = new KeyValue(spheres[n-1].scaleXProperty(), spheres[0].getScaleX());
        KeyValue kv4 = new KeyValue(spheres[n-1].scaleYProperty(), spheres[0].getScaleY());
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv1, kv2, kv3, kv4);
        timeline.getKeyFrames().add(kf);
        timeline.playFromStart();
        timeline.setOnFinished(event -> posizionaSfere());
    }
}