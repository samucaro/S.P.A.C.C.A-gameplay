package com.example.gioco;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class TabelloneGiocoController {
    private static int numBang;
    private static Giocatore vincitore;
    private static Giocatore vincitoreTorneo;
    private double centroX;
    private double centroY;
    private double xOffset;
    private double yOffset;
    public boolean checkRidimensionato;
    private boolean checkInit;
    private boolean checkBack;
    private GameData gameData;
    private final DataSet dataSet = new DataSet();
    private ArrayList<Integer> numNodiMano;
    @FXML
    public OvalPaneController ovalPaneController;
    @FXML
    private Button turnButton;
    @FXML
    private Button backButton;
    @FXML
    private Button leaderBoardButton;
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
    private Text messaggioSalvataggio;
    private final Timeline turnoBot = new Timeline(
            new KeyFrame(Duration.ZERO, inizioTurno -> aggiornaCosa()),
            new KeyFrame(Duration.seconds(0.5), eventoPescata -> {
                pescataInizialeGiocatore();
                mettiCarte(true);
            }),
            new KeyFrame(Duration.seconds(1.5), eventoTurno -> {
                ((GiocatoreRobot) gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente())).giocaTurno(this, ovalPaneController);
                mettiVita();
                mettiCarte(true);
            }),
            new KeyFrame(Duration.seconds(2.3), eventoFineTurno -> handleTurnButton())
    );

    @FXML
    public void initialize() {
        gameData = GameData.getInstance();
        vincitore = null;
        ovalPaneController.getMainController(this);
        checkInit = false;
        checkBack = true;
        impostaCose();
        for(Giocatore giocatore : gameData.getGiocatoriPartita()) {
            if (giocatore.getHpRimanente() == 0) {
                setMortiEVincitore(giocatore);
            }
        }
        PauseTransition pause = getPauseTransition();
        pause.playFromStart();
    }

    private PauseTransition getPauseTransition() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(event -> {
            if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getHpRimanente() == 0) {
                handleTurnButton();
            }
            else if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatoreRobot) {
                turnoBot.playFromStart();
            } else {
                pesca.setVisible(true);
                aggiornaCosa();
            }
        });
        return pause;
    }

    //Imposta il layout del tabellone di gioco con tutte le carte, la vita e il mazzo
    private void impostaCose(){
        anchorPane.prefWidthProperty().bind(stackPane.widthProperty());
        anchorPane.prefHeightProperty().bind(stackPane.heightProperty());
        stackPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            checkRidimensionato = true;
            centroX = (double) newValue;
            scalaMazzo();
            mazzoEScarti.setLayoutX(centroX/2 - (checkInit ? mazzoEScarti.getWidth() : 140)/2);
            mettiCarte(false);
            if(mazzoEScarti.getHeight() != 0) {
                checkInit = true;
            }
        });
        stackPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            checkRidimensionato = true;
            centroY = (double) newValue;
            scalaMazzo();
            mazzoEScarti.setLayoutY(centroY/2 - (checkInit ? mazzoEScarti.getHeight() + mazzoEScarti.getHeight()*(ovalPaneController.getScala()/3)/2 : 171) / 2);
            mettiCarte(false);
            if(mazzoEScarti.getHeight() != 0) {
                checkInit = true;
            }
        });
        mettiVita();
        ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setOnMouseEntered(event -> {
            ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setScaleX(1.1);
            ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setScaleY(1.1);
        });
        ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setOnMouseExited(event -> {
            ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setScaleX(1.0);
            ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setScaleY(1.0);
        });
        turnButton.setDisable(true);
    }

    //Impone una valore di scala per il mazzo valido ogni qualvolta si ridimensiona la scena
    private void scalaMazzo(){
        mazzoEScarti.setScaleX(ovalPaneController.getScala()/3);
        mazzoEScarti.setScaleY(ovalPaneController.getScala()/3);
    }

    //Aggiorna la disposizione delle carte del giocatore corrente levandole prima tutte e rimettendo quelle esatte
    private void mettiCarte(boolean var) {
        if(vincitore == null) {
            numNodiMano = new ArrayList<>();
            for(int i = 0; i < anchorPane.getChildren().size(); i++) {
                if(anchorPane.getChildren().get(i) instanceof ImageView) {
                    if(var) {
                        anchorPane.getChildren().remove(i);
                        i--;
                    }
                    else {
                        numNodiMano.add(i);
                    }
                }
            }
            if(var) {
                ImageView imageView;
                ArrayList<Carta> manoCorrente = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano();
                for(int i = 0; i < manoCorrente.size(); i++) {
                    imageView = manoCorrente.get(i).getImage();
                    anchorPane.getChildren().add(imageView);
                    scalaImageView(imageView);
                    if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatorePersona)
                        impostaListenerCarta(imageView, i);
                    numNodiMano.add(anchorPane.getChildren().indexOf(imageView));
                }
                spostaCarta();
            }
            else {
                for(int i : numNodiMano) {
                    scalaImageView((ImageView) anchorPane.getChildren().get(i));
                }
                spostaCarta();
            }
        }
    }

    //Imposta il fattore di scala per le ImageView
    private void scalaImageView(ImageView c){
        double dim = (5 * Math.min(centroX, centroY)) / 37;
        c.setFitWidth(dim);
    }

    //Setta tutti i listener necessari alle carte per garantirne l'animazione
    private void impostaListenerCarta(ImageView imageView, int i) {
        Carta cartaAttuale = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().get(i);
        imageView.setOnMouseEntered(event -> {
            imageView.setScaleX(1.1);
            imageView.setScaleY(1.1);
            imageView.setOnMouseExited(event1 -> {
                imageView.setScaleX(1.0);
                imageView.setScaleY(1.0);
            });
        });
        imageView.setOnMousePressed(event -> {
            xOffset = event.getSceneX() - imageView.getTranslateX();
            yOffset = event.getSceneY() - imageView.getTranslateY();
        });
        imageView.setOnMouseDragged(event -> {
            imageView.setTranslateX(event.getSceneX() - xOffset);
            imageView.setTranslateY(event.getSceneY() - yOffset);
        });
        imageView.setOnMouseReleased(event -> {
            double finalX = event.getSceneX();
            double finalY = event.getSceneY();
            imageView.setTranslateX(0);
            imageView.setTranslateY(0);
            if(finalX<centroX/2+centroX/8 && finalX>centroX/2-centroX/8 && finalY<centroY/2+centroY/8 && finalY>centroY/2-centroY/8) {
                if(cartaAttuale instanceof  CartaBang) {
                    numBang++;
                    if(numBang > 2) {
                        erroreBang();
                    }
                    else {
                        cartaAttuale.usaAbilita(ovalPaneController, this);
                    }
                }
                else {
                    cartaAttuale.usaAbilita(ovalPaneController, this);
                }
            }
        });
    }

    private void erroreBang(){
        pesca.setText("MASSIMO 2 BANG");
        pesca.setTextFill(Color.RED);
        pesca.setVisible(true);
        PauseTransition p = new PauseTransition(Duration.seconds(1.7));
        p.setOnFinished(event -> {
            pesca.setText("Pesca per iniziare");
            pesca.setTextFill(Color.WHITE);
            pesca.setVisible(turnButton.isDisable());
        });
        p.playFromStart();
    }

    //posiziona le carte nel modo corretto all'interno dell'anello azzurro
    private void spostaCarta(){
        double cX = centroX/2;
        double cY = centroY-centroY/11;
        double semiLarghezza = centroX/5;
        double altezza = centroY/6.8;
        int numOggetti = numNodiMano.size();
        double[][] coordinate = calcolaCoordinateArco(cX, cY, semiLarghezza, altezza, numOggetti);
        double[] angoli = new double[numOggetti];
        for(int i = 0; i < numNodiMano.size(); i++) {
            angoli[i] = numOggetti <= 1 ? 0 : -(-30 + i * ((double) 60/(numOggetti-1)));
            ImageView imageView = (ImageView) anchorPane.getChildren().get(numNodiMano.get(i));
            imageView.setLayoutX(coordinate[i][0] - imageView.getFitWidth()/2);
            imageView.setLayoutY(coordinate[i][1] - imageView.getFitWidth()*1.29);
            imageView.setRotate(angoli[i]);
        }
    }

    //Gestisce il ridimensionamento dell'arco blu
    private double[][] calcolaCoordinateArco(double cX, double cY, double semiLarghezza, double altezza, int numOggetti) {
        double[][] coordinate = new double[numOggetti][2];
        double angoloStep = Math.PI / (numOggetti - 1);
        double angolo;
        double x;
        double y;
        for(int i = 0; i < numOggetti; i++) {
            angolo = i * angoloStep;
            x = cX + semiLarghezza * Math.cos(angolo);
            y = cY - altezza * Math.sin(angolo);
            coordinate[i][0] = x;
            coordinate[i][1] = y;
        }
        if(numOggetti == 1) {
            coordinate[0][0] = cX;
            coordinate[0][1] = cY - altezza;
        }
        return coordinate;
    }

    //Aggiorna la vita del giocatore corrente
    private void mettiVita() {
        int vita = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getHpRimanente();
        barraVita.setProgress((double) vita/5);
    }

    //Imposta i giocatori morti e nel caso decreta il vincitore
    public void setMortiEVincitore(Giocatore giocatoreMorto) {
        OvalPaneController.setMortoOP(giocatoreMorto);
        while(!(giocatoreMorto.getMano().isEmpty())) {
            scartaCarte(giocatoreMorto.getMano().getFirst(), giocatoreMorto);
        }
        giocatoreMorto.clearMano();
        int check = 0;
        Giocatore ggVivo = null;
        for(Giocatore giocatore : gameData.getGiocatoriPartita()) {
            if(giocatore.getHpRimanente() > 0) {
                check++;
                ggVivo = giocatore;
            }
        }
        if(check==1) {
            vincitore = ggVivo;
            handleFineGioco();
        }
        else if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) == (giocatoreMorto) && gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatorePersona) {
            handleTurnButton();
        }
    }

    //Gestisce tutte le azioni da compiere dopo il click del cambia turno
    public void handleTurnButton() {
        if(checkBack) {
            numBang = 0;
            checkRidimensionato = false;
            ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setMouseTransparent(true);
            turnButton.setDisable(true);
            pesca.setVisible(false);
            verificaMano();
            Timeline tm = ovalPaneController.cambiaTurno();
            for(int j : numNodiMano) {
                anchorPane.getChildren().get(j).setMouseTransparent(true);
            }
            tm.playFromStart();
            tm.setOnFinished(event -> {
                if(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getHpRimanente() == 0) {
                    if(checkRidimensionato) {
                        OvalPaneController.posizionaPianeti();
                    }
                    handleTurnButton();
                }
                else if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatoreRobot) {
                    turnoBot.playFromStart();
                }
                else {
                    ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setMouseTransparent(false);
                    aggiornaCosa();
                    pesca.setVisible(true);
                }
            });
        }
        messaggioSalvataggio.setVisible(false);
    }

    //In base al numero di carte del giocatore verifica se levare o mettere carte per arrivare a 5
    private void verificaMano() {
        Carta carta;
        if(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getHpRimanente() != 0) {
            while(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().size() > 5) {
                carta = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().get((int) (Math.random() * (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().size())));
                scartaCarte(carta, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
            }
            while(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().size() < 5) {
                gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).setMano(gameData.getMazzo().pesca());
            }
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
        Giocatore player = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente());
        for(int i = 1; i <= 2; i++) {
            player.setMano(gameData.getMazzo().pesca());
        }
        ((HBox) mazzoEScarti.getChildren().get(1)).getChildren().getFirst().setMouseTransparent(true);
        if(!(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatoreRobot)) {
            mettiCarte(true);
            turnButton.setDisable(false);
            pesca.setVisible(false);
        }
    }

    //Disabilita e abilita anchorPane ogni task di selezione e aggiorna il necessario
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
        OvalPaneController.posizionaPianeti();
        mettiVita();
        mettiCarte(true);
    }

    //Attiva la grafica per mostrare il vincitore della partita
    private void handleFineGioco() {
        numBang = 0;
        if(gameData.getNumPartitaCorrente() == 14) {
            vincitoreTorneo = vincitore;
        }
        checkBack = false;
        ovalPaneController.fineGiocoGrafica();
        boolean bol = gameData.getCode() > 999;
        Node n;
        for(int i = 0; i < anchorPane.getChildren().size(); i++) {
            n = anchorPane.getChildren().get(i);
            if((n != backButton) && (n != leaderBoardButton) && (bol || n != turnButton)) {
                anchorPane.getChildren().remove(n);
                i--;
            }
        }
        if(!bol) {
            turnButton.setDisable(false);
            turnButton.setText("CONTINUA TORNEO");
            turnButton.setFont(new Font("Game of Thrones", 12));
            turnButton.setOnAction(event -> {
                try {
                    salvaPartita();
                    gameData.aggiornaPartitaCorrente(vincitore.getNome());
                    GameData.resetInstance();
                    switchToTournamentPage(event);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        messaggioSalvataggio.setVisible(false);
        aggiornaFileLeaderBoard();
    }

    //Aggiorna il file delle LeaderBoard ogni volta che vi è un vincitore
    private void aggiornaFileLeaderBoard() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataSet.getProjectFolderPath() + File.separator + "/" + "LeaderBoard.txt"));
            StringBuilder contenuto = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.split(" ")[0];
                if(word.equals(getNomeVincitore())) {
                    int nuovoPunteggio;
                    if(word.equals(getNomeVincitoreTorneo())) {
                        nuovoPunteggio = Integer.parseInt(line.split(" ")[1]) + 5;
                    }
                    else {
                        nuovoPunteggio = Integer.parseInt(line.split(" ")[1]) + 3;
                    }
                    contenuto.append(getNomeVincitore()).append(" ").append(nuovoPunteggio).append("\n");
                }
                else {
                    contenuto.append(line).append("\n");
                }
            }
            reader.close();
            FileWriter file = new FileWriter((dataSet.getProjectFolderPath() + File.separator + "/" + "LeaderBoard.txt"), false);
            PrintWriter writer = new PrintWriter(file);
            writer.write(contenuto.toString());
            writer.close();
        } catch (IOException e) {
            System.err.println("Errore durante la creazione del file: " + e.getMessage());
        }
    }

    public static String getNomeVincitore() {
        return vincitore == null ? "" : vincitore.getNome();
    }
    public static String getNomeVincitoreTorneo() {
        return vincitoreTorneo == null ? "" : vincitoreTorneo.getNome();
    }

    //SAVE
    public void salvaPartita() throws IOException {
        gameData.aggiornaFile();
        messaggioSalvataggio.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), messaggioSalvataggio);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> messaggioSalvataggio.setVisible(false));
        fadeTransition.play();
    }

    //BACK
    public void switchToAdminPlayerPage(ActionEvent event) throws IOException {
        checkBack = false;
        salvaPartita();
        GameData.resetInstance();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //LEADERBOARD
    public void switchToLeaderBoard() {
        try {
            Stage stage = new Stage();
            stage.setTitle("LeaderBoard");
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LeaderBoard.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException e) {
            leaderBoardButton.setDisable(true);
            mostraErrore();
            System.err.println(e.getMessage());
        }
    }

    //CONTINUA TORNEO
    public void switchToTournamentPage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("TournamentPage.fxml")));
            StartTournamentController st = new StartTournamentController();
            fxmlLoader.setController(st);
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            st.leggiFile(gameData.getCode());
        }
        catch (IOException | NullPointerException e) {
            mostraErrore();
            System.err.println(e.getMessage());
        }
    }

    private void mostraErrore() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRORE");
        alert.setHeaderText("Impossibile caricare il contenuto");
        alert.setContentText("Si è verificato un errore durante il caricamento del contenuto. Contatta l'assistenza" +
                "tecnica al seguente numero verde: +393209786308");
        alert.showAndWait();
    }
}