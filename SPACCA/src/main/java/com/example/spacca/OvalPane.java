package com.example.spacca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OvalPane extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Partitonza.fxml"));
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        Scene scene = new Scene(root);
        impostaListener(scene);
        PerspectiveCamera cam = new PerspectiveCamera();
        scene.setCamera(cam);
        primaryStage.setTitle("Test JFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void impostaListener(Scene scene){
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            OvalPaneController.setScenaX((Double) newVal);
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            OvalPaneController.setScenaY((Double) newVal);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}