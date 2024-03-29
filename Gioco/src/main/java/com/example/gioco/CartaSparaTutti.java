package com.example.gioco;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CartaSparaTutti extends Carta{
    private final String desc;
    private final GameData gameData;
    private final Giocatore giocatoreSelezionato;

    public CartaSparaTutti() {
        gameData = GameData.getInstance();
        giocatoreSelezionato = null;
        desc = "Spara a tutti gli avversari contemporaneamente";
    }

    public String getDesc() {
        return desc;
    }

    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaSparaTutti.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
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
                for(Carta carta2: giocatoreSelezionato.getMano()) {
                    if(carta2 instanceof CartaMancato) {
                        var = true;
                        mainController.scartaCarte(carta2, giocatoreSelezionato);
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
