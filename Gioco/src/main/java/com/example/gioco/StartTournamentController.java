package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        start.setOnAction(event -> {
            try {
                switchToGamePage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        back.setOnAction(event -> {
            try {
                switchToAdminPlayerPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        disableTextField();
    }

    private void disableTextField() {
        for(int i = 0; i < anchorPane.getChildren().size(); i++) {
            if(anchorPane.getChildren().get(i) instanceof TextField) {
                anchorPane.getChildren().get(i).setDisable(true);
            }
        }
    }

    public void leggiFile(int code) throws IOException {
        this.code = code;
        BufferedReader reader = new BufferedReader(new FileReader(dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"));
        String line;
        int index = 0;
        while ((line = reader.readLine()) != null) {
            if(line.startsWith("Nome")) {
                if(anchorPane.getChildren().get(index) instanceof TextField) {
                    ((TextField) anchorPane.getChildren().get(index)).setText(line.split(":")[1].trim());
                    System.out.println(index + " NODO DI NOME " + ((TextField) anchorPane.getChildren().get(index)).getText());
                }
                index++;
            } else if (line.startsWith("Vincitore")) {
                if (line.trim().split(":").length>1){
                    start.setDisable(true);
                    ((TextField) anchorPane.getChildren().get(30)).setText(line.split(":")[1].trim());
                }
            }
        }
        reader.close();
        ((TextField) anchorPane.getChildren().getLast()).setText("" + code);
    }

    public void switchToGamePage(ActionEvent event) throws IOException {
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

    public void switchToAdminPlayerPage(ActionEvent event) throws IOException {
        GameData.resetInstance();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}