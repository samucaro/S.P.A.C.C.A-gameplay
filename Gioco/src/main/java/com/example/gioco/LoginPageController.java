package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class LoginPageController {
    private int numPersone;
    private int numGiocatori;
    @FXML
    private CheckBox rispostaSi;
    @FXML
    private CheckBox rispostaNo;
    @FXML
    private MenuButton numRobot;

    @FXML
    public void initialize() {
        numPersone = 0;
        numGiocatori = 0;
    }

    @FXML
    public void opzione1(ActionEvent event) {
        numGiocatori = 3;
        System.out.println("Hai selezionato 3 giocatori");
    }
    @FXML
    public void opzione2(ActionEvent event) {
        numGiocatori = 4;
        System.out.println("Hai selezionato 4 giocatori");
    }
    @FXML
    public void opzione3(ActionEvent event) {
        numGiocatori = 5;
        System.out.println("Hai selezionato 5 giocatori");
    }
    @FXML
    public void opzione4(ActionEvent event) {
        numGiocatori = 6;
        System.out.println("Hai selezionato 6 giocatori");
    }
    @FXML
    public void opzione5(ActionEvent event) {
        numGiocatori = 7;
        System.out.println("Hai selezionato 7 giocatori");
    }
    @FXML
    public void opzione6(ActionEvent event) {
        numGiocatori = 8;
        System.out.println("Hai selezionato 8 giocatori");
    }

    @FXML
    public void activeChois(ActionEvent event) {
        rispostaSi.setVisible(true);
        rispostaNo.setVisible(true);
    }
    public void giocoConRobot(ActionEvent event) {
        if(rispostaSi.isSelected()) {
            rispostaNo.setSelected(false);
        }
        numRobot.setVisible(true);
        System.out.println("Giochi contro almeno 1 robot");
    }
    @FXML
    public void giocoSenzaRobot(ActionEvent event) {
        if(rispostaNo.isSelected()) {
            rispostaSi.setSelected(false);
            numRobot.setVisible(false);
        }
        System.out.println("Giochi senza robot");
    }

    @FXML
    public void setNumRobot(MouseEvent event) {
        System.out.println("eccomi");
        for(int i=7; i>numGiocatori-1; i--) {
            System.out.println("ciao");
            numRobot.getItems().get(i).setVisible(false);
        }
        numRobot.show();
    }

    @FXML
    public void opzione1R(ActionEvent event) {
        numPersone = numGiocatori-1;
        System.out.println("Hai selezionato 1 robot");
    }
    @FXML
    public void opzione2R(ActionEvent event) {
        numPersone = numGiocatori-2;
        System.out.println("Hai selezionato 2 robot");
    }
    @FXML
    public void opzione3R(ActionEvent event) {
        numPersone = numGiocatori-3;
        System.out.println("Hai selezionato 3 robot");
    }
    @FXML
    public void opzione4R(ActionEvent event) {
        numPersone = numGiocatori-4;
        System.out.println("Hai selezionato 4 robot");
    }
    @FXML
    public void opzione5R(ActionEvent event) {
        numPersone = numGiocatori-5;
        System.out.println("Hai selezionato 5 robot");
    }
    @FXML
    public void opzione6R(ActionEvent event) {
        numPersone = numGiocatori-6;
        System.out.println("Hai selezionato 6 robot");
    }
    @FXML
    public void opzione7R(ActionEvent event) {
        numPersone = numGiocatori-7;
        System.out.println("Hai selezionato 7 robot");
    }
    @FXML
    public void opzione8R(ActionEvent event) {
        numPersone = numGiocatori-8;
        System.out.println("Hai selezionato 8 robot");
    }
}
