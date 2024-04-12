package com.example.gioco;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CartaSparaTutti extends Carta{
    private final String desc;
    private final GameData gameData;

    public CartaSparaTutti() {
        gameData = GameData.getInstance();
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

    public void usaAbilita(OvalPaneController ovalPaneController, TabelloneGiocoController tabelloneGiocoController) {
        for(Carta c: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if(c instanceof CartaSparaTutti) {
                tabelloneGiocoController.scartaCarte(c, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        gestisciEventiAttacco(ovalPaneController, tabelloneGiocoController);
        if (!(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()) instanceof GiocatoreRobot))
            tabelloneGiocoController.aggiornaCosa();
    }
    public void gestisciEventiAttacco(OvalPaneController ovalPaneController,  TabelloneGiocoController tabelloneGiocoController) {
        boolean checkCartaM;
        for (int i = 0; i < gameData.getGiocatoriPartita().size(); i++){
            checkCartaM = false;
            if ((i != gameData.getTurnoCorrente()) && (gameData.getGiocatoriPartita().get(i).getHpRimanente() != 0)) {
                for (int j = 0; j < gameData.getGiocatoriPartita().get(i).getMano().size(); j++){
                    if (gameData.getGiocatoriPartita().get(i).getMano().get(j) instanceof CartaMancato){
                        checkCartaM = true;
                        tabelloneGiocoController.scartaCarte(gameData.getGiocatoriPartita().get(i).getMano().get(j),gameData.getGiocatoriPartita().get(i));
                        ovalPaneController.dannoSfera(gameData.getGiocatoriPartita().get(i), false);
                        break;
                    }
                }
                if (!checkCartaM){
                    gameData.getGiocatoriPartita().get(i).subisciDanno(1);
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
        return "SparaTutti";
    }
}
