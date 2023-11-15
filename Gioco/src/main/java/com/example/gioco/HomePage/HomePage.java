package com.example.gioco.HomePage;

import javafx.application.Application;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.logging.Logger;

public class HomePage extends Application {
    private static final Logger logger = Logger.getLogger(HomePage.class.getName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("S.P.A.C.C.A.");
            URL url = getClass().getResource("HomePage.fxml");
            if (url == null) {
                throw new IOException("FXML file not found");
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Image logo = new Image("com/example/gioco/logo.png");
            Scene scene = new Scene(root);
            primaryStage.getIcons().add(logo);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(IOException e1) {
            System.out.println(e1.getMessage());
        } catch(Exception e) {
            logger.log(Level.SEVERE, "An error occurred", e);
        }
    }
}
