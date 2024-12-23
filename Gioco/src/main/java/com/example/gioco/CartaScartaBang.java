package com.example.gioco;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CartaScartaBang extends Carta{
    private final GameData gameData;
    private final String desc;

    public CartaScartaBang() {
        gameData = GameData.getInstance();
        desc = "Tutti i tuoi avversari scartano una carta Bang";
    }

    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaScartaBang.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void usaAbilita(OvalPaneController ovalPaneController, TabelloneGiocoController tabelloneGiocoController) {
        for(Carta c: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if(c instanceof CartaScartaBang) {
                tabelloneGiocoController.scartaCarte(c, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        gestisciEventiAttacco(ovalPaneController, tabelloneGiocoController);
        if (!(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatoreRobot))
            tabelloneGiocoController.aggiornaCosa();
    }

    public void gestisciEventiAttacco(OvalPaneController ovalPaneController,  TabelloneGiocoController tabelloneGiocoController) {
        boolean checkCartaB;
        for (int i = 0; i < gameData.getGiocatoriPartita().size(); i++){
            checkCartaB = false;
            if ((i != gameData.getTurnoCorrente()) && (gameData.getGiocatoriPartita().get(i).getHpRimanente() != 0)) {
                for (int j = 0; j < gameData.getGiocatoriPartita().get(i).getMano().size(); j++){
                    if (gameData.getGiocatoriPartita().get(i).getMano().get(j) instanceof CartaBang){
                        checkCartaB = true;
                        ovalPaneController.dannoSfera(gameData.getGiocatoriPartita().get(i), false);
                        tabelloneGiocoController.scartaCarte(gameData.getGiocatoriPartita().get(i).getMano().get(j),gameData.getGiocatoriPartita().get(i));
                        break;
                    }
                }
                if (!checkCartaB){
                    gameData.getGiocatoriPartita().get(i).subisciDanno(1, tabelloneGiocoController);
                    ovalPaneController.dannoSfera(gameData.getGiocatoriPartita().get(i), true);
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
