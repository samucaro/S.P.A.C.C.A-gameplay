package com.example.gioco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OvalPane extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Partitonza.fxml"));
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        Scene scene = new Scene(root);
        impostaListener(scene);
        ParallelCamera cam = new ParallelCamera();
        cam.setFarClip(2000);
        cam.setNearClip(0.5);
        scene.setCamera(cam);
        primaryStage.setTitle("S.P.A.C.C.A.");
        Image logo = new Image("logo.png");
        primaryStage.getIcons().add(logo);
        primaryStage.setScene(scene);
        primaryStage.show();
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

    public static void main(String[] args) {
        launch(args);
    }
}