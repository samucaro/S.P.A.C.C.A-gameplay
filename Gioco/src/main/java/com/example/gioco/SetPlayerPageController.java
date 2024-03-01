package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;


public class SetPlayerPageController {
    private GameData gameData = GameData.getInstance();
    private Scene scene;
    private DataSet DataS = new DataSet();
    private int code;
    private Parent root;
    private int numPersone;
    private int numGiocatori;
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
        numGiocatoriItem.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    reset();
                    numGiocatori = Integer.parseInt(newValue.split(" ")[0]);
                    numPersone = numGiocatori;
                    codice.setText(generaCodice());
                    outputText.setText("Hai selezionato " + numGiocatori + " giocatori");
                    outputText.setVisible(true);
                    numRobotItem.getItems().clear();
                    numRobotItem.getItems().add("Nessuno");
                    for(int i = 0; i < numGiocatori; i++) {
                        numRobotItem.getItems().add((i+1) + " bot");
                    }
                    numRobotItem.getSelectionModel().selectedItemProperty().addListener(
                            (obs, oldV, newV) -> {
                                int verify = 0;
                                selezioneNomi();
                                if(newV != null) {
                                    if (newV.equals("Nessuno")) {
                                        numPersone = numGiocatori;
                                        outputText.setText("Giochi senza robot");
                                    } else {
                                        numPersone = numGiocatori - Integer.parseInt(newV.split(" ")[0]);
                                        outputText.setText("Hai selezionato " + (numGiocatori - numPersone) + " robot, numero persone: " + numPersone);
                                    }
                                    selezioneNomi();
                                    for(int i = 0; i < numGiocatori; i++) {
                                        if(numRobotItem.getItems().get(i) != null) {
                                            verify++;
                                        }
                                        if(verify == numGiocatori) {
                                            saveLogout.setDisable(false);
                                        }
                                    }
                                }
                            }
                    );
                }
        );
    }

    public void reset() {
        outputText.setText("");
        saveLogout.setDisable(true);
    }
    private void selezioneNomi(){
        for (int i = 0; i < numGiocatori; i++){
            nomiGiocatori.getChildren().get(i).setVisible(true);
            ((TextField) nomiGiocatori.getChildren().get(i)).setText("Giocatore " + (i+1));
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

    public void switchToTypeGamePage(ActionEvent event) throws IOException {
        gameData.setNumero(numGiocatori);
        gameData.setPersone(numPersone);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TypeGamePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAdminPlayerPage(ActionEvent event) throws IOException {
        gameData.setNumero(numGiocatori); //cambiare
        gameData.setPersone(numPersone);
        DataS.creaFile(code);
        Mazzo m = new Mazzo();
        m.componiMazzo();
        gameData.scrivi();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
