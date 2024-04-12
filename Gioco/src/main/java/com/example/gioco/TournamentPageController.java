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
    private Mazzo mazzo;
    private String[] tipoGiocatore;
    private DataSet dataSet;
    private LinkedList<TextField> textFields;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField codice;
    @FXML
    private Button saveLogout;

    @FXML
    public void initialize() {
        dataSet = new DataSet();
        textFields = new LinkedList<>();
        tipoGiocatore = new String[16];
        code = 0;
        setTextField();
    }

    //Permette di attivare e disattivare il salvataggio sotto determinate condizioni
    private void setTextField() {
        int i = 0;
        for(Node node: anchorPane.getChildren()) {
            if (node instanceof TextField) {
                textFields.add((TextField) node);
                if (i<16) {
                    textFields.get(i).textProperty().addListener((observable, oldValue, newValue) -> {
                        boolean var = true;
                        for (int j = 0; j<16; j++) {
                            var = var && textFields.get(j).getText().isEmpty();
                        }
                        saveLogout.setDisable(var);
                        if (!var) {
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

    //Genera un codice casuale per identificare il torneo
    private String generaCodice() {
        if (code == 0)
            do {
                code = (int) (Math.random() * (999 - 100 + 1)) + 100;
            } while (dataSet.checkCode(code));
        return "" + code;
    }

    //Setta i bot nei TextField liberi e salva il tipo
    private void impostaTorneo() {
        int cont = 0;
        for(int i = 0; i < 16; i++) {
            if(textFields.get(i).getText().isEmpty()) {
                cont++;
                textFields.get(i).setText("Bot " + cont);
                tipoGiocatore[i] = "Bot";
            }
            else {
                tipoGiocatore[i] = "Persona";
            }
        }
    }

    //Imposta la mano iniziale di ogni giocatore
    private String assegnaMano() {
        StringBuilder str = new StringBuilder();
        for (int j = 1; j <= 5; j++) {
            str.append(mazzo.pesca().toStringNome()).append(" ");
        }
        return str.toString();
    }

    //Crea il file da dove leggere i dati seguendo una logica ben precisa
    private void nuovoFile() {
        try {
            FileWriter file = new FileWriter((dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"), true);
            PrintWriter writer = new PrintWriter(file);
            writer.println("Dati Generali Torneo:");
            writer.println("Partita Corrente: 0");
            writer.println("Vincitore: ");
            writer.println("******************************");
            for(int i = 0; i < 15; i++) {
                mazzo = new Mazzo();
                mazzo.componiMazzo();
                writer.println("Numero Partita: " + i);
                if(i <= 7) {
                    writer.println("Stato: Pronta");
                }
                else {
                    writer.println("Stato: Attesa");
                }
                writer.println("Mazzo: " + mazzo.toString());
                writer.println("Scarti: ");
                writer.println("Turno: 0");
                String[] mani = {assegnaMano(), assegnaMano()};
                if(i <= 7) {
                    for (int j = 0; j < 2; j++) {
                        writer.println("Giocatore: " + j);
                        writer.println("Tipo: " + tipoGiocatore[i+i+j]);
                        writer.println("Nome: " + textFields.get(i+i+j).getText());
                        writer.println("Mano: " + mani[j]);
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

    //Save & Logout
    public void switchToAdminPlayerPage(ActionEvent event) throws IOException {
        dataSet.creaFile(code);
        impostaTorneo();
        nuovoFile();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //BACK
    public void switchToTypeGamePage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TypeGamePage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
