package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class TypeGamePageController {
    private Scene scene;
    private Parent root;
    private GameData gameData = GameData.getInstance();
    @FXML
    public void initialize() {
    }
    public void switchToSetPlayerPage(ActionEvent event) throws IOException {
        gameData.setTipo("Partita");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SetPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToTournamentPage(ActionEvent event) throws IOException {
        gameData.setTipo("Torneo");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TournamentPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAdminPlayerPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
