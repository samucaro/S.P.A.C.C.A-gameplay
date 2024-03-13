package com.example.gioco;

import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Objects;
import java.util.Vector;

public class TournamentPageController {
    private Scene scene;
    private Parent root;
    private int code;
    private Mazzo m;
    private String[] tipoGiocatore;
    private String[] mani;
    private String[] nomi;
    private DataSet DataS = new DataSet();
    private GameData gameData = GameData.getInstance();
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField codice;
    @FXML
    private Button saveLogout;

    @FXML
    public void initialize() {
        System.out.println("cacca");
        //non entra????
        anchorPane.getChildren().addListener(
                (ListChangeListener<? super Node>) (ObservableList) -> {
                    System.out.println("cacca");
                    verificaTesto();
                }
        );
    }
    public void switchToTypeGamePage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TypeGamePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void verificaTesto() {
        for(Node node: anchorPane.getChildren()) {
            if (node instanceof TextField) {
                if (!((TextField) node).getText().isEmpty()) {
                    codice.setText(generaCodice());
                    saveLogout.setDisable(false);
                    System.out.println("Almeno un TextField contiene del testo.");
                }
            }
        }
    }

    private String generaCodice() {
        do {
            code = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;
        } while (DataS.checkCode(code));
        return "" + code;
    }
    private void nuovoFile() {
        Vector<Integer> vector = new Vector<>();
        for (int i = 0; i < 16; i++)
            vector.add(i);
        Collections.shuffle(vector);
        try {
            FileWriter file = new FileWriter((DataS.getProjectFolderPath() + File.separator + "/" + code + ".txt"), true);
            PrintWriter writer = new PrintWriter(file);
            writer.println("Dati Generali Torneo:");
            writer.println("NumGiocatori: " + 16);
            writer.println("Turno: 0");
            writer.println("Mazzo: " + m.toString());
            writer.println("Scarti: ");
            writer.println("******************************");
            int j = 1;
            for (int i : vector) {
                writer.println("Giocatore: " + j);
                writer.println("Tipo: " + tipoGiocatore[i]);
                writer.println("Nome: " + nomi[i]);
                writer.println("Mano: " + mani[i]);
                writer.println("HpRimanente: " + 5);
                writer.println("******************************");
                j++;
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Errore durante la creazione del file: " + e.getMessage());
        }
    }
}
