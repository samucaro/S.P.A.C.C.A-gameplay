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

    @FXML
    public void initialize() {

    }

    private String getProjectFolderPath() {
        String currentDirectory = System.getProperty("user.dir");
        String projectFolderPath = currentDirectory + File.separator + "src/main/resources";
        File folder = new File(projectFolderPath);
        if (!folder.exists()) {
            System.err.println("La cartella non esiste!");
        }
        return projectFolderPath;
    }

    public void mostraRegolamento() {
        try {
            File file = new File(getProjectFolderPath() + File.separator + "/" + "RegoleSPACCA.txt");
            if (file.exists() && file.isFile() && file.getName().endsWith(".txt")) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Il file specificato non esiste o non è un file .txt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToLoginAdminPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginAdminPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSetGamePage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SetGamePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
