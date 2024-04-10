package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Iterator;

public class CartaBang extends Carta{
    private final GameData gameData;
    private Giocatore selectedGG;
    private final String desc;

    public CartaBang() {
        gameData = GameData.getInstance();
        selectedGG = null;
        desc = "Spara a un tuo avversario";
    }

    public String getDesc() {
        return desc;
    }

    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaBang.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public void usaAbilita(OvalPaneController ovalPaneController, MainController mainController) {
        for (Carta c : gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if (c instanceof CartaBang) {
                mainController.scartaCarte(c, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatoreRobot){
            ovalPaneController.giocatoreSelezionato = null;
            gestisciEventiAttacco(ovalPaneController, mainController).handle(new ActionEvent());
        } else {
            ovalPaneController.startSelection().setOnSucceeded(event -> {
                gestisciEventiAttacco(ovalPaneController, mainController).handle(new ActionEvent());
                ovalPaneController.fineSelezione();
                mainController.stopSelectionMC();
            });
        }
    }
    public EventHandler<ActionEvent> gestisciEventiAttacco(OvalPaneController ovalPaneController,  MainController mainController) {
        return event -> {
            boolean var = false;
            selectedGG = ovalPaneController.planetSelection();
            for(Carta c: selectedGG.getMano()) {
                if(c instanceof CartaMancato) {
                    var = true;
                    mainController.scartaCarte(c,selectedGG);
                    ovalPaneController.dannoSfera(selectedGG, false);
                    break;
                }
            }
            if (!var) {
                selectedGG.subisciDanno(1);
                ovalPaneController.dannoSfera(selectedGG, true);
            }
        };
    }

    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "Bang";
    }
}
