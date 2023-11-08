package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;


public class HomePageController {
    private String psw;
    private String ut;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    private static int cont;
    @FXML
    private Label error;

    @FXML
    public void initialize() {
        ut = "samucaro";
        psw = "Fiorentina60S!";
        cont=1;
    }

    public void verifyLogin(ActionEvent event) throws IOException {
        if(username.getText().equals("samucaro") && password.getText().equals(psw)) {
            System.out.println("Accesso Riuscito!");
            cont=1;
        }
        else {
            error.setVisible(true);
            if(cont == 4) {
                System.out.println("Se sbagli un'altra volta l'accesso verrà bloccato per 30 secondi");
            }
            else if(cont == 5) {
                switchToBlockPage(event);
            }
            else if(cont == 6) {
                switchToBlockPage(event);
            }
            else if(cont == 7) {
                switchToBlockPage(event);
            }
            else if(cont >= 8) {
                switchToBlockPage(event);
            }
            cont++;
            username.deleteText(0, username.getLength());
            password.deleteText(0, password.getLength());
        }
    }

    public void switchToBlockPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("BlockPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setPassword(String psw) {
        this.psw = psw;
    }
    private void setUsername(String ut) {
        this.ut = ut;
    }
}