package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class LoginAdminPageController {
    private static int cont;
    private String psw;
    private String ut;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label error;
    @FXML
    private Label timeError;

    @FXML
    public void initialize() {
        ut = "";
        psw = "";
        cont=0;
        username.setOnMouseClicked(event1 -> {
            error.setVisible(false);
            timeError.setVisible(false);
        });
        password.setOnMouseClicked(event1 -> {
            error.setVisible(false);
            timeError.setVisible(false);
        });
    }

    //Verifica che il login sia corretto altrimenti lancia il recupero password
    public void verifyLogin(ActionEvent event) {
        if(username.getText().equals(ut) && password.getText().equals(psw)) {
            switchToLoginPage(event);
        }
        else {
            error.setVisible(true);
            if(cont == 3) {
                timeError.setVisible(true);
            }
            else if(cont >= 4) {
                switchToBlockPage(event);
                cont -= 2;
            }
            cont++;
            username.deleteText(0, username.getLength());
            password.deleteText(0, password.getLength());
        }
    }

    //Errore inserimento ut e psw
    public void switchToBlockPage(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BlockPage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException e) {
            mostraErrore();
            System.err.println(e.getMessage());
        }
    }

    //LOGIN
    public void switchToLoginPage(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TypeGamePage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException e) {
            mostraErrore();
            System.err.println(e.getMessage());
        }
    }

    //BACK
    public void switchToAdminPlayerPage(ActionEvent event) throws IOException {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException e) {
            mostraErrore();
            System.err.println(e.getMessage());
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
