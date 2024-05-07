package com.example.gioco;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LeaderBoardController implements Initializable {
    @FXML
    private TableView<Costumer> tableView;
    @FXML
    private TableColumn<Costumer, Integer> rank;
    @FXML
    private TableColumn<Costumer, String> giocatore;
    @FXML
    private TableColumn<Costumer, Integer> punteggio;
    private String nomeVincitore;
    private String nomeVincitoreTorneo;
    private final DataSet dataSet = new DataSet();
    private GameData gameData;
    ObservableList<Costumer> data = FXCollections.observableArrayList();

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        gameData = GameData.getInstance();
        rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        giocatore.setCellValueFactory(new PropertyValueFactory<>("giocatore"));
        punteggio.setCellValueFactory(new PropertyValueFactory<>("punteggio"));
        punteggio.setSortable(true);
        nomeVincitore = TabelloneGiocoController.getNomeVincitore();
        nomeVincitoreTorneo = TabelloneGiocoController.getNomeVincitoreTorneo();
        submit();
        System.out.println("NOME PARTITA" + nomeVincitore);
        System.out.println("NOME TORNEO" + nomeVincitoreTorneo);
    }

    //Aggiorna la tabella leggendo il file della LeaderBoard
    private void submit() {
        ArrayList<String> nomi = leggiFile();
        for (int i = 0; i < nomi.size(); i++) {
            if(nomeVincitore.equals(nomi.get(i).split(" ")[0])) {
                if(gameData.getGiocatoriPartita().size() != 2) {
                    aggiornaFile(nomi.get(i).split(" ")[0]);
                    Costumer customer = new Costumer(i + 1, nomi.get(i).split(" ")[0], Integer.parseInt(nomi.get(i).split(" ")[1]) + 3);
                    data.add(customer);
                }
                else {
                    if(nomeVincitoreTorneo.equals(nomi.get(i).split(" ")[0])) {
                        aggiornaFile(nomi.get(i).split(" ")[0]);
                        Costumer customer = new Costumer(i + 1, nomi.get(i).split(" ")[0], Integer.parseInt(nomi.get(i).split(" ")[1]) + 5);
                        data.add(customer);
                    }
                    else {
                        Costumer customer = new Costumer(i+1, nomi.get(i).split(" ")[0], Integer.parseInt(nomi.get(i).split(" ")[1]));
                        data.add(customer);
                    }
                }
            }
            else {
                Costumer customer = new Costumer(i+1, nomi.get(i).split(" ")[0], Integer.parseInt(nomi.get(i).split(" ")[1]));
                data.add(customer);
            }
        }
        sortingRank();
    }

    //Crea un ArrayList di stringhe salvando nome e punteggio di ogni giocatore
    private ArrayList<String> leggiFile() {
        ArrayList<String> nomi = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataSet.getProjectFolderPath() + File.separator + "/" + "LeaderBoard.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                nomi.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
        return nomi;
    }

    //Aggiorna il file delle LeaderBoard ogni volta che vi è un vincitore
    private void aggiornaFile(String nomeVincitore){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataSet.getProjectFolderPath() + File.separator + "/" + "LeaderBoard.txt"));
            StringBuilder contenuto = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.split(" ")[0];
                if(word.equals(nomeVincitore)) {
                    int nuovoPunteggio;
                    if(word.equals(nomeVincitoreTorneo)) {
                        nuovoPunteggio = Integer.parseInt(line.split(" ")[1]) + 5;
                    }
                    else {
                        nuovoPunteggio = Integer.parseInt(line.split(" ")[1]) + 3;
                    }
                    contenuto.append(nomeVincitore).append(" ").append(nuovoPunteggio).append("\n");
                }
                else {
                    contenuto.append(line).append("\n");
                }
            }
            reader.close();
            FileWriter file = new FileWriter((dataSet.getProjectFolderPath() + File.separator + "/" + "LeaderBoard.txt"), false);
            PrintWriter writer = new PrintWriter(file);
            writer.write(contenuto.toString());
            writer.close();
        } catch (IOException e) {
            System.err.println("Errore durante la creazione del file: " + e.getMessage());
        }
    }

    //Ordina la tabella in base al punteggio di ogni giocatore
    private void sortingRank() {
        tableView.setItems(data);
        punteggio.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().add(punteggio);
        tableView.sort();
        for(int i = 0; i < tableView.getItems().size(); i++) {
            tableView.getItems().get(i).setRank(i+1);
        }
    }
}
