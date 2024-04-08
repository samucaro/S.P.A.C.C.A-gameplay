package com.example.gioco;

import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.util.Objects;

public class OvalPaneController {
    private static GameData gameData;
    @FXML
    private Pane ovalPane;
    private MainController mc;
    private static Group[] pianeti;
    private PhongMaterial redMaterial;
    private Giocatore giocatoreSelezionato;
    private ProgressBar progressBar;
    private AnimationTimer timer;
    private Text scegliAvversario;
    private Arc arcoInterno;
    private Arc arcoEsterno;
    private static double centroX;
    private static double centroY;
    private boolean planetSelected;
    private static double orx = 230.0;
    private static double ory = 250.0;
    private static double irx = 90.0;
    private static double iry = 130.0;
    private static Shape halfDonut;
    private static int turnoDi;
    @FXML
    public void initialize() {
        gameData = GameData.getInstance();
        turnoDi = gameData.getTurnoCorrente();
        pianeti = new Group[gameData.getNumero()];
        redMaterial = new PhongMaterial(Color.RED);
        scegliAvversario = new Text("Scegli chi vuoi attaccare");
        progressBar = new ProgressBar();
        giocatoreSelezionato = null;
        planetSelected = false;
        creaGruppoPianeti();
        impostaStile();
        iniziaHD();
    }

    //Imposta la struttura dei pianeti e la loro disposizione nel tabellone
    private void creaGruppoPianeti() {
        for (int i = 0; i < gameData.getNumero(); i++) {
            pianeti[i] = new Group();
            pianeti[i].getChildren().addFirst(new Sphere(60));
            Image texture = textures(i);
            PhongMaterial material1 = new PhongMaterial();
            material1.setDiffuseMap(texture);
            ((Sphere) pianeti[i].getChildren().getFirst()).setMaterial(material1);
            pianeti[i].getChildren().getFirst().setRotationAxis(Rotate.Y_AXIS);
            rotazionePianeti(i);
            if (i==2){
                Cylinder ring = new Cylinder(80, 3);
                PhongMaterial material2 = new PhongMaterial();
                material2.setDiffuseColor(Color.BEIGE);
                ring.setMaterial(material2);
                ring.setRotationAxis(Rotate.Y_AXIS);
                ring.setRotate(75);
                pianeti[i].getChildren().add(ring);
            }
            int indice = ((gameData.getNumero() - 1) - i) % gameData.getNumero();
            Text nomeGiocatore = new Text(gameData.getGiocatoriPartita().get(indice).getNome());
            nomeGiocatore.setFont(Font.font("Game of Thrones", 17));
            nomeGiocatore.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 0.5");
            double centerX = -nomeGiocatore.getBoundsInLocal().getWidth()/2;
            double centerY = -5-nomeGiocatore.getBoundsInLocal().getHeight()/2;
            nomeGiocatore.setLayoutX(centerX);
            nomeGiocatore.setLayoutY(centerY);
            pianeti[i].getChildren().add(nomeGiocatore);
            gestoreEventiPianeti(i);
            ovalPane.getChildren().add(pianeti[i]);
        }
    }
    //Carica le immagini dei pianeti
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
    //Imposta lo stile del testo mostrato nella scelta del giocatore
    private void impostaStile() {
        progressBar.setStyle("-fx-background-color: cyan; -fx-background-radius: 20;");
        scegliAvversario.setFont(Font.font("Game of Thrones", 18));
        scegliAvversario.setTextAlignment(TextAlignment.CENTER);
        scegliAvversario.setFill(Color.WHITE);
        scegliAvversario.setStroke(Color.BLACK);
        scegliAvversario.setStrokeWidth(0.7);
    }
    //gestisce i possibili eventi sui pianeti
    public void gestoreEventiPianeti(int ind) {
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
    }
    //Creazione Donut e gestione ridimensionamento
    private void iniziaHD() {
        arcoEsterno = new Arc(100.0, 100.0, orx, ory, 0.0, 180.0);
        arcoInterno = new Arc(100.0, 100.0, irx, iry, 0.0, 180.0);
        arcoEsterno.setType(ArcType.ROUND);
        arcoInterno.setType(ArcType.ROUND);
        halfDonut = Shape.subtract(arcoEsterno, arcoInterno); //permette di creare l'arco facendo la sottrazione tra quello esterno e quello interno
        ovalPane.getChildren().add(halfDonut);
    }
    public void setScenaX(double x){
        centroX=x/2;
        posizionaSfere();
        reShape();
    }
    public void setScenaY(double y){
        centroY=y/2;
        posizionaSfere();
        reShape();
    }
    public void reShape() {
        double h = (60 * Math.min(centroX, centroY)) / 300;
        orx=h + centroX*1/2;
        ory=h + centroY*1/1.5;
        irx=2*h;
        iry=centroY/5+h;
        if (ovalPane.getChildren().contains(progressBar)){
            ovalPane.getChildren().remove(halfDonut);
            scegliAvversario.setWrappingWidth(centroX/1.5);
            progressBar.setPrefWidth(centroX/1.5);
            progressBar.setLayoutX(centroX-centroX/3);
            progressBar.setLayoutY(centroY+30);
            scegliAvversario.setLayoutX(centroX-centroX/3);
            scegliAvversario.setLayoutY(centroY);
        } else {
            ovalPane.getChildren().remove(halfDonut);
            arcoEsterno.setRadiusX(orx);
            arcoEsterno.setRadiusY(ory);
            arcoInterno.setRadiusX(irx);
            arcoInterno.setRadiusY(iry);
            halfDonut = Shape.subtract(arcoEsterno, arcoInterno);
            halfDonut.setFill(Color.rgb(0, 191, 255, 0.2));
            ovalPane.getChildren().add(halfDonut);
            halfDonut.setTranslateX(centroX-100);
            halfDonut.setTranslateY(centroY*2-100);
        }
    }
    private void rotazionePianeti(int i) {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                pianeti[i].getChildren().getFirst().rotateProperty().set(pianeti[i].getChildren().getFirst().getRotate() + 0.2);
            }
        };
        timer.start();
    }
    private static void posizionaSfere() {
        for (int i = 0; i < gameData.getNumero(); i++) {
            int indice = ((gameData.getNumero() - turnoDi) + i) % gameData.getNumero();
            if (indice == 2) {
                ((Cylinder) pianeti[indice].getChildren().get(1)).setRadius((60 * Math.min(centroX, centroY)) / 300 + 30);
            }
            ((Sphere) pianeti[indice].getChildren().get(0)).setRadius((60 * Math.min(centroX, centroY)) / 300);
            pianeti[indice].setTranslateX((centroX - ((Sphere) pianeti[indice].getChildren().getFirst()).getRadius() * 2 + 50) * Math.cos((2 * Math.PI * (i + 1) / gameData.getNumero())+(Math.PI/2)) + centroX);
            pianeti[indice].setTranslateY((centroY - ((Sphere) pianeti[indice].getChildren().getFirst()).getRadius() * 2 + 40) * Math.sin((2 * Math.PI * (i + 1) / gameData.getNumero())+(Math.PI/2)) + centroY);
            pianeti[indice].setScaleX(i == gameData.getNumero()-1 ? 0.6 : 1);
            pianeti[indice].setScaleY(i == gameData.getNumero()-1 ? 0.6 : 1);
        }
    }
    public void cambiaTurno() {
        KeyValue kv1, kv2, kv3, kv4;
        KeyFrame kf;
        int i;
        Timeline timeline = new Timeline();
        turnoDi = (turnoDi == (gameData.getNumero()-1)) ? 0 : turnoDi+1;
        gameData.setTurnoCorrente(turnoDi);
        for (int ind = 0; ind < gameData.getNumero()-1; ind++) {
            i = ind % gameData.getNumero();
            kv1 = new KeyValue(pianeti[i].translateXProperty(), pianeti[i+1].getTranslateX());
            kv2 = new KeyValue(pianeti[i].translateYProperty(), pianeti[i+1].getTranslateY());
            kv3 = new KeyValue(pianeti[i].scaleXProperty(), pianeti[i+1].getScaleX());
            kv4 = new KeyValue(pianeti[i].scaleYProperty(), pianeti[i+1].getScaleY());
            kf = new KeyFrame(Duration.seconds(1), kv1, kv2, kv3, kv4);
            timeline.getKeyFrames().add(kf);
        }
        kv1 = new KeyValue(pianeti[gameData.getNumero()-1].translateXProperty(), pianeti[0].getTranslateX());
        kv2 = new KeyValue(pianeti[gameData.getNumero()-1].translateYProperty(), pianeti[0].getTranslateY());
        kv3 = new KeyValue(pianeti[gameData.getNumero()-1].scaleXProperty(), pianeti[0].getScaleX());
        kv4 = new KeyValue(pianeti[gameData.getNumero()-1].scaleYProperty(), pianeti[0].getScaleY());
        kf = new KeyFrame(Duration.seconds(1), kv1, kv2, kv3, kv4);
        timeline.getKeyFrames().add(kf);
        timeline.playFromStart();
        timeline.setOnFinished(event -> posizionaSfere());
    }
    public void getMc(MainController mainController){
        mc = mainController;
        ovalPane.prefWidthProperty().bind(mc.stackPane.widthProperty());
        ovalPane.prefHeightProperty().bind(mc.stackPane.heightProperty());
        mc.stackPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            setScenaX((Double) newValue);
        });
        mc.stackPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            setScenaY((Double) newValue);
        });
    }
    //Da il giocatore associato al pianeta selezionato per essere attaccato
    public Giocatore planetSelection(){
        if (giocatoreSelezionato == null) {
            int index;
            do {
                index = (int) (Math.random() * (gameData.getNumero()));
            } while (index == gameData.getTurnoCorrente());
            return gameData.getGiocatoriPartita().get(index);
        } else {
            return giocatoreSelezionato;
        }
    }
    public Task<Void> startSelection(){
        giocatoreSelezionato = null;
        halfDonut.setVisible(false);
        mc.startSelectionMC();
        ovalPane.getChildren().addAll(progressBar, scegliAvversario);
        reShape();
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
        for (int j = 0; j < gameData.getNumero(); j++){
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
        reShape();
    }
}