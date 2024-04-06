package com.example.gioco;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.util.ArrayList;

public class MainController {
    private double centroX;
    private double centroY;
    private double xOffset;
    private double yOffset;
    private ArrayList<Integer> numNodiMano;
    private boolean checkInit;
    private GameData gameData;
    @FXML
    public OvalPaneController ovalPaneController;
    @FXML
    private Button saveButton;
    @FXML
    private Button turnButton;
    @FXML
    private StackPane stackPane;
    @FXML
    private HBox mazzoEScarti;
    @FXML
    private ImageView scarti;
    @FXML
    private ProgressBar barraVita;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    public void initialize() {
        gameData = GameData.getInstance();
        checkInit = false;
        impostaCose();
        mazzoEScarti.getChildren().getFirst().setOnMouseEntered(event1 -> {
            mazzoEScarti.getChildren().getFirst().setScaleX(1.1);
            mazzoEScarti.getChildren().getFirst().setScaleY(1.1);
        });
        mazzoEScarti.getChildren().getFirst().setOnMouseExited(event2 -> {
            mazzoEScarti.getChildren().getFirst().setScaleX(1.0);
            mazzoEScarti.getChildren().getFirst().setScaleY(1.0);
        });
    }
    public void impostaCose(){
        anchorPane.prefWidthProperty().bind(stackPane.widthProperty()); //permette di legare l'AnchorPane alle dimensioni dello StackPane
        anchorPane.prefHeightProperty().bind(stackPane.heightProperty());
        stackPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            centroX = (double) newValue;
            scalaMazzo();
            mazzoEScarti.setLayoutX(centroX/2 - (checkInit ? mazzoEScarti.getWidth() : 140)/2);
            mettiCarte(false);
            if (mazzoEScarti.getHeight() != 0)
                checkInit=true;
        });
        stackPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            centroY = (double) newValue;
            scalaMazzo();
            mazzoEScarti.setLayoutY(centroY/2 - (checkInit ? mazzoEScarti.getHeight() : 90)/2);
            mettiCarte(false);
            if (mazzoEScarti.getHeight()!=0)
                checkInit=true;
        });
        mettiCarte(true);
        mettiVita();
        ovalPaneController.getMc(this);
    }

    public void scalaMazzo(){
        double scala = getScala();
        mazzoEScarti.setScaleX(scala);
        mazzoEScarti.setScaleY(scala);
    }

    public double getScala(){
        return 0.01 * (10 * Math.min(centroX, centroY)) / 50;
    }
    public void scartaCarte(Carta c, Giocatore g){
        g.scarta(c);
        gameData.getMazzo().scarta(c);
        scarti.setImage(c.getImage().getImage());
    }

    public void mettiCarte(boolean var) {
        numNodiMano = new ArrayList<>();
            for(int i = 0; i < anchorPane.getChildren().size(); i++) {
                if (anchorPane.getChildren().get(i) instanceof ImageView) {
                    //System.out.println("Carte: " + ((ImageView) anchorPane.getChildren().get(i)).getImage().getUrl());
                    if (var) {
                        anchorPane.getChildren().remove(i);
                        i--;
                    } else {
                        numNodiMano.add(i);
                    }
                }
            }
        if (var) {
            PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
            pause.play();
            pause.setOnFinished(event -> {
                ImageView imageView;
                ArrayList<Carta> manoCorrente = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano();
                for (int i = 0; i< manoCorrente.size(); i++) {
                    imageView = manoCorrente.get(i).getImage();
                    anchorPane.getChildren().add(imageView);
                    scala(imageView);
                    listenerCarta(imageView, i);
                    numNodiMano.add(anchorPane.getChildren().indexOf(imageView));
                }
                spostaCarta();

            });
        } else {
            for (int i : numNodiMano) {
                scala((ImageView) anchorPane.getChildren().get(i));
            }
            spostaCarta();
        }
    }
    public void spostaCarta(){
        double cX = centroX/2;
        double cY = centroY-20;
        double semiLarghezza = centroX/5;
        double altezza = 10 + centroY/5;
        int numOggetti = numNodiMano.size();
        double[][] coordinate = calcolaCoordinateArco(cX, cY, semiLarghezza, altezza, numOggetti);
        double[] angoli = new double[numOggetti];
        for (int i = 0; i < numNodiMano.size(); i++) {
            angoli[i] = numOggetti <= 1 ? 0 : -(-30 + i * ((double) 60/(numOggetti-1)));
            ImageView imageView = (ImageView) anchorPane.getChildren().get(numNodiMano.get(i));
            imageView.setLayoutX(coordinate[i][0] - imageView.getFitWidth()/2);
            imageView.setLayoutY(coordinate[i][1] - imageView.getFitWidth()*1.29);
            imageView.setRotate(angoli[i]);
        }
    }
    public void listenerCarta(ImageView imageView, int i){
        Carta cartaAttuale = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().get(i);
        imageView.setOnMouseEntered(event1 -> {
            imageView.setScaleX(1.1);
            imageView.setScaleY(1.1);
            imageView.setOnMouseExited(event2 -> {
                imageView.setScaleX(1.0);
                imageView.setScaleY(1.0);
            });
        });
        imageView.setOnMousePressed(event1 -> {
            xOffset = event1.getSceneX() - imageView.getTranslateX();
            yOffset = event1.getSceneY() - imageView.getTranslateY();
        });
        imageView.setOnMouseDragged(event2 -> {
            imageView.setTranslateX(event2.getSceneX() - xOffset);
            imageView.setTranslateY(event2.getSceneY() - yOffset);
        });
        imageView.setOnMouseReleased(event -> {
            double finalX = event.getSceneX();
            double finalY = event.getSceneY();
            imageView.setTranslateX(0);
            imageView.setTranslateY(0);
            if (finalX<centroX/2+100 && finalX>centroX/2-100 && finalY<centroY/2+100 && finalY>centroY/2-100){
                cartaAttuale.usaAbilita(ovalPaneController, this);
            }
        });
    }

    public void scala(ImageView c){
        double dim = (5 * Math.min(centroX, centroY)) / 37;
        c.setFitWidth(dim);
    }
    public void mettiVita(){
        int vita = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getHpRimanente();
        barraVita.setProgress((double) vita/5);
    }
    public double[][] calcolaCoordinateArco(double cX, double cY, double semiLarghezza, double altezza, int numOggetti) {
        double[][] coordinate = new double[numOggetti][2];
        double angoloStep = Math.PI / (numOggetti - 1);
        double angolo;
        double x;
        double y;
        for (int i = 0; i < numOggetti; i++) {
            angolo = i * angoloStep;
            x = cX + semiLarghezza * Math.cos(angolo);
            y = cY - altezza * Math.sin(angolo);
            coordinate[i][0] = x;
            coordinate[i][1] = y;
        }
        if (numOggetti == 1) {
            coordinate[0][0] = cX;
            coordinate[0][1] = cY - altezza;
        }
        return coordinate;
    }

    @FXML
    public void handleTurnButton() {
        turnButton.setDisable(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        ovalPaneController.cambiaTurno();
        pause.play();
        mettiCarte(true);
        pause.setOnFinished(event -> {
            turnButton.setDisable(false);
            mettiVita();
        });
    }
    @FXML
    public void salvaPartita() {
        System.out.println(gameData.getGiocatoriPartita());
    }
    public void pescataInizialeGiocatore() {
        Giocatore player = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente());
        for(int i = 1; i <= 2; i++) {
            player.addCarta(gameData.getMazzo().pesca());
            System.out.println("Ho pescato");
        }
        mettiCarte(true);
    }
    public void startSelectionMC(){
        anchorPane.setDisable(true);
        anchorPane.setVisible(false);
    }
    public void stopSelectionMC(){
        anchorPane.setDisable(false);
        anchorPane.setVisible(true);
        aggiornaCosa();
    }
    public void aggiornaCosa() {
        mettiVita();
        mettiCarte(true);
    }
}