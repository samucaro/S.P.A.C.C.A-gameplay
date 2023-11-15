package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginPageController {
    private GameData gameData = GameData.getInstance();
    private Scene scene;
    private Parent root;
    private int numPersone;
    private int numGiocatori;
    @FXML
    private CheckBox rispostaSi;
    @FXML
    private CheckBox rispostaNo;
    @FXML
    private MenuButton numRobot;
    @FXML
    private Button startGame;

    @FXML
    public void initialize() {
        numPersone = 0;
        numGiocatori = 0;
    }

    public void opzione1(ActionEvent event) {
        numGiocatori = 3;
        System.out.println("Hai selezionato 3 giocatori");
    }

    public void opzione2(ActionEvent event) {
        numGiocatori = 4;
        System.out.println("Hai selezionato 4 giocatori");
    }

    public void opzione3(ActionEvent event) {
        numGiocatori = 5;
        System.out.println("Hai selezionato 5 giocatori");
    }

    public void opzione4(ActionEvent event) {
        numGiocatori = 6;
        System.out.println("Hai selezionato 6 giocatori");
    }

    public void opzione5(ActionEvent event) {
        numGiocatori = 7;
        System.out.println("Hai selezionato 7 giocatori");
    }

    public void opzione6(ActionEvent event) {
        numGiocatori = 8;
        System.out.println("Hai selezionato 8 giocatori");
    }


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

    public void giocoSenzaRobot(ActionEvent event) {
        if(rispostaNo.isSelected()) {
            rispostaSi.setSelected(false);
            numRobot.setVisible(false);
        }
        startGame.setDisable(false);
        System.out.println("Giochi senza robot");
    }


    public void setNumRobot(MouseEvent event) {
        for(int i=0; i<numGiocatori; i++) {
            numRobot.getItems().get(i).setVisible(true);
        }
        for(int i=7; i>numGiocatori-1; i--) {
            numRobot.getItems().get(i).setVisible(false);
        }
        numRobot.show();
    }


    public void opzione1R(ActionEvent event) {
        numPersone = numGiocatori-1;
        System.out.println("Hai selezionato 1 robot, numero persone: " + numPersone);
        startGame.setDisable(false);
    }

    public void opzione2R(ActionEvent event) {
        numPersone = numGiocatori-2;
        System.out.println("Hai selezionato 2 robot, numero persone: " + numPersone);
        startGame.setDisable(false);
    }

    public void opzione3R(ActionEvent event) {
        numPersone = numGiocatori-3;
        System.out.println("Hai selezionato 3 robot, numero persone: " + numPersone);
        startGame.setDisable(false);
    }

    public void opzione4R(ActionEvent event) {
        numPersone = numGiocatori-4;
        System.out.println("Hai selezionato 4 robot, numero persone: " + numPersone);
        startGame.setDisable(false);
    }
    public void opzione5R(ActionEvent event) {
        numPersone = numGiocatori-5;
        System.out.println("Hai selezionato 5 robot, numero persone: " + numPersone);
        startGame.setDisable(false);
    }
    public void opzione6R(ActionEvent event) {
        numPersone = numGiocatori-6;
        System.out.println("Hai selezionato 6 robot, numero persone: " + numPersone);
        startGame.setDisable(false);
    }
    public void opzione7R(ActionEvent event) {
        numPersone = numGiocatori-7;
        System.out.println("Hai selezionato 7 robot, numero persone: " + numPersone);
        startGame.setDisable(false);
    }
    public void opzione8R(ActionEvent event) {
        numPersone = numGiocatori-8;
        System.out.println("Hai selezionato 8 robot, numero persone: " + numPersone);
        startGame.setDisable(false);
    }

    public void switchToAccessPlayer(ActionEvent event) throws IOException {
        gameData.setNumero(numGiocatori);
        root = FXMLLoader.load(getClass().getResource("AccessPlayerPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*@FXML
    public void assegnaRuoli(ActionEvent event) {
        //creaGiocatore();
        Ruoli[] array = new Ruoli[numGiocatori];
        if(numGiocatori==3) {
            array[0]=Ruoli.SCERIFFO;
            array[1]=Ruoli.RINNEGATO;
            array[2]=Ruoli.FUORILEGGE;
            for(int i=0; i<numGiocatori; i++) {
                int ref = (int) (Math.random()*numGiocatori)+1;
                switch(ref) {
                    case 1:
                        array[i]=Ruoli.SCERIFFO;
                        break;
                    case 2:
                        array[i]=Ruoli.RINNEGATO;
                }
            }

        }
        else if(numGiocatori==4) {

        }
        else if(numGiocatori==5) {

        }
        else if(numGiocatori==6) {

        }
        else if(numGiocatori==7) {

        }
        else {

        }
    }
    @FXML
    public void assegnaPersonaggi(ActionEvent event) {
        int val = (int) (Math.random()*(4-1) + 1);
        System.out.println(val);
    }*/
}
