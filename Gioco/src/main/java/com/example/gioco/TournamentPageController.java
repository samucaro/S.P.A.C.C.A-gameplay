package com.example.gioco;

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
import java.util.*;

public class TournamentPageController {
    private Scene scene;
    private Parent root;
    private int code;
    private Mazzo m;
    private String[] tipoGiocatore;
    private String[] mani;
    private DataSet DataS = new DataSet();
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
        tipoGiocatore = new String[16];
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
                tipoGiocatore[i] = "Bot";
            }
            else {
                tipoGiocatore[i] = "Persona";
            }
        }
    }

    private String assegnaMano() {
        String str = "";
        for (int j = 1; j <= 5; j++) {
            str += m.pesca().toStringNome() + " ";
        }
        return str;
    }

    public void nuovoFile() {
        impostaTorneo();
        try {
            FileWriter file = new FileWriter((DataS.getProjectFolderPath() + File.separator + "/" + code + ".txt"), true);
            PrintWriter writer = new PrintWriter(file);
            writer.println("Dati Generali Torneo:");
            writer.println("Partita Corrente: 0");
            writer.println("Vincitore: ");
            writer.println("******************************");
            for(int i = 0; i < 15; i++) {
                m = new Mazzo();
                m.componiMazzo();
                writer.println("Numero Partita: " + i);
                if(i <= 7) {
                    writer.println("Stato: Pronta");
                }
                else {
                    writer.println("Stato: Attesa");
                }
                writer.println("Mazzo: " + m.toString());
                writer.println("Scarti: ");
                writer.println("Turno: 0");
                if(i <= 7) {
                    for (int j = 0; j < 2; j++) {
                        writer.println("Giocatore: " + j);
                        writer.println("Tipo: " + tipoGiocatore[i+j]);
                        writer.println("Nome: " + t.get(i+j).getText());
                        writer.println("Mano: " + assegnaMano());
                        writer.println("HpRimanente: " + 5);
                    }
                }
                else {
                    for (int j = 0; j < 2; j++) {
                        writer.println("Giocatore: " + j);
                        writer.println("Tipo: ");
                        writer.println("Nome: ");
                        writer.println("Mano: " + assegnaMano());
                        writer.println("HpRimanente: " + 5);
                    }
                }
                writer.println("******************************");
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
