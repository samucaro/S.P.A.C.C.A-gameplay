package com.example.gioco;

import javafx.animation.*;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    private TabelloneGiocoController mc;
    private static Group[] pianeti;
    private PhongMaterial redMaterial;
    private PhongMaterial orangeMaterial;
    public Giocatore giocatoreSelezionato;
    private ProgressBar progressBar;
    private Text scegliAvversario;
    private Arc arcoInterno;
    private Arc arcoEsterno;
    private static int currentSphere;
    private Integer winnerSphere;
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
    private Pane ovalPane;

    @FXML
    public void initialize() {
        gameData = GameData.getInstance();
        turnoDi = gameData.getTurnoCorrente();
        currentSphere = gameData.getNumero()-1;
        pianeti = new Group[gameData.getNumero()];
        redMaterial = new PhongMaterial(Color.RED);
        orangeMaterial = new PhongMaterial(Color.ORANGE);
        scegliAvversario = new Text("Scegli chi vuoi attaccare");
        progressBar = new ProgressBar();
        giocatoreSelezionato = null;
        winnerSphere = null;
        planetSelected = false;
        creaGruppoPianeti();
        impostaStile();
        iniziaHD();
    }

    //Imposta la struttura dei pianeti e la loro disposizione nel tabellone, con nome e vita di ognuno
    private void creaGruppoPianeti() {
        for(int i = 0; i < gameData.getNumero(); i++) {
            pianeti[i] = new Group();
            pianeti[i].getChildren().add(new Sphere(60));
            Image texture = textures(i);
            PhongMaterial material1 = new PhongMaterial();
            material1.setDiffuseMap(texture);
            ((Sphere) pianeti[i].getChildren().getFirst()).setMaterial(material1);
            pianeti[i].getChildren().getFirst().setRotationAxis(Rotate.Y_AXIS);
            rotazionePianeti(i);
            if(i==2) {
                Cylinder ring = new Cylinder(80, 3);
                PhongMaterial material2 = new PhongMaterial();
                material2.setDiffuseColor(Color.BEIGE);
                ring.setMaterial(material2);
                ring.setRotationAxis(Rotate.Y_AXIS);
                ring.setRotate(75);
                pianeti[i].getChildren().add(ring);
            }
            int indice = ((gameData.getNumero()-1) - i) % gameData.getNumero();
            Text vitaGiocatore = new Text("hp: " + gameData.getGiocatoriPartita().get(indice).getHpRimanente() + "/5");
            Text nomeGiocatore = new Text(gameData.getGiocatoriPartita().get(indice).getNome());
            vitaGiocatore.setFont(Font.font("Game of Thrones", 16));
            vitaGiocatore.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 0.5");
            nomeGiocatore.setFont(Font.font("Game of Thrones", 17));
            nomeGiocatore.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 0.5");
            vitaGiocatore.setLayoutX(-vitaGiocatore.getBoundsInLocal().getWidth()/2);
            vitaGiocatore.setLayoutY(22-vitaGiocatore.getBoundsInLocal().getHeight()/2);
            nomeGiocatore.setLayoutX(-nomeGiocatore.getBoundsInLocal().getWidth()/2);
            nomeGiocatore.setLayoutY(-5-nomeGiocatore.getBoundsInLocal().getHeight()/2);
            pianeti[i].getChildren().addAll(vitaGiocatore, nomeGiocatore);
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
    private void gestoreEventiPianeti(int ind) {
        pianeti[ind].setOnMouseEntered(event -> {
            pianeti[ind].setScaleX(pianeti[ind].getScaleX()+0.1);
            pianeti[ind].setScaleY(pianeti[ind].getScaleY()+0.1);
        });
        pianeti[ind].setOnMouseExited(event -> {
            pianeti[ind].setScaleX(pianeti[ind].getScaleX()-0.1);
            pianeti[ind].setScaleY(pianeti[ind].getScaleY()-0.1);
        });
        pianeti[ind].setOnMouseClicked(event -> {
            for(int j = 0; j < gameData.getNumero(); j++) {
                if(gameData.getGiocatoriPartita().get(j).getNome().equals(((Text) pianeti[ind].getChildren().getLast()).getText())) {
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
        halfDonut = Shape.subtract(arcoEsterno, arcoInterno);
        ovalPane.getChildren().add(halfDonut);
    }

    //Metodi per ridimensionamento
    private void setScenaX(double x){
        centroX=x/2;
        if(winnerSphere == null) {
            posizionaPianeti();
            reShape();
        }
        else {
            mettiSferaVincitrice();
        }
    }
    private void setScenaY(double y){
        centroY=y/2;
        if (winnerSphere == null) {
            posizionaPianeti();
            reShape();
        }
        else {
            mettiSferaVincitrice();
        }
    }
    public void reShape() {
        System.out.println("RESHAPE");
        double h = (60 * Math.min(centroX, centroY)) / 300;
        orx = h+centroX*1/2;
        ory = h+centroY*1/1.5;
        irx = 2*h;
        iry = centroY/5+h;
        boolean checkVis = halfDonut.isVisible();
        if(ovalPane.getChildren().contains(progressBar)) {
            //ovalPane.getChildren().remove(halfDonut);
            halfDonut.setVisible(false);
            scegliAvversario.setWrappingWidth(centroX/1.5);
            progressBar.setPrefWidth(centroX/1.5);
            progressBar.setLayoutX(centroX-centroX/3);
            progressBar.setLayoutY(centroY+30);
            scegliAvversario.setLayoutX(centroX-centroX/3);
            scegliAvversario.setLayoutY(centroY);
        }
        else {
            ovalPane.getChildren().remove(halfDonut);
            arcoEsterno.setRadiusX(orx);
            arcoEsterno.setRadiusY(ory);
            arcoInterno.setRadiusX(irx);
            arcoInterno.setRadiusY(iry);
            halfDonut = Shape.subtract(arcoEsterno, arcoInterno);
            halfDonut.setFill(Color.rgb(0, 191, 255, 0.2));
            ovalPane.getChildren().add(halfDonut);
            halfDonut.setVisible(checkVis);
            halfDonut.setTranslateX(centroX - 100);
            halfDonut.setTranslateY(centroY * 2 - 100);
        }
    }

    //Imposta la rotazione 3D dei pianeti
    private void rotazionePianeti(int i) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                pianeti[i].getChildren().getFirst().rotateProperty().set(pianeti[i].getChildren().getFirst().getRotate() + 0.2);
            }
        };
        timer.start();
    }

    //Imposta i pianeti morti
    public static void setMortoOP(Giocatore giocatoreMorto) {
        for(int j = 0; j < gameData.getNumero(); j++) {
            if(giocatoreMorto.getNome().equals(((Text) pianeti[j].getChildren().getLast()).getText())) {
                pianeti[j].setMouseTransparent(true);
                ((Sphere) pianeti[j].getChildren().getFirst()).setMaterial(new PhongMaterial(Color.WHITE));
                ((Text) pianeti[j].getChildren().get(pianeti[j].getChildren().size()-2)).setText("ELIMINATO");
                pianeti[j].getChildren().get(pianeti[j].getChildren().size()-2).setLayoutX(-pianeti[j].getChildren().get(pianeti[j].getChildren().size()-2).getBoundsInLocal().getWidth()/2);
                pianeti[j].getChildren().get(pianeti[j].getChildren().size()-2).setLayoutY(22-pianeti[j].getChildren().get(pianeti[j].getChildren().size()-2).getBoundsInLocal().getHeight()/2);
            }
        }
    }

    //Aggiorna la riga di testo della vita di ogni giocatore con la vita corrente di ognuno
    public static void setVita(Giocatore ggDanneggiato) {
        for(int j = 0; j < gameData.getNumero(); j++) {
            if(ggDanneggiato.getNome().equals(((Text) pianeti[j].getChildren().getLast()).getText())) {
                ((Text) pianeti[j].getChildren().get(pianeti[j].getChildren().size()-2)).setText("hp: " + ggDanneggiato.getHpRimanente() + "/5");
            }
        }
    }

    //Posiziona i pianeti nel modo corretto
    public static void posizionaPianeti() {
        double scale;
        int indice;
        for(int i = 0; i < gameData.getNumero(); i++) {
            indice = ((gameData.getNumero() - turnoDi) + i) % gameData.getNumero();
            if(indice == 2) {
                ((Cylinder) pianeti[indice].getChildren().get(1)).setRadius((60 * Math.min(centroX, centroY)) / 300 + 30);
            }
            ((Sphere) pianeti[indice].getChildren().getFirst()).setRadius((60 * Math.min(centroX, centroY)) / 300);
            pianeti[indice].setTranslateX((centroX - ((Sphere) pianeti[indice].getChildren().getFirst()).getRadius() * 2 + 50) * Math.cos((2 * Math.PI * (i + 1) / gameData.getNumero())+(Math.PI/2)) + centroX);
            pianeti[indice].setTranslateY((centroY - ((Sphere) pianeti[indice].getChildren().getFirst()).getRadius() * 2 + 40) * Math.sin((2 * Math.PI * (i + 1) / gameData.getNumero())+(Math.PI/2)) + centroY);
            if(i == gameData.getNumero()-1) {
                pianeti[indice].getChildren().get(pianeti[indice].getChildren().size() - 2).setVisible(((Text) pianeti[indice].getChildren().get(pianeti[indice].getChildren().size() - 2)).getText().equals("ELIMINATO"));
                scale = 0.6;
            }
            else {
                pianeti[indice].getChildren().get(pianeti[indice].getChildren().size() - 2).setVisible(true);
                scale = 1;
            }
            pianeti[indice].setScaleX(scale);
            pianeti[indice].setScaleY(scale);
        }

    }

    //Gestisce l'evento del cambio turno
    public Timeline cambiaTurno() {
        System.out.println("CAMBIA TURNO");
        pianeti[currentSphere].getChildren().get(pianeti[currentSphere].getChildren().size() - 2).setVisible(true);
        KeyValue kv1, kv2, kv3, kv4;
        KeyFrame kf;
        int i;
        Timeline timeline = new Timeline();
        turnoDi = (turnoDi == (gameData.getNumero()-1)) ? 0 : turnoDi+1;
        currentSphere = currentSphere == 0 ? gameData.getNumero()-1 : currentSphere-1;
        gameData.setTurnoCorrente(turnoDi);
        for (int ind = 0; ind < gameData.getNumero(); ind++) {
            i = ind % gameData.getNumero();
            kv1 = new KeyValue(pianeti[i].translateXProperty(), pianeti[(i + 1) % gameData.getNumero()].getTranslateX());
            kv2 = new KeyValue(pianeti[i].translateYProperty(), pianeti[(i + 1) % gameData.getNumero()].getTranslateY());
            kv3 = new KeyValue(pianeti[i].scaleXProperty(), pianeti[(i + 1) % gameData.getNumero()].getScaleX());
            kv4 = new KeyValue(pianeti[i].scaleYProperty(), pianeti[(i + 1) % gameData.getNumero()].getScaleY());
            kf = new KeyFrame(Duration.seconds(0.8), kv1, kv2, kv3, kv4);
            timeline.getKeyFrames().add(kf);
        }
        return timeline;
    }

    public void getMainController(TabelloneGiocoController tabelloneGiocoController) {
        mc = tabelloneGiocoController;
        ovalPane.prefWidthProperty().bind(mc.stackPane.widthProperty());
        ovalPane.prefHeightProperty().bind(mc.stackPane.heightProperty());
        mc.stackPane.widthProperty().addListener((observable, oldValue, newValue) -> setScenaX((Double) newValue));
        mc.stackPane.heightProperty().addListener((observable, oldValue, newValue) -> setScenaY((Double) newValue));
    }

    //Ritorna un giocatore casuale fra quelli presenti
    public Giocatore planetSelection(){
        if(giocatoreSelezionato == null) {
            int index;
            do {
                index = (int) (Math.random() * (gameData.getNumero()));
            } while((index == gameData.getTurnoCorrente()) || (gameData.getGiocatoriPartita().get(index).getHpRimanente() == 0));
            return gameData.getGiocatoriPartita().get(index);
        }
        else {
            return giocatoreSelezionato;
        }
    }

    //Inizia la task di scelta del giocatore verso cui compiere l'effetto della carta scartata
    public Task<Void> startSelection(){
        giocatoreSelezionato = null;
        halfDonut.setVisible(false);
        mc.startSelectionMC();
        ovalPane.getChildren().addAll(progressBar, scegliAvversario);
        reShape();
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                pianeti[currentSphere].setMouseTransparent(true);
                for(int secondo = 1; secondo <= 10; secondo++) {
                    if(planetSelected) {
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

    //Colora il pianeta colpito di rosso, di arancione altrimenti
    public void dannoSfera(Giocatore ggAttaccato, boolean c){
        Timeline animation;
        PhongMaterial matOriginale = new PhongMaterial();
        for(int j = 0; j < gameData.getNumero(); j++) {
            if(ggAttaccato.getNome().equals(((Text) pianeti[j].getChildren().getLast()).getText())) {
                matOriginale.setDiffuseMap(textures(j));
                if(ggAttaccato.getHpRimanente() == 0) {
                    matOriginale = new PhongMaterial(Color.WHITE);
                    }
                animation = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(((Sphere) pianeti[j].getChildren().getFirst()).materialProperty(), c ? redMaterial : orangeMaterial)),
                        new KeyFrame(Duration.seconds(1), new KeyValue(((Sphere) pianeti[j].getChildren().getFirst()).materialProperty(), matOriginale))
                );
                animation.playFromStart();
                break;
            }
        }
    }

    //Reimposta il tabellone completata la task di selezione del giocatore da attaccare
    public void fineSelezione(){
        halfDonut.setVisible(Objects.equals(TabelloneGiocoController.getNomeVincitore(), ""));
        ovalPane.getChildren().removeAll(progressBar, scegliAvversario);
        pianeti[currentSphere].setMouseTransparent(false);
        reShape();
    }

    //Blocca il giocatore eliminato
    public void fineGiocoGrafica() {
        ovalPane.getChildren().remove(halfDonut);
        for(int j = 0; j < gameData.getNumero(); j++) {
            if(!((Text) pianeti[j].getChildren().get(pianeti[j].getChildren().size()-2)).getText().equals("ELIMINATO")) {
                winnerSphere = j;
                (pianeti[j].getChildren().get(pianeti[j].getChildren().size()-2)).setVisible(false);
                spostaSferaVincitrice();
            } else {
                ovalPane.getChildren().remove(pianeti[j]);
            }
        }
    }

    public double getScala(){
        return 0.03 * ((20 * Math.min(centroX, centroY)) / 50);
    }

    //Impostano la grafica di visualizzazione del giocatore vincitore
    private void spostaSferaVincitrice() {
        ((Sphere) pianeti[winnerSphere].getChildren().getFirst()).setRadius(50);
        Timeline timeline = new Timeline();
        KeyValue scx = new KeyValue(pianeti[winnerSphere].scaleXProperty(), getScala());
        KeyValue scy = new KeyValue(pianeti[winnerSphere].scaleYProperty(), getScala());
        KeyValue kv1 = new KeyValue(pianeti[winnerSphere].translateXProperty(), centroX);
        KeyValue kv2 = new KeyValue(pianeti[winnerSphere].translateYProperty(), centroY);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv1, kv2, scx, scy);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        timeline.setOnFinished(actionEvent -> mettiSferaVincitrice());
    }

    public void mettiSferaVincitrice(){
        pianeti[winnerSphere].setScaleX(getScala());
        pianeti[winnerSphere].setScaleY(getScala());
        pianeti[winnerSphere].setTranslateX(centroX);
        pianeti[winnerSphere].setTranslateY(centroY);
    }
}