package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class CartaSparaTutti extends Carta{
    private String desc = "Spara a tutti gli avversari contemporaneamente";
    private final GameData gameData = GameData.getInstance();
    private Giocatore selectedGG = null;
    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaSparaTutti.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void usaAbilita(OvalPaneController ovalPaneController, MainController mainController) {
        int cont = 0;
        boolean var;
        for(Carta carta1: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if(carta1 instanceof CartaSparaTutti) {
                mainController.scartaCarte(carta1, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        int io = gameData.getTurnoCorrente();
        for(Giocatore giocatore: gameData.getGiocatoriPartita()) {
            cont++;
            if(cont != io) {
                var = false;
                for(Carta carta2: selectedGG.getMano()) {
                    if(carta2 instanceof CartaMancato) {
                        var = true;
                        mainController.scartaCarte(carta2,selectedGG);
                        break;
                    }
                }
                if (!var) {
                    giocatore.subisciDanno(1);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "SparaTutti";
    }
}
