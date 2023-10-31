package com.example.spacca;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class HomePageController {
    @FXML
    private TextField username = null;
    @FXML
    private PasswordField password = null;
    private final Button login = new Button("Label del Pulsante");;
    private static int cont=1;
    private Task<Void> textTimer3, textTimer6, textTimer5, textTimer;

    @FXML
    public void initialize() {
        textTimer3 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int seconds = 1;
                while(seconds<=30) {
                    System.out.println(seconds);
                    seconds++;
                    Thread.sleep(1000);
                }
                return null;
            }
        };
        textTimer3.setOnSucceeded(event -> {
            username.deleteText(0, username.getLength());
            password.deleteText(0, password.getLength());
            System.out.println("Ora puoi reinserire le credenziali.");
            System.out.println("Consigliamo di ricercare le credenziali o fare il recupero delle credenziali." +
                    "Da ora in poi avrai una sola possibilità, se sbagli l'accesso verrà bloccato per 1 minuto");
        });

        textTimer6 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int seconds = 1;
                while(seconds<=60) {
                    System.out.println(seconds);
                    seconds++;
                    Thread.sleep(1000);
                }
                return null;
            }
        };
        textTimer6.setOnSucceeded(event -> {
            username.deleteText(0, username.getLength());
            password.deleteText(0, password.getLength());
            System.out.println("Ora puoi reinserire le credenziali.");
            System.out.println("Consigliamo di ricercare le credenziali o fare il recupero delle credenziali." +
                    "Da ora in poi avrai una sola possibilità, se sbagli l'accesso verrà bloccato per 5 minuti");
        });
        textTimer5 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int seconds = 1;
                while(seconds<=300000) {
                    System.out.println(seconds);
                    seconds++;
                    Thread.sleep(1000);
                }
                return null;
            }
        };
        textTimer5.setOnSucceeded(event -> {
            username.deleteText(0, username.getLength());
            password.deleteText(0, password.getLength());
            System.out.println("Ora puoi reinserire le credenziali.");
            System.out.println("Consigliamo di ricercare le credenziali o fare il recupero delle credenziali." +
                    "Da ora in poi avrai una sola possibilità, se sbagli l'accesso verrà bloccato definitivamente.");
        });
    }

    public void verifyLogin() {
        if(username.getText().equals("samucaro") && password.getText().equals("Fiorentina60S!")) {
            System.out.println("Accesso Riuscito!");
            cont=1;
        }
        else {
            System.out.println("Accesso Negato! Utente o Password errati!");
            if(cont == 4) {
                System.out.println("Se sbagli un'altra volta l'accesso verrà bloccato per 30 secondi");
            }
            else if(cont == 5) {
                login.setDisable(true);
                textTimer3.run();
                login.setDisable(false);
            }
            else if(cont == 6) {
                login.setDisable(true);
                textTimer6.run();
                login.setDisable(false);
            }
            else if(cont == 7) {
                login.setDisable(true);
                textTimer5.run();
                login.setDisable(false);
            }
            else if(cont >= 8) {
                login.setDisable(true);
            }
            cont++;
            username.deleteText(0, username.getLength());
            password.deleteText(0, password.getLength());
        }
    }
}
