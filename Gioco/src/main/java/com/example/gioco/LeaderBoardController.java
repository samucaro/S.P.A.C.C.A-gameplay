package com.example.gioco;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class LeaderBoardController {
    @FXML
    private TableView<Costumer> tableView;
    @FXML
    private TableColumn<Costumer, Integer> rank;
    @FXML
    private TableColumn<Costumer, String> giocatore;
    @FXML
    private TableColumn<Costumer, Integer> punteggio;
    private static int cont = 0;
    private GameData gameData;

    @FXML
    public void initialize() {
        gameData = GameData.getInstance();
        rank.setCellValueFactory(new PropertyValueFactory<Costumer, Integer>("rank"));
        giocatore.setCellValueFactory(new PropertyValueFactory<Costumer, String>("giocatore"));
        punteggio.setCellValueFactory(new PropertyValueFactory<Costumer, Integer>("punteggio"));
        submit();
    }

    private void submit() {
        boolean var = false;
        Costumer costumer;
        ObservableList<Costumer> costumers;
        for(int i = 0; i < gameData.getGiocatoriPartita().size(); i++) {
            for(int j = 0; j < tableView.getItems().size(); j++) {
                if(tableView.getItems().get(j).getNome().equals(gameData.getGiocatoriPartita().get(i).getNome())) {
                    if(gameData.getGiocatoriPartita().get(i).getNome() != null || TabelloneGiocoController.vincitore.getNome().equals(tableView.getItems().get(j).getNome())) {
                        tableView.getItems().get(j).setPunteggio(3);
                    }
                    var = true;
                    break;
                }
            }
            if(!var) {
                if(gameData.getGiocatoriPartita().get(i).getNome() != null || gameData.getGiocatoriPartita().get(i).getNome().equals(TabelloneGiocoController.vincitore.getNome())) {
                    costumer = new Costumer(cont++, gameData.getGiocatoriPartita().get(i).getNome(), 3);
                    tableView.getItems().add(costumer);
                }
                else {
                    costumer = new Costumer(cont++, gameData.getGiocatoriPartita().get(i).getNome(), 0);
                    tableView.getItems().add(costumer);
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
                tableView.getItems().set(i, tableView.getItems().get(min));
                tableView.getItems().set(min, costumer);
            }
        }
    }
}
