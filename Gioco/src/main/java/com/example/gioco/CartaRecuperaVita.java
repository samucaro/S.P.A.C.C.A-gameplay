package com.example.gioco;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CartaRecuperaVita extends Carta{
    private final String desc;
    private final GameData gameData;

    public CartaRecuperaVita() {
        gameData = GameData.getInstance();
        desc = "Recuperi un punto vita";
    }

    public String getDesc() {
        return desc;
    }

    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaRecuperaVita.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public void usaAbilita(OvalPaneController ovalPaneController, MainController mainController) {
        if (gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getHpRimanente() < 5) {
            for (Carta carta : gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
                if (carta instanceof CartaRecuperaVita) {
                    mainController.scartaCarte(carta, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                    break;
                }
            }
            gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).cura(1);
            mainController.aggiornaCosa();
        }
    }
    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "RecuperaVita";
    }
}
