package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    @FXML
    private ChoiceBox<String> list;
    @FXML
    private Button start;
    private DataSet dataSet;

    @FXML
    public void initialize() {
        dataSet = new DataSet();
        gameData = GameData.getInstance();
        mostraPartite();
        list.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    if(newV != null)
                        start.setDisable(false);
                }
        );
    }

    //mostra le partite e i tornei disponibili
    private void mostraPartite() {
        File directory = new File(dataSet.getProjectFolderPath());
        if (directory.exists() && directory.isDirectory()) {
            String[] files = directory.list();
            if (files != null) {
                for (String fileName : files) {
                    if(!fileName.equals("LeaderBoard.txt") && !fileName.equals("RegoleSPACCA.txt")) {
                        if(fileName.charAt(3) == '.')
                            list.getItems().add(fileName.substring(0, 3));
                        else
                            list.getItems().add(fileName.substring(0, 4));
                    }
                }
            }
        } else {
            System.err.println("La cartella specificata non esiste o non è una cartella valida.");
        }
    }

    //BACK
    public void switchToAdminPlayerPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //START
    public void switchToGamePage(ActionEvent event) throws IOException {
        if(list.getValue().length() == 4) {
            gameData.leggiFilePartita(Integer.parseInt(list.getValue()));
        }
        else {
            gameData.leggiFileTorneo(Integer.parseInt(list.getValue()));
        }
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TabelloneGioco.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ParallelCamera cam = new ParallelCamera();
        cam.setFarClip(2000);
        cam.setNearClip(0.5);
        scene.setCamera(cam);
        stage.setScene(scene);
        stage.show();
    }
}
