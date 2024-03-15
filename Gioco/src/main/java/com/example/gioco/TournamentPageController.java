package com.example.gioco;

import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
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
import java.util.*;

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
    private int numPartita;
    private LinkedList<TextField> t;
    private LinkedList<Partita> partiteTorneo;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField codice;
    @FXML
    private Button saveLogout;

    @FXML
    public void initialize() {
        t = new LinkedList<>();
        code = 0;
        settaTextField();
    }
    public void switchToTypeGamePage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TypeGamePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Completare
    public void impostaTorneo() {
        int cont = 0;
        for(int i = 0; i < 16; i++) {
            if(t.get(i).getText().isEmpty()) {
                cont++;
                t.get(i).setText("Bot " + cont);
            }
            ArrayList<Giocatore> g = new ArrayList<>();

            String tipo = t.get(i).getText();

            /*partiteTorneo.add(new Partita());*/
        }
    }

    private void nuovoFile() {
        try {
            FileWriter file = new FileWriter((DataS.getProjectFolderPath() + File.separator + "/" + code + ".txt"), true);
            PrintWriter writer = new PrintWriter(file);
            writer.println("Dati Generali Torneo:");
            writer.println("Partita Corrente: " + numPartita);
            writer.println("Vincitore: ");
            writer.println("******************************");
            for(int i = 1; i < 16; i++) {
                writer.println("Numero Partita: " + i);
                writer.println("Mazzo: " + m.toString());
                writer.println("Scarti: ");
                writer.println("Turno: 1");
                writer.println("******************************");
                for (int j = 1; i < 2; i++) {
                    writer.println("Giocatore: " + j);
                    writer.println("Tipo: " + tipoGiocatore[i]);
                    writer.println("Nome: " + nomi[i]);
                    writer.println("Mano: " + mani[i]);
                    writer.println("HpRimanente: " + 5);
                    writer.println("******************************");
                    j++;
                }
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Errore durante la creazione del file: " + e.getMessage());
        }
    }

    /*
    public void switchToGamePage(ActionEvent event) throws IOException {
        String str = list.getValue();
        int codice = Integer.parseInt(str);
        gameData.leggiFile(codice);
        root = FXMLLoader.load(getClass().getResource("Partitonza.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        impostaListener(scene);
        ParallelCamera cam = new ParallelCamera();
        cam.setFarClip(2000);
        cam.setNearClip(0.5);
        scene.setCamera(cam);
        stage.setScene(scene);
        stage.show();
    }
     */
    private void settaTextField() {
        int i = 0;
        for(Node node: anchorPane.getChildren()) {
            if (node instanceof TextField) {
                t.add((TextField) node);
                if (i<16) {
                    t.get(i).textProperty().addListener((observable, oldValue, newValue) -> {
                        boolean ver = true;
                        for (int j = 0; j<16; j++) {
                            ver = ver && t.get(j).getText().isEmpty();
                        }
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
}
