package com.example.gioco.HomePage;

import com.example.gioco.Partita.MainController;
import com.example.gioco.Partita.OvalPaneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AccessPlayerPageController {
    private Parent root;
    private Scene scene;
    @FXML
    private BorderPane borderP;
    @FXML
    public void initialize() {
        Image sfondo = new Image(getClass().getResource("com/example/gioco/_33e14802-fae8-45d6-80aa-5b89524679cb.jpg").toString());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(sfondo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        borderP.setBackground(new Background(backgroundImage));
    }

    public void switchOvalPane(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Partitonza.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        scene = new Scene(root);
        impostaListener(scene);
        ParallelCamera cam = new ParallelCamera();
        cam.setFarClip(2000);
        cam.setNearClip(0.5);
        scene.setCamera(cam);
        stage.setScene(scene);
        stage.show();
    }
    public void impostaListener(Scene scene){
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            OvalPaneController.setScenaX((Double) newVal);
            MainController.setScenaX((Double) newVal);
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            OvalPaneController.setScenaY((Double) newVal);
            MainController.setScenaY((Double) newVal);
        });
    }
}
