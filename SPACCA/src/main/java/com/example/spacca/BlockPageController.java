package com.example.spacca;

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

public class BlockPageController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label timer;
    private Timeline timeline;
    private int seconds;
    @FXML
    public void initialize() {
        seconds=1;
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateTimer));
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
        System.out.println("ciao");
        root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage =(Stage) timer.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

