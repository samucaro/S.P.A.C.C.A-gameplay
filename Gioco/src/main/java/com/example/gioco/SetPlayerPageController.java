package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.PrintWriter;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Vector;


public class SetPlayerPageController {
    private Scene scene;
    private Parent root;
    private DataSet DataS;
    private int code;
    private String[] mani;
    private String[] tipoGiocatore;
    private int numPersone;
    private Mazzo m;
    private int numGiocatori;
    private String[] nomi;
    @FXML
    private VBox nomiGiocatori;
    @FXML
    private Button saveLogout;
    @FXML
    private TextField codice;
    @FXML
    private ChoiceBox<String> numGiocatoriItem;
    @FXML
    private ChoiceBox<String> numRobotItem;
    @FXML
    public void initialize() {
        DataS = new DataSet();
        numPersone = 0;
        numGiocatori = 0;
        numGiocatoriItem.getItems().addAll("3 giocatori", "4 giocatori", "5 giocatori", "6 giocatori", "7 giocatori","8 giocatori");
        impostaGiocatori();
    }

    //Metodo che permette di gestire le opzioni dei choiceBox in base alle scelte dell'utente
    public void impostaGiocatori() {
        gestisciGiocatori();
        gestisciRobot();
    }

    //metodo che gestisce le possibili scelte sui giocatori
    private void gestisciGiocatori() {
        numGiocatoriItem.getSelectionModel().selectedItemProperty().addListener( //ogni volta che modifico il numero di giocatori resetta tutte le opzioni successive
                (observable, oldValue, newValue) -> {
                    for (int i = 0; i < 8; i++ )
                        nomiGiocatori.getChildren().get(i).setVisible(false);
                    saveLogout.setDisable(true);
                    numGiocatori = Integer.parseInt(newValue.split(" ")[0]);
                    numPersone = numGiocatori;
                    codice.setText(generaCodice());
                    numRobotItem.getItems().clear();
                    numRobotItem.getItems().add("Nessuno");
                    for(int i = 0; i < numGiocatori; i++) {
                        numRobotItem.getItems().add((i+1) + " bot");
                    }
                    nomi = new String[numGiocatori];
                }
        );
    }

    //metodo che gestisce le possibili scelte sui robot
    public void gestisciRobot() {
        numRobotItem.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    saveLogout.setDisable(true);
                    if(newV != null) {
                        if (newV.equals("Nessuno")) {
                            numPersone = numGiocatori;
                        } else {
                            numPersone = numGiocatori - Integer.parseInt(newV.split(" ")[0]);
                            if(numPersone == 0)
                                saveLogout.setDisable(false);
                        }
                        selezioneNomi();
                    }
                }
        );
    }

    //metodo che abilita il salvataggio solo sotto determinate condizioni necessarie
    public void verificaNomi() {
        boolean check = true;
        for(int i = 0; i < numGiocatori; i++) {
            check = check && !((TextField) nomiGiocatori.getChildren().get(i)).getText().isEmpty();
        }
        if (check) {
            saveLogout.setDisable(false);
            for(int i = 0; i < numGiocatori; i++)
                nomi[i] = ((TextField) nomiGiocatori.getChildren().get(i)).getText();
        }
        else {
            saveLogout.setDisable(true);
        }
    }

    //Metodo che gestisce l'inserimento del nome se e solo se il giocatore è una persona
    private void selezioneNomi(){
        tipoGiocatore = new String[numGiocatori];
        for (int i = 0; i < numGiocatori; i++){
            nomiGiocatori.getChildren().get(i).setVisible(true);
            ((TextField) nomiGiocatori.getChildren().get(i)).setPromptText("Giocatore " + (i+1));
            ((TextField) nomiGiocatori.getChildren().get(i)).setText("");
            nomiGiocatori.getChildren().get(i).setDisable(false);
            tipoGiocatore[i] = "Persona";
        }
        for (int i = numGiocatori; i < 8; i++ ){
            nomiGiocatori.getChildren().get(i).setVisible(false);
        }
        for (int i = numPersone; i < numGiocatori; i++) {
            nomiGiocatori.getChildren().get(i).setVisible(true);
            ((TextField) nomiGiocatori.getChildren().get(i)).setText("Bot " + (i-numPersone+1));
            nomiGiocatori.getChildren().get(i).setDisable(true);
            tipoGiocatore[i] = "Bot";
        }
    }

    //metodo che genera un codice casuale per il file di salvataggio e la partita
    private String generaCodice() {
        do {
            code = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;
        } while (DataS.checkCode(code));
        return "" + code;
    }

    //mazzo che assegna la mano a ogni giocatore e salva tutto su file
    private void assegnaMano() {
        mani = new String[numGiocatori];
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < numGiocatori; i++) {
            for (int j = 1; j <= 5; j++) {
                str.append(m.pesca().toStringNome()).append(" ");
            }
            mani[i] = str.toString();
            str = new StringBuilder();
        }
    }

    //metodo che genera il file secondo una logica ben precisa
    private void nuovoFile() {
        Vector<Integer> vector = new Vector<>();
        for (int i = 0; i < numGiocatori; i++)
            vector.add(i);
        Collections.shuffle(vector);
        try {
            FileWriter file = new FileWriter((DataS.getProjectFolderPath() + File.separator + "/" + code + ".txt"), true);
            PrintWriter writer = new PrintWriter(file);
            writer.println("Dati Generali Partita:");
            writer.println("NumGiocatori: " + numGiocatori);
            writer.println("Turno: 0");
            writer.println("Mazzo: " + m.toString());
            writer.println("Scarti: ");
            writer.println("******************************");
            int j = 1;
            for (int i : vector) {
                writer.println("Giocatore: " + j);
                writer.println("Tipo: " + tipoGiocatore[i]);
                writer.println("Nome: " + nomi[i]);
                writer.println("Mano: " + mani[i]);
                writer.println("HpRimanente: " + 5);
                writer.println("******************************");
                j++;
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Errore durante la creazione del file: " + e.getMessage());
        }
    }

    public void switchToTypeGamePage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TypeGamePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToAdminPlayerPage(ActionEvent event) throws IOException {
        DataS.creaFile(code);
        m = new Mazzo();
        m.componiMazzo();
        assegnaMano();
        nuovoFile();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}