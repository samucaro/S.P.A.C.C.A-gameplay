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
    private GameData gameData = GameData.getInstance();
    @FXML
    private ChoiceBox<String> list;
    @FXML
    private Button start;
    private DataSet DataS = new DataSet();
    @FXML
    public void initialize() {
        mostraPartite();
        list.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    if(newV != null)
                        start.setDisable(false);
                }
        );
    }

    public void mostraPartite() {
        String percorsoCartellaProgetto = DataS.getProjectFolderPath();
        File directory = new File(percorsoCartellaProgetto);
        if (directory.exists() && directory.isDirectory()) {
            String[] files = directory.list();
            if (files != null) {
                for (String fileName : files) {
                    if(fileName.charAt(3) == '.')
                        list.getItems().add(fileName.substring(0, 3));
                    else
                        list.getItems().add(fileName.substring(0, 4));
                }
            }
        } else {
            System.err.println("La cartella specificata non esiste o non è una cartella valida.");
        }
    }

    public void switchToAdminPlayerPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void impostaListener(Scene scene){
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            OvalPaneController.setScenaX((Double) newVal);
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            OvalPaneController.setScenaY((Double) newVal);
        });
    }

    //switchare sulla partita direttamente
    public void switchToGamePage(ActionEvent event) throws IOException {
        String str = list.getValue();
        int codice = Integer.parseInt(str);
        gameData.leggiFilePartita(codice);
        root = FXMLLoader.load(getClass().getResource("Partitonza.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        impostaListener(scene);
        ParallelCamera cam = new ParallelCamera();
        cam.setFarClip(2000);
        cam.setNearClip(0.5);
        scene.setCamera(cam);
        stage.setScene(scene);
        stage.show();
    }
}
