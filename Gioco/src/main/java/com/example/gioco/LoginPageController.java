package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;


public class LoginPageController {
    private GameData gameData = GameData.getInstance();
    private Scene scene;
    private Parent root;
    private int numPersone;
    private int numGiocatori;
    @FXML
    private CheckBox rispostaSi;
    @FXML
    private CheckBox rispostaNo;
    @FXML
    private MenuButton numRobot;
    @FXML
    private Button startGame;
    @FXML
    private Button fastGame;
    @FXML
    private Text outputText;

    @FXML
    public void initialize() {
        numPersone = 0;
        numGiocatori = 0;
    }

    public void reset() {
        outputText.setText("");
        startGame.setDisable(true);
        fastGame.setDisable(true);
        rispostaNo.setVisible(false);
        rispostaNo.setSelected(false);
        rispostaSi.setVisible(false);
        rispostaSi.setSelected(false);
        numRobot.setVisible(false);
    }

    public void opzione1() {
        numGiocatori = 3;
        outputText.setText("Hai selezionato 3 giocatori");
        outputText.setVisible(true);
    }
    public void opzione2() {
        numGiocatori = 4;
        outputText.setText("Hai selezionato 4 giocatori");
        outputText.setVisible(true);
    }
    public void opzione3() {
        numGiocatori = 5;
        outputText.setText("Hai selezionato 5 giocatori");
        outputText.setVisible(true);
    }
    public void opzione4() {
        numGiocatori = 6;
        outputText.setText("Hai selezionato 6 giocatori");
        outputText.setVisible(true);
    }
    public void opzione5() {
        numGiocatori = 7;
        outputText.setText("Hai selezionato 7 giocatori");
        outputText.setVisible(true);
    }
    public void opzione6() {
        numGiocatori = 8;
        outputText.setText("Hai selezionato 8 giocatori");
        outputText.setVisible(true);
    }
    public void giocoConRobot() {
        startGame.setDisable(true);
        fastGame.setDisable(true);
        if(rispostaSi.isSelected()) {
            outputText.setText("Giochi contro almeno 1 robot");
            rispostaNo.setSelected(false);
        }
        numRobot.setVisible(true);
    }
    public void activeChois() {
        rispostaSi.setVisible(true);
        rispostaNo.setVisible(true);
    }
    public void setNumRobot() {
        for(int i=0; i<numGiocatori; i++) {
            numRobot.getItems().get(i).setVisible(true);
        }
        for(int i=7; i>numGiocatori-1; i--) {
            numRobot.getItems().get(i).setVisible(false);
        }
        numRobot.show();
    }

    public void giocoSenzaRobot() {
        if(rispostaNo.isSelected()) {
            rispostaSi.setSelected(false);
            numRobot.setVisible(false);
            outputText.setText("Giochi senza robot");
            numPersone = numGiocatori;
        }
        gameData.setRobot(0);
        startGame.setDisable(false);
        fastGame.setDisable(false);
    }

    public void opzione1R() {
        numPersone = numGiocatori-1;
        System.out.println();
        outputText.setText("Hai selezionato 1 robot, numero persone: " + numPersone);
        gameData.setRobot(1);
        startGame.setDisable(false);
        fastGame.setDisable(false);
    }
    public void opzione2R() {
        numPersone = numGiocatori-2;
        outputText.setText("Hai selezionato 2 robot, numero persone: " + numPersone);
        gameData.setRobot(2);
        startGame.setDisable(false);
        fastGame.setDisable(false);
    }
    public void opzione3R() {
        numPersone = numGiocatori-3;
        outputText.setText("Hai selezionato 3 robot, numero persone: " + numPersone);
        gameData.setRobot(3);
        startGame.setDisable(false);
        fastGame.setDisable(false);
    }
    public void opzione4R() {
        numPersone = numGiocatori-4;
        outputText.setText("Hai selezionato 4 robot, numero persone: " + numPersone);
        gameData.setRobot(4);
        startGame.setDisable(false);
        fastGame.setDisable(false);
    }
    public void opzione5R() {
        numPersone = numGiocatori-5;
        outputText.setText("Hai selezionato 5 robot, numero persone: " + numPersone);
        gameData.setRobot(5);
        startGame.setDisable(false);
        fastGame.setDisable(false);
    }
    public void opzione6R() {
        numPersone = numGiocatori-6;
        outputText.setText("Hai selezionato 6 robot, numero persone: " + numPersone);
        gameData.setRobot(6);
        startGame.setDisable(false);
        fastGame.setDisable(false);
    }
    public void opzione7R() {
        numPersone = numGiocatori-7;
        outputText.setText("Hai selezionato 7 robot, numero persone: " + numPersone);
        gameData.setRobot(7);
        startGame.setDisable(false);
        fastGame.setDisable(false);
    }
    public void opzione8R() {
        numPersone = numGiocatori-8;
        outputText.setText("Hai selezionato 8 robot, numero persone: " + numPersone);
        gameData.setRobot(8);
        startGame.setDisable(false);
        fastGame.setDisable(false);
    }

    /*
    public void regole(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileTesto = new File();
        }
        catch(FileNotFoundException e) {
            System.out.println("ERRORE! " + e.getMessage());
        }
    }
     */

    public void switchToAccessPlayer(ActionEvent event) throws IOException {
        gameData.setNumero(numGiocatori);
        gameData.setPersone(numPersone);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AccessPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Da cambiare, deve switchare sulla partita direttamente
    public void switchToFastGamePage(ActionEvent event) throws IOException {
        gameData.setNumero(numGiocatori);
        gameData.setPersone(numPersone);
        gameData.getGGRandom();
        root = FXMLLoader.load(getClass().getResource("Partitonza.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        scene = new Scene(root);
        impostaListener(scene);
        ParallelCamera cam = new ParallelCamera();
        cam.setFarClip(2000);
        cam.setNearClip(0.5);
        scene.setCamera(cam);
        stage.setScene(scene);
        stage.show();
    }
    public void impostaListener(Scene scene){
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            OvalPaneController.setScenaX((Double) newVal);
            MainController.setScenaX((Double) newVal);
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            OvalPaneController.setScenaY((Double) newVal);
            MainController.setScenaY((Double) newVal);
        });
    }

    /*@FXML
    public void assegnaRuoli(ActionEvent event) {
        //creaGiocatore();
        Ruoli[] array = new Ruoli[numGiocatori];
        if(numGiocatori==3) {
            array[0]=Ruoli.SCERIFFO;
            array[1]=Ruoli.RINNEGATO;
            array[2]=Ruoli.FUORILEGGE;
            for(int i=0; i<numGiocatori; i++) {
                int ref = (int) (Math.random()*numGiocatori)+1;
                switch(ref) {
                    case 1:
                        array[i]=Ruoli.SCERIFFO;
                        break;
                    case 2:
                        array[i]=Ruoli.RINNEGATO;
                }
            }

        }
        else if(numGiocatori==4) {

        }
        else if(numGiocatori==5) {

        }
        else if(numGiocatori==6) {

        }
        else if(numGiocatori==7) {

        }
        else {

        }
    }
    @FXML
    public void assegnaPersonaggi(ActionEvent event) {
        int val = (int) (Math.random()*(4-1) + 1);
        System.out.println(val);
    }*/
}
