package com.example.gioco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class StartGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
            primaryStage.setTitle("S.P.A.C.C.A.");
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
            Image logo = new Image("logo.png");
            Scene scene = new Scene(root);
            primaryStage.getIcons().add(logo);
            primaryStage.setScene(scene);
            primaryStage.show();
    }
}
