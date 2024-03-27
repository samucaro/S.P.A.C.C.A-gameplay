package com.example.gioco;

import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.util.Objects;
import java.util.Random;

public class OvalPaneController {
    @FXML
    private Pane ovalPane;
    private static final GameData gameData = GameData.getInstance();
    private MainController mc;
    private static Group[] pianeti;
    PhongMaterial material;
    PhongMaterial redMaterial = new PhongMaterial(Color.RED);
    private Giocatore giocatoreSelezionato = null;
    private final ProgressBar progressBar = new ProgressBar();
    private final Label scegliAvversario = new Label("Scegli chi attaccare!");
    private static double centroX;
    private static double centroY;
    private static BooleanProperty resetHD = new SimpleBooleanProperty(false);
    private boolean planetSelected = false;
    private static double orx = 230.0;
    private static double ory = 250.0;
    private static double irx = 90.0;
    private static double iry = 130.0;
    private static Shape halfDonut;
    private static final int n = gameData.getNumero();
    private static int turnoDi = 0;
    @FXML
    public void initialize() {
        pianeti = new Group[n];
        for (int i = 0; i < n; i++) {
            pianeti[i] = new Group();
            pianeti[i].getChildren().addFirst(new Sphere(60));
            Image texture = textures(i);
            material = new PhongMaterial();
            material.setDiffuseMap(texture);
            ((Sphere) pianeti[i].getChildren().getFirst()).setMaterial(material);
            pianeti[i].getChildren().getFirst().setRotationAxis(Rotate.Y_AXIS);
            rotazionePianeti(i);
            if (i==2){
                Cylinder ring = new Cylinder(80, 3);
                PhongMaterial materialS = new PhongMaterial();
                materialS.setDiffuseColor(Color.BEIGE);
                ring.setMaterial(materialS);
                ring.setRotationAxis(Rotate.Y_AXIS);
                ring.setRotate(75);
                pianeti[i].getChildren().add(ring);
            }
            int indice = ((n - 1) - i) % n;
            Text nomeGiocatore = new Text(gameData.getGiocatoriPartita().get(indice).getNome());
            nomeGiocatore.setFont(Font.font("Game of Thrones", 17));
            nomeGiocatore.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 0.5");
            double centerX = -nomeGiocatore.getBoundsInLocal().getWidth()/2;
            double centerY = -5 -nomeGiocatore.getBoundsInLocal().getHeight()/2;
            nomeGiocatore.setLayoutX(centerX);
            nomeGiocatore.setLayoutY(centerY);
            pianeti[i].getChildren().add(nomeGiocatore);
            int ind = i;
            pianeti[ind].setOnMouseEntered(event -> {
                pianeti[ind].setScaleX(pianeti[ind].getScaleX()+0.1);
                pianeti[ind].setScaleY(pianeti[ind].getScaleY()+0.1);
            });
            pianeti[ind].setOnMouseExited(event -> {
                pianeti[ind].setScaleX(pianeti[ind].getScaleX()-0.1);
                pianeti[ind].setScaleY(pianeti[ind].getScaleY()-0.1);
            });
            pianeti[ind].setOnMouseClicked(event -> {
                ((Text) pianeti[ind].getChildren().getLast()).getText();
                for (int j = 0; j < gameData.getGiocatoriPartita().size(); j++){
                    if (gameData.getGiocatoriPartita().get(j).getNome().equals(((Text) pianeti[ind].getChildren().getLast()).getText())){
                        giocatoreSelezionato = gameData.getGiocatoriPartita().get(j);
                        planetSelected = true;
                    }
                }
            });
            ovalPane.getChildren().add(pianeti[i]);
        }
        progressBar.setStyle("-fx-background-color: cyan; -fx-background-radius: 20;");
        scegliAvversario.setStyle("--body-font-size: 15; -fx-background-color: none; -fx-border-color: cyan; -fx-border-width: 1.5;");
        scegliAvversario.setAlignment(Pos.CENTER);
        iniziaHD();
    }
    public void getMc(MainController mainController){
        mc = mainController;
    }
    public Giocatore planetSelection(){
        int index;
        if (giocatoreSelezionato == null) {
            do {
                index = (int) (Math.random() * (gameData.getNumero()));
            } while (index == gameData.getTurnoCorrente());
            return gameData.getGiocatoriPartita().get(index);
        } else
            return giocatoreSelezionato;
    }
    public Task<Void> startSelection(){
        giocatoreSelezionato = null;
        halfDonut.setVisible(false);
        mc.startSelectionMC();
        ovalPane.getChildren().addAll(progressBar, scegliAvversario);
        resetHD.set(true);
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (int secondo = 1; secondo <= 20; secondo++) {
                    if (planetSelected) {
                        succeeded();
                        return null;
                    }
                    updateProgress(secondo, 10);
                    Thread.sleep(500);
                }
                return null;
            }
        };
        planetSelected = false;
        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        return task;
    }
    public void dannoSfera(Giocatore ggAttaccato){
        Timeline animation;
        Material matOriginale;
        for (int j = 0; j < n; j++){
            if (ggAttaccato.getNome().equals(((Text) pianeti[j].getChildren().getLast()).getText())) {
                matOriginale = ((Sphere) pianeti[j].getChildren().getFirst()).getMaterial();
                animation = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(((Sphere) pianeti[j].getChildren().getFirst()).materialProperty(), redMaterial)),
                        new KeyFrame(Duration.seconds(1), new KeyValue(((Sphere) pianeti[j].getChildren().getFirst()).materialProperty(), matOriginale))
                );
                animation.playFromStart();
                break;
            }
        }
    }
    public void fineSelezione(){
        halfDonut.setVisible(true);
        ovalPane.getChildren().removeAll(progressBar, scegliAvversario);
        resetHD.set(true);
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
            if (ovalPane.getChildren().contains(progressBar)){
                scegliAvversario.setPrefWidth(centroX/1.5);
                progressBar.setPrefWidth(centroX/1.5);
                progressBar.setLayoutX(centroX-centroX/3);
                progressBar.setLayoutY(centroY+30);
                scegliAvversario.setLayoutX(centroX-centroX/3);
                scegliAvversario.setLayoutY(centroY);
            } else {
                ovalPane.getChildren().remove(halfDonut);
                outerArc.setRadiusX(orx);
                outerArc.setRadiusY(ory);
                innerArc.setRadiusX(irx);
                innerArc.setRadiusY(iry);
                halfDonut = Shape.subtract(outerArc, innerArc);
                halfDonut.setFill(javafx.scene.paint.Color.rgb(0, 191, 255, 0.2));
                ovalPane.getChildren().add(halfDonut);
                halfDonut.setTranslateX(centroX-100);
                halfDonut.setTranslateY(centroY*2-100);
            }
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
    }
    //FINE HALF DONUTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
    //INIZIO ANIMAZIONE E ABBELLIMENTO SFEREEEEEEEEEEEEEEEEEEEEEEEEEEEEE
    private void rotazionePianeti(int i) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                pianeti[i].getChildren().getFirst().rotateProperty().set(pianeti[i].getChildren().getFirst().getRotate() + 0.2);
            }
        };
        timer.start();
    }

    private Image textures(int i){
        return switch (i) {
            case 7 -> new Image(Objects.requireNonNull(getClass().getResource("mercury.jpg")).toString());
            case 6 -> new Image(Objects.requireNonNull(getClass().getResource("venus.jpg")).toString());
            case 5 -> new Image(Objects.requireNonNull(getClass().getResource("earth.jpg")).toString());
            case 4 -> new Image(Objects.requireNonNull(getClass().getResource("mars.jpg")).toString());
            case 3 -> new Image(Objects.requireNonNull(getClass().getResource("jupiter.jpg")).toString());
            case 2 -> new Image(Objects.requireNonNull(getClass().getResource("saturn.jpg")).toString());
            case 1 -> new Image(Objects.requireNonNull(getClass().getResource("uranus.jpg")).toString());
            case 0 -> new Image(Objects.requireNonNull(getClass().getResource("neptune.jpg")).toString());
            default -> new Image("null");
        };
    }
    private static void posizionaSfere() {
        for (int i = 0; i < n; i++) {
            int indice = ((n - turnoDi) + i) % n;
            if (indice == 2) {
                ((Cylinder) pianeti[indice].getChildren().get(1)).setRadius((60 * Math.min(centroX, centroY)) / 300 + 30);
            }
            ((Sphere) pianeti[indice].getChildren().get(0)).setRadius((60 * Math.min(centroX, centroY)) / 300);
            pianeti[indice].setTranslateX((centroX - ((Sphere) pianeti[indice].getChildren().getFirst()).getRadius() * 2 + 50) * Math.cos((2 * Math.PI * (i + 1) / n)+(Math.PI/2)) + centroX);
            pianeti[indice].setTranslateY((centroY - ((Sphere) pianeti[indice].getChildren().getFirst()).getRadius() * 2 + 40) * Math.sin((2 * Math.PI * (i + 1) / n)+(Math.PI/2)) + centroY);
            pianeti[indice].setScaleX(i == n-1 ? 0.6 : 1);
            pianeti[indice].setScaleY(i == n-1 ? 0.6 : 1);
        }
    }
    //FINE SFERE

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
        KeyValue kv1, kv2, kv3, kv4;
        KeyFrame kf;
        int i;
        Timeline timeline = new Timeline();
        turnoDi = (turnoDi == (n-1)) ? 0 : turnoDi+1;
        gameData.setTurnoCorrente(turnoDi);
        for (int ind = 0; ind < n-1; ind++) {
            i = ind % n;
            kv1 = new KeyValue(pianeti[i].translateXProperty(), pianeti[i+1].getTranslateX());
            kv2 = new KeyValue(pianeti[i].translateYProperty(), pianeti[i+1].getTranslateY());
            kv3 = new KeyValue(pianeti[i].scaleXProperty(), pianeti[i+1].getScaleX());
            kv4 = new KeyValue(pianeti[i].scaleYProperty(), pianeti[i+1].getScaleY());
            kf = new KeyFrame(Duration.seconds(1), kv1, kv2, kv3, kv4);
            timeline.getKeyFrames().add(kf);
        }
        kv1 = new KeyValue(pianeti[n-1].translateXProperty(), pianeti[0].getTranslateX());
        kv2 = new KeyValue(pianeti[n-1].translateYProperty(), pianeti[0].getTranslateY());
        kv3 = new KeyValue(pianeti[n-1].scaleXProperty(), pianeti[0].getScaleX());
        kv4 = new KeyValue(pianeti[n-1].scaleYProperty(), pianeti[0].getScaleY());
        kf = new KeyFrame(Duration.seconds(1), kv1, kv2, kv3, kv4);
        timeline.getKeyFrames().add(kf);
        timeline.playFromStart();
        timeline.setOnFinished(event -> posizionaSfere());
    }
}