package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.*;

public class TournamentPageController implements Initializable {
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
    private Button start;
    @FXML
    private Button back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataSet = new DataSet();
        textFields = new LinkedList<>();
        tipoGiocatore = new String[16];
        code = 0;
        setTextField();
        saveLogout.setOnAction(event -> {
            try {
                switchToAdminPlayerPage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        back.setOnAction(event -> {
            try {
                switchToTypeGamePage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //Permette di attivare e disattivare il salvataggio sotto determinate condizioni
    private void setTextField() {
        int i = 0;
        codice.setText(generaCodice());
        saveLogout.setDisable(false);
        for(Node node: anchorPane.getChildren()) {
            if (node instanceof TextField) {
                textFields.add((TextField) node);
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
                writer.println("Numero Partita: " + i);
                writer.println("Dati Generali Partita:");
                writer.println("NumGiocatori: " + 2);
                writer.println("Turno: 0");
                mazzo = new Mazzo();
                mazzo.componiMazzo();
                writer.println("Mazzo: " + mazzo.toString());
                writer.println("Scarti: ");
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
        aggiornaFileLeaderBoard();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminPlayerPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Salvando i giocatori carica i nomi in un file per aggiornare la LeaderBoard
    private void aggiornaFileLeaderBoard() {
        try {
            FileWriter file = new FileWriter((dataSet.getProjectFolderPath() + File.separator + "/" + "LeaderBoard.txt"), true);
            PrintWriter writer = new PrintWriter(file);
            for(int i = 0; i < anchorPane.getChildren().size()-1; i++) {
                if(anchorPane.getChildren().get(i) instanceof TextField) {
                    String nome = ((TextField) anchorPane.getChildren().get(i)).getText();
                    if (!controllaNomi(nome)) {
                        if (!nome.isEmpty() && !nome.split(" ")[0].equals("Bot")) {
                            writer.println(nome + " 0");
                        }
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Errore durante la creazione del file: " + e.getMessage());
        }
    }

    private boolean controllaNomi(String nome) throws IOException {
        boolean check = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataSet.getProjectFolderPath() + File.separator + "/" + "LeaderBoard.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.split(" ")[0];
                if (word.equals(nome)) {
                    check = true;
                    break;
                }
            }
        }
        catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
        return check;
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
