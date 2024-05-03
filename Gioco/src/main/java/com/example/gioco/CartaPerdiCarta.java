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

    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaPescaCarta.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void usaAbilita(OvalPaneController ovalPaneController, TabelloneGiocoController tabelloneGiocoController) {
        if(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatoreRobot) {
            ovalPaneController.giocatoreSelezionato = null;
            gestisciEventiAttacco(ovalPaneController, tabelloneGiocoController);
        }
        else {
            ovalPaneController.startSelection().setOnSucceeded(event -> {
                gestisciEventiAttacco(ovalPaneController, tabelloneGiocoController);
                ovalPaneController.fineSelezione();
                tabelloneGiocoController.stopSelectionMC();
            });
        }
    }

    public void gestisciEventiAttacco(OvalPaneController ovalPaneController, TabelloneGiocoController tabelloneGiocoController) {
            giocatoreSelezionato = ovalPaneController.planetSelection();
            if(!giocatoreSelezionato.getMano().isEmpty()) {
                int index = 0;
                for(Carta c: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
                    if(c instanceof CartaPerdiCarta) {
                        index = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().indexOf(c);
                        tabelloneGiocoController.scartaCarte(c, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                        break;
                    }
                }
                int val = (int) (Math.random() * (giocatoreSelezionato.getMano().size()));
                gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).setMano(index, giocatoreSelezionato.getMano().get(val));
                giocatoreSelezionato.scarta(giocatoreSelezionato.getMano().get(val));
                ovalPaneController.dannoSfera(giocatoreSelezionato, false);
            }
    }

    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "PerdiCarta";
    }
}
