package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CartaBang extends Carta{
    private final GameData gameData;
    private Giocatore selectedGG;
    private final String desc;

    public CartaBang() {
        gameData = GameData.getInstance();
        selectedGG = null;
        desc = "Spara a un tuo avversario";
    }

    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaBang.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public void usaAbilita(OvalPaneController ovalPaneController, TabelloneGiocoController tabelloneGiocoController) {
        for (Carta c : gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if (c instanceof CartaBang) {
                tabelloneGiocoController.scartaCarte(c, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatoreRobot){
            ovalPaneController.giocatoreSelezionato = null;
            gestisciEventiAttacco(ovalPaneController, tabelloneGiocoController);
        } else {
            ovalPaneController.startSelection().setOnSucceeded(event -> {
                gestisciEventiAttacco(ovalPaneController, tabelloneGiocoController);
                ovalPaneController.fineSelezione();
                tabelloneGiocoController.stopSelectionMC();
            });
        }
    }
    public void gestisciEventiAttacco(OvalPaneController ovalPaneController,  TabelloneGiocoController tabelloneGiocoController) {
            boolean var = false;
            selectedGG = ovalPaneController.planetSelection();
            for(Carta c: selectedGG.getMano()) {
                if(c instanceof CartaMancato) {
                    var = true;
                    tabelloneGiocoController.scartaCarte(c,selectedGG);
                    ovalPaneController.dannoSfera(selectedGG, false);
                    break;
                }
            }
            if (!var) {
                selectedGG.subisciDanno(1, tabelloneGiocoController);
                ovalPaneController.dannoSfera(selectedGG, true);
            }
    }

    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "Bang";
    }
}
