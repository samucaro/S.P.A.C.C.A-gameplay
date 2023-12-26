package com.example.gioco;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;

public class BlockPageController {
    private int seconds;
    @FXML
    private Label timer;

    @FXML
    public void initialize() {
        seconds=1;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateTimer));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTimer(ActionEvent event) {
        timer.setText("" + seconds);
        seconds++;
        if(seconds == 32) {
            try {
                switchToHomePage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void switchToHomePage() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomePage.fxml")));
        Stage stage = (Stage) timer.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
