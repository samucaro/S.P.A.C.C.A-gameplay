package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.util.Objects;

public class AdminPlayerPageController {
    private Scene scene;
    private Parent root;
    @FXML
    private Button regoleButton;
    @FXML
    private Text errorMessage;

    @FXML
    public void initialize() {}

    //LOGIN ADMIN
    public void switchToLoginAdminPage(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginAdminPage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException e) {
            mostraErrore();
            System.out.println(e.getMessage());
        }
    }

    //LOGIN PLAYER
    public void switchToSetGamePage(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SetGamePage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException e) {
            mostraErrore();
            System.out.println(e.getMessage());
        }
    }

    //REGOLE
    public void mostraRegolamento() {
        try {
            Stage stage = new Stage();
            /*stage.setMinWidth(612.5);
            stage.setMinHeight(412.5);
            stage.setMaxWidth(612.5);
            stage.setMaxHeight(412.5);*/
            stage.setTitle("Regolamento");
            stage.getIcons().add(new Image("logo.png"));
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Regolamento.fxml")));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException ex) {
            regoleButton.setDisable(true);
            errorMessage.setVisible(true);
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
