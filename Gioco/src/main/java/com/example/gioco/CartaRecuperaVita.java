package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class CartaRecuperaVita extends Carta{
    private String desc = "Recuperi un punto vita";
    private final GameData gameData = GameData.getInstance();
    private Giocatore selectedGG = null;
    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaRecuperaVita.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public String getDesc() {
        return desc;
    }
    public void usaAbilita(OvalPaneController ovalPaneController, MainController mainController) {
        for(Carta carta: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if(carta instanceof CartaRecuperaVita) {
                mainController.scartaCarte(carta, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).cura(1);
        mainController.aggiornaCosa();
    }
    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "RecuperaVita";
    }
}
