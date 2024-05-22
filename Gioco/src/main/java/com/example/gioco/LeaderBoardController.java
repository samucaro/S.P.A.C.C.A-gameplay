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
    private final DataSet dataSet = new DataSet();
    ObservableList<Costumer> data = FXCollections.observableArrayList();

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        giocatore.setCellValueFactory(new PropertyValueFactory<>("giocatore"));
        punteggio.setCellValueFactory(new PropertyValueFactory<>("punteggio"));
        punteggio.setSortable(true);
        submit();
    }

    //Aggiorna la tabella leggendo il file della LeaderBoard
    private void submit() {
        ArrayList<String> nomi = leggiFile();
        for (int i = 0; i < nomi.size(); i++) {
            Costumer customer = new Costumer(i+1, nomi.get(i).split(" ")[0], Integer.parseInt(nomi.get(i).split(" ")[1]));
            data.add(customer);
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
