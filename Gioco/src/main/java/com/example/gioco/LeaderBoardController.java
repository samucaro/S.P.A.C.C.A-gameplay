package com.example.gioco;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private static int cont = 1;
    private GameData gameData;

    ObservableList<Costumer> data = FXCollections.observableArrayList();

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        gameData = GameData.getInstance();
        rank.setCellValueFactory(new PropertyValueFactory<Costumer, Integer>("rank"));
        giocatore.setCellValueFactory(new PropertyValueFactory<Costumer, String>("giocatore"));
        punteggio.setCellValueFactory(new PropertyValueFactory<Costumer, Integer>("punteggio"));
        submit();
    }

    private void submit() {
        boolean var = false;
        ArrayList<Costumer> costumers = new ArrayList<>();
        for(int i = 0; i < gameData.getGiocatoriPartita().size(); i++) {
            for(int j = 0; j < tableView.getItems().size(); j++) {
                if(tableView.getItems().get(j).getGiocatore().equals(gameData.getGiocatoriPartita().get(i).getNome())) {
                    if(gameData.getGiocatoriPartita().get(i).getNome() != null || TabelloneGiocoController.getNomeVincitore().equals(tableView.getItems().get(j).getGiocatore())) {
                        tableView.getItems().get(j).setPunteggio(3);
                    }
                    var = true;
                    break;
                }
            }
            if(!var) {
                if(gameData.getGiocatoriPartita().get(i).getNome().equals(TabelloneGiocoController.getNomeVincitore())) {
                    Costumer costumer = new Costumer(cont++, gameData.getGiocatoriPartita().get(i).getNome(), 3);
                    data.add(costumer);
                    tableView.setItems(data);
                }
                else {
                    Costumer costumer = new Costumer(cont++, gameData.getGiocatoriPartita().get(i).getNome(), 0);
                    data.add(costumer);
                    tableView.setItems(data);
                }
            }

        }
        sortingRank();
    }

    private void sortingRank() {
        for(int i = 0; i < tableView.getItems().size(); i++) {
            int min = i;
            for(int j = i+1; j < tableView.getItems().size(); j++) {
                if(tableView.getItems().get(j).getPunteggio() > tableView.getItems().get(min).getPunteggio()) {
                    min = j;
                }
            }
            if(i != min) {
                Costumer costumer = tableView.getItems().get(i);
                int rank = tableView.getItems().get(i).getRank();
                costumer.setRank(tableView.getItems().get(min).getRank());
                tableView.getItems().set(i, tableView.getItems().get(min));
                tableView.getItems().set(min, costumer);
                tableView.getItems().get(min).setRank(rank);
            }
        }
    }
}
