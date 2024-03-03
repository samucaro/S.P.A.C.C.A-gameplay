package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.PrintWriter;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class SetPlayerPageController {
    private GameData gameData = GameData.getInstance();
    private Scene scene;
    private DataSet DataS = new DataSet();
    private int code;
    private String[] mani;
    private Parent root;
    private int numPersone;
    private Mazzo m;
    private int numGiocatori;
    private String[] nomi;
    @FXML
    private VBox nomiGiocatori;
    @FXML
    private CheckBox rispostaSi;
    @FXML
    private CheckBox rispostaNo;
    @FXML
    private MenuButton numRobot;
    @FXML
    private Button saveLogout;
    @FXML
    private Text outputText;
    @FXML
    private TextField codice;
    @FXML
    private ScrollPane setNomi;
    @FXML
    private ChoiceBox<String> numGiocatoriItem;
    @FXML
    private ChoiceBox<String> numRobotItem;
    public SetPlayerPageController() throws IOException {
    }
    @FXML
    public void initialize() {
        numPersone = 0;
        numGiocatori = 0;
        numGiocatoriItem.getItems().addAll("3 giocatori", "4 giocatori", "5 giocatori", "6 giocatori", "7 giocatori","8 giocatori");
        impostaGiocatori();
    }
    public void impostaGiocatori() {
        numGiocatoriItem.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
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
        numRobotItem.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    saveLogout.setDisable(true);
                    selezioneNomi();
                    if(newV != null) {
                        if (newV.equals("Nessuno")) {
                            numPersone = numGiocatori;
                        } else {
                            numPersone = numGiocatori - Integer.parseInt(newV.split(" ")[0]);
                        }
                        selezioneNomi();
                    }
                }
        );
    }
    public void verificaNomi(KeyEvent event) {
        boolean check = true;
        for(int i = 0; i < numGiocatori; i++) {
            check = check && !((TextField) nomiGiocatori.getChildren().get(i)).getText().isEmpty();
        }
        System.out.println(check);
        if (check) {
            saveLogout.setDisable(false);
            for(int i = 0; i < numGiocatori; i++)
                nomi[i] = ((TextField) nomiGiocatori.getChildren().get(i)).getText();
        } else
            saveLogout.setDisable(true);
    }
    private void selezioneNomi(){
        for (int i = 0; i < numGiocatori; i++){
            nomiGiocatori.getChildren().get(i).setVisible(true);
            ((TextField) nomiGiocatori.getChildren().get(i)).setPromptText("Giocatore " + (i+1));
            ((TextField) nomiGiocatori.getChildren().get(i)).setText("");
            nomiGiocatori.getChildren().get(i).setDisable(false);
        }
        for (int i = numGiocatori; i < 8; i++ )
            nomiGiocatori.getChildren().get(i).setVisible(false);
        for (int i = numPersone; i < numGiocatori; i++) {
            nomiGiocatori.getChildren().get(i).setVisible(true);
            ((TextField) nomiGiocatori.getChildren().get(i)).setText("Bot " + (i-numPersone+1));
            nomiGiocatori.getChildren().get(i).setDisable(true);
        }
    }
    private String generaCodice() {
        do {
            code = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;
        } while (DataS.checkCode(code));
        gameData.setCode(code);
        return "" + code;
    }
    private void assegnaMano() {
        mani = new String[numGiocatori];
        String str = "";
        for(int i = 0; i < numGiocatori; i++) {
            for (int j = 1; j <= 5; j++) {
                str += m.pesca().toStringNome() + " ";
            }
            mani[i] = str;
            str = "";
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

    private void nuovoFile() {
        try {
            FileWriter file = new FileWriter((DataS.getProjectFolderPath() + File.separator + "/" + code + ".txt"), true);
            PrintWriter writer = new PrintWriter(file);
            writer.println("Dati Generali Partita:");
            writer.println("NumGiocatori: " + numGiocatori);
            writer.println("Turno: 0");
            writer.println("Mazzo: " + m.toString());
            writer.println("Scarti: ");
            writer.println("******************************");

            // Informazioni dei giocatori
            for (int i = 0; i < numGiocatori; i++) {
                writer.println("Giocatore " + (i + 1) + ":");
                writer.println("Nome: " + nomi[i]);
                writer.println("Mano: " + mani[i]);
                if(numPersone >= 0) {
                    writer.println("Tipo: Persona");
                    numPersone--;
                }
                else {
                    writer.println("Tipo: Bot");
                }
                writer.println("HpRimanente: " + 5);
                writer.println("******************************");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Errore durante la creazione del file: " + e.getMessage());
        }
    }
}


