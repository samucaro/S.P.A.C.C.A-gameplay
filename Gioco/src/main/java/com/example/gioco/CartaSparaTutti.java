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
        for(Carta c: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if(c instanceof CartaSparaTutti) {
                mainController.scartaCarte(c, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        gestisciEventiAttacco(ovalPaneController, mainController);
    }

    public void gestisciEventiAttacco(OvalPaneController ovalPaneController,  MainController mainController) {
        boolean checkCartaM;
        for (int i = 0; i < gameData.getGiocatoriPartita().size(); i++){
            checkCartaM = false;
            if (i != gameData.getTurnoCorrente()) {
                for (int j = 0; j < gameData.getGiocatoriPartita().get(i).getMano().size(); j++){
                    System.out.println(gameData.getGiocatoriPartita().get(i).getNome() + " " + j);
                    if (gameData.getGiocatoriPartita().get(i).getMano().get(j) instanceof CartaMancato){
                        checkCartaM = true;
                        mainController.scartaCarte(gameData.getGiocatoriPartita().get(i).getMano().get(j),gameData.getGiocatoriPartita().get(i));
                        break;
                    }
                }
                if (!checkCartaM){
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
        return "SparaTutti";
    }
}
