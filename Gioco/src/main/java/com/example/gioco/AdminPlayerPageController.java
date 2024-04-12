package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.awt.Desktop;
import javafx.stage.Stage;
import java.io.*;
import java.util.Objects;

public class AdminPlayerPageController {
    private Scene scene;
    private Parent root;
    private DataSet dataSet;

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
            File file = new File(dataSet.getProjectFolderPath() + File.separator + "/" + "RegoleSPACCA.txt");
            if (file.exists() && file.isFile() && file.getName().endsWith(".txt")) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Il file specificato non esiste o non è un file .txt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
