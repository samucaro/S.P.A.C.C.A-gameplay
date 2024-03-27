package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class CartaPerdiCarta extends Carta{
    private final GameData gameData = GameData.getInstance();
    private Giocatore selectedGG = null;
    private String desc = "Pesca la prima carta da un avversario a tua scelta";
    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaPescaCarta.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public String getDesc() {
        return desc;
    }
    public void usaAbilita(OvalPaneController ovalPaneController, MainController mainController) {
        for(Carta c: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if(c instanceof CartaPerdiCarta) {
                mainController.scartaCarte(c,gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        ovalPaneController.startSelection().setOnSucceeded(event -> {
            selectedGG = ovalPaneController.planetSelection();
            mainController.scartaCarte(selectedGG.getMano().get((int) (Math.random() * (selectedGG.getMano().size()))),selectedGG);
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
        return "PerdiCarta";
    }
}
