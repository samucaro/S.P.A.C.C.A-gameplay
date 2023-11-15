package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private static int cont;
    @FXML
    private Label error;

    @FXML
    public void initialize() {
        ut = "samucaro";
        psw = "112233";
        cont=1;

        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    ActionEvent event = new ActionEvent(password, null);
                    try {
                        verifyLogin(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void verifyLogin(ActionEvent event) throws IOException {
        if(username.getText().equals(ut) && password.getText().equals(psw)) {
            cont=1;
            switchToLoginPage(event);
        }
        else {
            error.setVisible(true);
            if(cont == 4) {
                System.out.println("Se sbagli un'altra volta l'accesso verrà bloccato per 30 secondi");
            }
            else if(cont >= 5) {
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

    public void switchToLoginPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("TypeGamePage.fxml"));
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
