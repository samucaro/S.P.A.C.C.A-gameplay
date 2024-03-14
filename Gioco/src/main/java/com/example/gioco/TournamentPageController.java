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
import java.util.LinkedList;
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
        code = 0;
        System.out.println("cacca");

        settaTextF();
        //non entra????
    }
    public void switchToTypeGamePage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TypeGamePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void settaTextF() {
        int i = 0;
        LinkedList<TextField> t = new LinkedList<>();
        for(Node node: anchorPane.getChildren()) {
            if (node instanceof TextField) {
                t.add((TextField) node);
                if (i<16) {
                    t.get(i).textProperty().addListener((observable, oldValue, newValue) -> {
                        System.out.println("yoooo");
                        boolean ver = true;
                        for (int j = 0; j<16; j++)
                            ver = ver&&t.get(j).getText().isEmpty();
                        saveLogout.setDisable(ver);
                        if (!ver) {
                            codice.setText(generaCodice());
                        }
                        else {
                            codice.clear();
                            code = 0;
                        }
                    });
                }
                i++;
            }
        }
    }

    private String generaCodice() {
        if (code == 0)
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
