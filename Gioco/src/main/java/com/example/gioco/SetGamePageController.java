package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import java.io.File;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class SetGamePageController {
    private Scene scene;
    private Parent root;
    private GameData gameData;
    private DataSet dataSet;
    @FXML
    private ChoiceBox<String> list;
    @FXML
    private Button start;
    @FXML
    private Button leaderBoardButton;

    @FXML
    public void initialize() {
        dataSet = new DataSet();
        gameData = GameData.getInstance();
        mostraPartite();
        list.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    if(newV != null) {
                        start.setDisable(false);
                    }
                }
        );
    }

    //mostra le partite e i tornei disponibili
    private void mostraPartite() {
        File directory = new File(dataSet.getProjectFolderPath());
        if(directory.exists() && directory.isDirectory()) {
            String[] files = directory.list();
            if(files != null) {
                for(String fileName : files) {
                    if(!fileName.equals("LeaderBoard.txt")) {
                        if(fileName.charAt(3) == '.') {
                            list.getItems().add(fileName.substring(0, 3));
                        }
                        else {
                            list.getItems().add(fileName.substring(0, 4));
                        }
                    }
                }
            }
        }
        else {
            System.err.println("La cartella specificata non esiste o non è una cartella valida.");
        }
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

    //START
    public void switchToGamePage(ActionEvent event) {
        try {
            if(Integer.parseInt(list.getValue()) <= 999) {
                FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("TournamentPage.fxml")));
                StartTournamentController st = new StartTournamentController();
                fxmlLoader.setController(st);
                root = fxmlLoader.load();
                scene = new Scene(root);
                st.leggiFile(Integer.parseInt(list.getValue()));
            }
            else {
                gameData.leggiFile(Integer.parseInt(list.getValue()));
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TabelloneGioco.fxml")));
                ParallelCamera cam = new ParallelCamera();
                cam.setFarClip(2000);
                cam.setNearClip(0.5);
                scene = new Scene(root);
                scene.setCamera(cam);

            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException ex) {
            start.setDisable(true);
            mostraErrore();
            System.err.println(ex.getMessage());
        }
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

    private void mostraErrore() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRORE");
        alert.setHeaderText("Impossibile caricare il contenuto");
        alert.setContentText("Si è verificato un errore durante il caricamento del contenuto. Contatta l'assistenza" +
                "tecnica al seguente numero verde: +393209786308");
        alert.showAndWait();
    }
}
