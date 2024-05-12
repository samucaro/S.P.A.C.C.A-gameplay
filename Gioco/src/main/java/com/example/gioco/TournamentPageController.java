package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.*;

//Primo controller per la schermata del tabellone del torneo, serve alla gestione dei giocatori all'interno dell torneo
public class TournamentPageController implements Initializable {
    private Scene scene;
    private Parent root;
    private Mazzo mazzo;
    private String[] tipoGiocatore;
    private DataSet dataSet;
    private LinkedList<TextField> textFields;
    private int code;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField codice;
    @FXML
    private Button saveLogout;
    @FXML
    private Button back;

    public TournamentPageController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataSet = new DataSet();
        textFields = new LinkedList<>();
        tipoGiocatore = new String[16];
        code = 0;
        setTextField();
        saveLogout.setOnAction(this::switchToAdminPlayerPage);
        back.setOnAction(event -> {
            try {
                switchToTypeGamePage(event);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //Permette di attivare e disattivare il salvataggio sotto determinate condizioni
    private void setTextField() {
        codice.setText(generaCodice());
        saveLogout.setDisable(false);
        for(Node node: anchorPane.getChildren()) {
            if (node instanceof TextField) {
                textFields.add((TextField) node);
            }
        }
    }

    //Genera un codice casuale per identificare il torneo
    private String generaCodice() {
        if(code == 0) {
            do {
                code = (int) (Math.random() * (999 - 100 + 1)) + 100;
            } while (dataSet.checkCode(code));
        }
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
                    for(int j = 0; j < 2; j++) {
                        writer.println("Giocatore: " + j);
                        writer.println("Tipo: " + tipoGiocatore[i+i+j]);
                        writer.println("Nome: " + textFields.get(i+i+j).getText());
                        writer.println("Mano: " + mani[j]);
                        writer.println("HpRimanente: " + 5);
                    }
                }
                else {
                    for(int j = 0; j < 2; j++) {
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
        }
        catch (IOException e) {
            System.err.println("Errore durante la creazione del file: " + e.getMessage());
        }
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
        }
        catch (IOException e) {
            System.err.println("Errore durante la creazione del file: " + e.getMessage());
        }
    }

    private boolean controllaNomi(String nome) {
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

    //Save & Logout
    public void switchToAdminPlayerPage(ActionEvent event) {
        try {
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
        catch (IOException | NullPointerException e) {
            mostraErrore();
            System.err.println(e.getMessage());
        }
    }

    //BACK
    public void switchToTypeGamePage(ActionEvent event) throws IOException {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TypeGamePage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException | NullPointerException e) {
            mostraErrore();
            System.err.println(e.getMessage());
        }
    }

    private void mostraErrore() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRORE");
        alert.setHeaderText("Impossibile caricare il contenuto");
        alert.setContentText("Si è verificato un errore durante il caricamento del contenuto. Contatta l'assistenza" +
                "tecnica al seguente numero verde: +393209786308");
        alert.showAndWait();
    }
}
