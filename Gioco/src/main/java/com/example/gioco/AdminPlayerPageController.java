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
    private DataSet dataSet;
    @FXML
    private Button regoleButton;
    @FXML
    private Text errorMessage;

    @FXML
    public void initialize() {
        dataSet = new DataSet();
    }

    //LOGIN ADMIN
    public void switchToLoginAdminPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginAdminPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //LOGIN PLAYER
    public void switchToSetGamePage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SetGamePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Regole
    public void mostraRegolamento() {
        try {
            Stage stage = new Stage();
            stage.setMinWidth(600);
            stage.setMinHeight(400);
            stage.setMaxWidth(600);
            stage.setMaxHeight(400);
            stage.setTitle("Regolamento");
            stage.getIcons().add(new Image("logo.png"));
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Regolamento.fxml")));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException ex) {
            mostraErrore();
            System.out.println(ex.getMessage());
        }
    }

    private void mostraErrore() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRORE");
        alert.setHeaderText("Impossibile caricare il regolamento");
        alert.setContentText("Si è verificato un errore durante il caricamento del regolamento. Contatta l'assistenza tecnica.");
        alert.showAndWait();
        regoleButton.setDisable(true);
        errorMessage.setVisible(true);
    }
}
