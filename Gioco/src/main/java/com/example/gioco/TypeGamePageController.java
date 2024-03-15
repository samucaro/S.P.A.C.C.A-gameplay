package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TypeGamePageController {
    private Scene scene;
    private Parent root;
    private GameData gameData = GameData.getInstance();
    private DataSet DataS = new DataSet();
    @FXML
    private ChoiceBox<String> partite;
    @FXML
    private Button elimina;
    @FXML
    private Text testo;
    private String newValue;
    private String percorsoCartellaProgetto;
    @FXML
    public void initialize() {
        percorsoCartellaProgetto = DataS.getProjectFolderPath();
        mostraPartite();
        partite.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    if(newV != null) {
                        elimina.setVisible(true);
                        newValue = newV;
                    }
                }
        );
    }

    public void mostraPartite() {
        partite.getItems().clear();
        String percorsoCartellaProgetto = DataS.getProjectFolderPath();
        File directory = new File(percorsoCartellaProgetto);
        if (directory.exists() && directory.isDirectory()) {
            String[] files = directory.list();
            if (files != null) {
                for (String fileName : files) {
                    partite.getItems().add(fileName.substring(0, 4));

                }
            }
        } else {
            System.err.println("La cartella specificata non esiste o non è una cartella valida.");
        }
    }

    public void eliminaPartita() {
        DataS.eliminaFile(percorsoCartellaProgetto + File.separator + newValue + ".txt");
        testo.setVisible(true);
        mostraPartite();
    }
    public void switchToSetPlayerPage(ActionEvent event) throws IOException {
        gameData.setTipo("Partita");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SetPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToTournamentPage(ActionEvent event) throws IOException {
        gameData.setTipo("Torneo");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TournamentPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAdminPlayerPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
