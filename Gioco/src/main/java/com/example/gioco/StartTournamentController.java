package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

//Classe che gestisce il corso del torneo
public class StartTournamentController implements Initializable {
    private DataSet dataSet;
    private GameData gameData;
    private int code;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button start;
    @FXML
    private Button back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataSet = new DataSet();
        gameData = GameData.getInstance();
        start.setVisible(true);
        start.setOnAction(this::switchToGamePage);
        back.setOnAction(this::switchToAdminPlayerPage);
        disableTextField();
    }

    private void disableTextField() {
        for(int i = 0; i < anchorPane.getChildren().size(); i++) {
            if(anchorPane.getChildren().get(i) instanceof TextField) {
                anchorPane.getChildren().get(i).setDisable(true);
            }
        }
    }

    //leggendo il file del torneo setta in automatico i nomi dei text field con i vari vincitori
    public void leggiFile(int code) {
        try {
            this.code = code;
            BufferedReader reader = new BufferedReader(new FileReader(dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"));
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Nome")) {
                    if (anchorPane.getChildren().get(index) instanceof TextField) {
                        ((TextField) anchorPane.getChildren().get(index)).setText(line.split(":")[1].trim());
                    }
                    index++;
                } else if (line.startsWith("Vincitore")) {
                    if (line.trim().split(":").length > 1) {
                        start.setDisable(true);
                        ((TextField) anchorPane.getChildren().get(30)).setText(line.split(":")[1].trim());
                    }
                }
            }
            reader.close();
            ((TextField) anchorPane.getChildren().getLast()).setText("" + code);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToGamePage(ActionEvent event) {
        try {
            gameData.leggiFile(code);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TabelloneGioco.fxml")));
            ParallelCamera cam = new ParallelCamera();
            cam.setFarClip(2000);
            cam.setNearClip(0.5);
            Scene scene = new Scene(root);
            scene.setCamera(cam);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException e) {
            mostraErrore();
            System.err.println(e.getMessage());
        }
    }

    public void switchToAdminPlayerPage(ActionEvent event) {
        try {
            GameData.resetInstance();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
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