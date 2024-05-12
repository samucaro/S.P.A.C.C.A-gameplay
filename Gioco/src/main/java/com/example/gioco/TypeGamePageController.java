package com.example.gioco;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TypeGamePageController {
    private Scene scene;
    private Parent root;
    private GameData gameData;
    private DataSet dataSet;
    private String newValue;
    private String percorsoCartellaProgetto;
    @FXML
    private ChoiceBox<String> partite;
    @FXML
    private Button elimina;
    @FXML
    private Text testo;

    @FXML
    public void initialize() {
        dataSet = new DataSet();
        gameData = GameData.getInstance();
        percorsoCartellaProgetto = dataSet.getProjectFolderPath();
        mostraPartite(percorsoCartellaProgetto);
        partite.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    if(newV != null) {
                        elimina.setVisible(true);
                        newValue = newV;
                    }
                }
        );
    }

    //Mostra tutte le partite disponibili
    public void mostraPartite(String percorsoCartellaProgetto) {
        partite.getItems().clear();
        File directory = new File(percorsoCartellaProgetto);
        if(directory.exists() && directory.isDirectory()) {
            String[] files = directory.list();
            if(files != null) {
                for(String fileName : files) {
                    if(!fileName.equals("LeaderBoard.txt")) {
                        if(fileName.length() > 7) {
                            partite.getItems().add(fileName.substring(0, 4));
                        }
                        else {
                            partite.getItems().add(fileName.substring(0, 3));
                        }
                    }
                }
            }
        }
        else {
            System.err.println("La cartella specificata non esiste o non è una cartella valida.");
        }
    }

    //Elimina la partita selezionata
    public void eliminaPartita() {
        dataSet.eliminaFile(percorsoCartellaProgetto + File.separator + newValue + ".txt");
        testo.setVisible(true);
        mostraPartite(percorsoCartellaProgetto);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), testo);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> {
            testo.setVisible(false);
        });
        fadeTransition.play();
    }

    //BACK
    public void switchToAdminPlayerPage(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException ex) {
            mostraErrore();
            System.err.println(ex.getMessage());
        }
    }

    //MULTIPLAYER
    public void switchToSetPlayerPage(ActionEvent event) {
        try {
            gameData.setTipo("Partita");
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SetPlayerPage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException ex) {
            mostraErrore();
            System.err.println(ex.getMessage());
        }
    }

    //TOURNAMENT
    public void switchToTournamentPage(ActionEvent event) {
        try {
            gameData.setTipo("Torneo");
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("TournamentPage.fxml")));
            TournamentPageController tp = new TournamentPageController();
            fxmlLoader.setController(tp);
            root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException ex) {
            mostraErrore();
            System.err.println(ex.getMessage());
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
