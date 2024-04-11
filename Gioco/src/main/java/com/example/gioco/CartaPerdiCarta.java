package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CartaPerdiCarta extends Carta{
    private final GameData gameData;
    private Giocatore giocatoreSelezionato;
    private final String desc;

    public CartaPerdiCarta() {
        gameData = GameData.getInstance();
        giocatoreSelezionato = null;
        desc = "Pesca carta casuale da un avversario a tua scelta";
    }

    public String getDesc() {
        return desc;
    }

    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaPescaCarta.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void usaAbilita(OvalPaneController ovalPaneController, MainController mainController) {
        if(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatoreRobot) {
            ovalPaneController.giocatoreSelezionato = null;
            gestisciEventiAttacco(ovalPaneController, mainController).handle(new ActionEvent());
        }
        else {
            ovalPaneController.startSelection().setOnSucceeded(event -> {
                gestisciEventiAttacco(ovalPaneController, mainController).handle(new ActionEvent());
                ovalPaneController.fineSelezione();
                mainController.stopSelectionMC();
            });
        }
    }

    public EventHandler<ActionEvent> gestisciEventiAttacco(OvalPaneController ovalPaneController, MainController mainController) {
        return event -> {
            giocatoreSelezionato = ovalPaneController.planetSelection();
            if(!giocatoreSelezionato.getMano().isEmpty()) {
                for(Carta c: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
                    if(c instanceof CartaPerdiCarta) {
                        mainController.scartaCarte(c, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                        break;
                    }
                }
                int val = (int) (Math.random() * (giocatoreSelezionato.getMano().size()));
                gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).addCarta(giocatoreSelezionato.getMano().get(val));
                giocatoreSelezionato.scarta(giocatoreSelezionato.getMano().get(val));
                ovalPaneController.dannoSfera(giocatoreSelezionato, false);
                ovalPaneController.fineSelezione();
                mainController.stopSelectionMC();
            }
        };
    }

    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "PerdiCarta";
    }
}
