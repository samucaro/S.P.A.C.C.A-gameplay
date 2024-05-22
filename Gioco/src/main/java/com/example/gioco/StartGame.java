package com.example.gioco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class StartGame extends Application {

    public static void main(String[] args) {
        try {
            launch(args);
        }
        catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Impossibile caricare il contenuto");
            alert.setContentText("Si è verificato un errore in RunTime.\nUn avviso è già stato inoltrato all'assistenza." +
                    " Riprovi più tardi.\n" + "Errore: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
            primaryStage.setTitle("S.P.A.C.C.A.");
            primaryStage.setMinWidth(550);
            primaryStage.setMinHeight(280);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
            Image logo = new Image("logo.png");
            Scene scene = new Scene(root);
            primaryStage.getIcons().add(logo);
            primaryStage.setScene(scene);
            primaryStage.show();
    }
}
