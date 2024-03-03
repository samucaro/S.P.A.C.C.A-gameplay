package com.example.gioco;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SetGamePageController {
    private Scene scene;
    private Parent root;
    private GameData gameData = GameData.getInstance();
    @FXML
    private ListView list;
    @FXML
    private TextField g1;
    @FXML
    private TextField g2;
    @FXML
    private TextField g3;
    @FXML
    private TextField g4;
    @FXML
    private TextField g5;
    @FXML
    private TextField g6;
    @FXML
    private TextField g7;
    @FXML
    private TextField g8;
    @FXML
    public void initialize() {
        ObservableList<Integer> items = FXCollections.observableArrayList();
        items.add(1);
        items.add(2);
        items.add(3);
        items.add(4);
        list.setItems(items);

        mostraNomi();
    }

    public void mostraNomi() {
        g1.setVisible(true);
        g2.setVisible(true);
        g3.setVisible(true);
        g4.setVisible(true);
        g5.setVisible(true);
        g6.setVisible(true);
        g7.setVisible(true);
        g8.setVisible(true);
    }

    public void switchToAdminPlayerPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public void impostaListener(Scene scene){
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            OvalPaneController.setScenaX((Double) newVal);
            MainController.setScenaX((Double) newVal);
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            OvalPaneController.setScenaY((Double) newVal);
            MainController.setScenaY((Double) newVal);
        });
    }

    //switchare sulla partita direttamente
    public void switchToGamePage(ActionEvent event) throws IOException {
        gameData.getGG();
        gameData.setMazzo(new Mazzo());
        //System.out.println(gameData.getGiocatoriPartita().toString());
        root = FXMLLoader.load(getClass().getResource("Partitonza.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //stage.setMinWidth(900);
        //stage.setMinHeight(600);
        scene = new Scene(root);
        impostaListener(scene);
        ParallelCamera cam = new ParallelCamera();
        cam.setFarClip(2000);
        cam.setNearClip(0.5);
        scene.setCamera(cam);
        stage.setScene(scene);
        stage.show();
    }
}
