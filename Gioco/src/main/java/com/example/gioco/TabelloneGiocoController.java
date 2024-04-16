package com.example.gioco;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TabelloneGiocoController {
    private double centroX;
    private double centroY;
    private double xOffset;
    private double yOffset;
    private ArrayList<Integer> numNodiMano;
    private boolean checkInit;
    private boolean checkFattaPI;
    private GameData gameData;
    public static Giocatore vincitore;
    @FXML
    public OvalPaneController ovalPaneController;
    @FXML
    private Button turnButton;
    @FXML
    public StackPane stackPane;
    @FXML
    private VBox mazzoEScarti;
    @FXML
    private ImageView scarti;
    @FXML
    private ProgressBar barraVita;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label pesca;

    @FXML
    public void initialize() throws IOException {
        ovalPaneController.getMainController(this);
        gameData = GameData.getInstance();
        checkInit = false;
        checkFattaPI = false;
        impostaCose();
        ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setOnMouseEntered(event -> {
            ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setScaleX(1.1);
            ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setScaleY(1.1);
        });
        ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setOnMouseExited(event -> {
            ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setScaleX(1.0);
            ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setScaleY(1.0);
        });
        turnButton.setDisable(true);
        for (Giocatore giocatore : gameData.getGiocatoriPartita()) {
            if (giocatore.getHpRimanente() == 0) {
                setMortiEVincitore(giocatore);
            }
        }
        if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getHpRimanente() == 0) {
            handleTurnButton();
        }
        if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatoreRobot) {
            ((GiocatoreRobot) gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente())).giocaTurno(this, ovalPaneController);
            handleTurnButton();
        }
    }

    //*******************************INIZIO GESTIONE GRAFICA************************************************************

    //Imposta il layout del tabellone di gioco con tutte le carte, la vita e il mazzo
    private void impostaCose(){
        anchorPane.prefWidthProperty().bind(stackPane.widthProperty());
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
            mazzoEScarti.setLayoutY(centroY/2 - (checkInit ? mazzoEScarti.getHeight() + mazzoEScarti.getHeight()*getScala()/2 : 171) / 2);
            mettiCarte(false);
            if (mazzoEScarti.getHeight() != 0)
                checkInit=true;
        });
        mettiCarte(true);
        mettiVita();
    }

    //Impone una valore di scala per il mazzo valido ogni qualvolta si ridimensiona la scena
    private void scalaMazzo(){
        double scala = getScala();
        mazzoEScarti.setScaleX(scala);
        mazzoEScarti.setScaleY(scala);
    }
    //Da il fattore di scala
    private double getScala(){
        return 0.01 * (10 * Math.min(centroX, centroY)) / 50;
    }

    //Aggiorna la disposizione delle carte del giocatore corrente levandole prima tutte e rimettendo quelle esatte
    public void mettiCarte(boolean var) {
        numNodiMano = new ArrayList<>();
        for(int i = 0; i < anchorPane.getChildren().size(); i++) {
            if (anchorPane.getChildren().get(i) instanceof ImageView) {
                if (var) {
                    anchorPane.getChildren().remove(i);
                    i--;
                } else {
                    numNodiMano.add(i);
                }
            }
        }
        if (var) {
                ImageView imageView;
                ArrayList<Carta> manoCorrente = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano();
                for (int i = 0; i< manoCorrente.size(); i++) {
                    imageView = manoCorrente.get(i).getImage();
                    anchorPane.getChildren().add(imageView);
                    scalaImageView(imageView);
                    impostaListenerCarta(imageView, i);
                    numNodiMano.add(anchorPane.getChildren().indexOf(imageView));
                }
                spostaCarta();
        } else {
            for (int i : numNodiMano) {
                scalaImageView((ImageView) anchorPane.getChildren().get(i));
            }
            spostaCarta();
        }
    }

    //Imposta il fattore di scala per le ImageView
    public void scalaImageView(ImageView c){
        double dim = (5 * Math.min(centroX, centroY)) / 37;
        c.setFitWidth(dim);
    }

    //Setta tutti i listener necessari alle carte per garantirne l'animazione
    private void impostaListenerCarta(ImageView imageView, int i){
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

    //posiziona le carte nel modo corretto all'interno dell'anello azzurro
    public void spostaCarta(){
        double cX = centroX/2;
        double cY = centroY-centroY/11;
        double semiLarghezza = centroX/5;
        double altezza = centroY/6.8;
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

    //Gestisce il ridimensionamento dell'arco blu
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

    //Aggiorna la vita del giocatore corrente
    public void mettiVita(){
        int vita = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getHpRimanente();
        barraVita.setProgress((double) vita/5);
    }

    //*******************************FINE GESTIONE GRAFICA**************************************************************

    //Imposta i giocatori morti e nel caso decreta il vincitore
    public void setMortiEVincitore(Giocatore giocatoreMorto) {
        OvalPaneController.setMortoOP(giocatoreMorto);
        int check = 0;
        for (Giocatore giocatore : gameData.getGiocatoriPartita()){
            if (giocatore.getHpRimanente() > 0) {
                check++;
                vincitore = giocatore;
            }
        }
        if (check==1) {
            System.out.println("VINCE LA PARTITA: " + vincitore.getNome());
        } else if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) == (giocatoreMorto)) {
            handleTurnButton();
        }
    }

    //Gestisce tutte le azioni da compiere dopo il click del cambia turno
    public void handleTurnButton() {
        checkFattaPI = false;
        turnButton.setDisable(true);
        verificaMano();
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        ovalPaneController.cambiaTurno();
        for (int j : numNodiMano) {
            anchorPane.getChildren().get(j).setMouseTransparent(true);
        }
        pause.play();
        pause.setOnFinished(event -> {
            if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getHpRimanente() == 0) {
                handleTurnButton();
            } else  if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatoreRobot){
                ((GiocatoreRobot) gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente())).giocaTurno(this, ovalPaneController);
                handleTurnButton();
            } else {
                for (int j : numNodiMano) {
                    anchorPane.getChildren().get(j).setMouseTransparent(false);
                }
                aggiornaCosa();
                checkPI();
            }
        });
    }

    //In base al numero di carte del giocatore verifica se levare o mettere carte per arrivare a 5
    private void verificaMano() {
        Carta carta;
        while(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().size() > 5) {
            carta = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().get((int) (Math.random() * (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().size())));
            scartaCarte(carta, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
        }
        while(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().size() < 5) {
            gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).setMano(gameData.getMazzo().pesca());
        }
    }

    //Scarta la carta dalla mano del giocatore e aggiorna la pila degli scarti
    public void scartaCarte(Carta c, Giocatore g){
        g.scarta(c);
        gameData.getMazzo().setScarti(c);
        scarti.setImage(gameData.getMazzo().ultimoScarto().getImage().getImage());
    }

    //Pesca le prime due carte dal mazzo
    public void pescataInizialeGiocatore() {
        pesca.setVisible(false);
        turnButton.setDisable(false);
        Giocatore player = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente());
        for(int i = 1; i <= 2; i++) {
            player.setMano(gameData.getMazzo().pesca());
            System.out.println("Ho pescato");
        }
        checkFattaPI = true;
        checkPI();
        if (!(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatoreRobot))
            mettiCarte(true);
    }

    //Verifica se la pescata iniziale è stata fatta e nel caso attiva e disattiva il necessario
    private void checkPI(){
        if (!checkFattaPI) {
            turnButton.setDisable(true);
            pesca.setVisible(true);
            ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setMouseTransparent(false);
        } else {
            turnButton.setDisable(false);
            pesca.setVisible(false);
            ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setMouseTransparent(true);
        }
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

    //SAVE
    public void salvaPartita() {
        gameData.aggiornaFile();
        System.out.println(gameData.getGiocatoriPartita());
        System.out.println(gameData.getMazzo().toStringScarti());
    }

    //BACK
    public void switchToAdminPlayerPage(ActionEvent event) throws IOException {
        salvaPartita();
        GameData.resetInstance();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLeaderBoard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LeaderBoard.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}