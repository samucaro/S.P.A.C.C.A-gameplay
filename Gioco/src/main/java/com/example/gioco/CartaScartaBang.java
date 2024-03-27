package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class CartaScartaBang extends Carta{
    private final GameData gameData = GameData.getInstance();
    private String desc = "Tutti i tuoi avversari scartano una carta Bang";
    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaScartaBang.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public String getDesc() {
        return desc;
    }
    public void usaAbilita(OvalPaneController ovalPaneController, MainController mainController) {
        for(Carta c: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if(c instanceof CartaScartaBang) {
                mainController.scartaCarte(c,gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        boolean checkCartaB;
        for (int i = 0; i < gameData.getGiocatoriPartita().size(); i++){
            if (i!=gameData.getTurnoCorrente()) {
                checkCartaB = false;
                for (int j = 0; j < gameData.getGiocatoriPartita().get(i).getMano().size(); j++){
                    if (gameData.getGiocatoriPartita().get(i).getMano().get(j) instanceof CartaBang){
                        checkCartaB = true;
                        mainController.scartaCarte(gameData.getGiocatoriPartita().get(i).getMano().get(j),gameData.getGiocatoriPartita().get(i));
                    }
                }
                if (!checkCartaB){
                    gameData.getGiocatoriPartita().get(i).subisciDanno(1);
                    ovalPaneController.dannoSfera(gameData.getGiocatoriPartita().get(i));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "ScartaBang";
    }
}
