package com.example.gioco;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;

public class BlockPageController {
    private Parent root;
    private Scene scene;
    private int seconds;
    private Timeline timeline;
    @FXML
    private Label timer;

    @FXML
    public void initialize() {
        seconds=1;
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateTimer));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //Aggiorna il tempo ogni secondo
    private void updateTimer(ActionEvent event) {
        timer.setText("" + seconds);
        if(seconds == 31) {
            try {
                switchToHomePage();
                timeline.stop();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            seconds++;
        }
    }

    //Torna al login dopo la scadenza dei secondi
    public void switchToHomePage() throws IOException {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginAdminPage.fxml")));
            Stage stage = (Stage) timer.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException e) {
            mostraErrore();
            System.err.println(e.getMessage());
        }
    }

    //Forgot Password
    public void mostraIndovinello() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Indovinello");
            stage.getIcons().add(new Image("logo.png"));
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Indovinello.fxml")));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException ex) {
            mostraErrore();
            System.err.println(ex.getMessage());
        }
    }

    private void mostraErrore() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRORE");
        alert.setHeaderText("Impossibile caricare il contenuto");
        alert.setContentText("Si è verificato un errore durante il caricamento del contenuto. Contatta l'assistenza" +
                "tecnica al seguente numero verde: +393209786308");
        alert.showAndWait();
    }
}
