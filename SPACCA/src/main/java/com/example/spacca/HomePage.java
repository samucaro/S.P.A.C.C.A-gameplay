package com.example.spacca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HomePage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("S.P.A.C.C.A.");
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            Image logo = new Image("logo.png");
            Scene scene = new Scene(root);
            primaryStage.getIcons().add(logo);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
