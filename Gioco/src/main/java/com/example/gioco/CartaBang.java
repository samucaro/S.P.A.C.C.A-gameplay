package com.example.gioco;

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
        for(Carta c: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if(c instanceof CartaBang) {
                mainController.scartaCarte(c,gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        gestisciEventiAttacco(ovalPaneController, mainController);
    }

    public void gestisciEventiAttacco(OvalPaneController ovalPaneController,  MainController mainController) {
        ovalPaneController.startSelection().setOnSucceeded(event -> {
            boolean var = false;
            selectedGG = ovalPaneController.planetSelection();
            for(Carta c: selectedGG.getMano()) {
                if(c instanceof CartaMancato) {
                    var = true;
                    mainController.scartaCarte(c,selectedGG);
                    break;
                }
            }
            if (!var) {
                selectedGG.subisciDanno(1);
            }
            ovalPaneController.dannoSfera(selectedGG);
            ovalPaneController.fineSelezione();
            mainController.stopSelectionMC();
        });
    }
    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "Bang";
    }
}
