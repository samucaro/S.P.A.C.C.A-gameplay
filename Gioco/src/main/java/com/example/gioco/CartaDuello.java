package com.example.gioco;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CartaDuello extends Carta {
    private final GameData gameData;
    private Giocatore selectedGG;
    private final String desc;
    private static int contAvversario;
    private static int contTuo;

    public CartaDuello() {
        gameData = GameData.getInstance();
        selectedGG = null;
        desc = "A turno tu e il tuo avversario" +
                "scartate un Bang, il primo che rimane senza perde un punto vita. Parte l'avversario.";
    }

    public String getDesc() {
        return desc;
    }

    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaDuello.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public void usaAbilita(OvalPaneController ovalPaneController, MainController mainController) {
        for(Carta c: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if(c instanceof CartaDuello) {
                mainController.scartaCarte(c, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        gestisciEventiAttacco(ovalPaneController, mainController);
    }

    public void gestisciEventiAttacco(OvalPaneController ovalPaneController,  MainController mainController) {
        ovalPaneController.startSelection().setOnSucceeded(event -> {
            contAvversario = 0;
            contTuo = 0;
            Carta cartaAvversario;
            selectedGG = ovalPaneController.planetSelection();
            for (int i = 0; i < selectedGG.getMano().size(); i++) {
                cartaAvversario = selectedGG.getMano().get(i);
                if (cartaAvversario instanceof CartaBang) {
                    contAvversario++;
                    mainController.scartaCarte(cartaAvversario, selectedGG);
                    i--;
                    if (!controlloBang(mainController)) {
                        gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).subisciDanno(2);
                        ovalPaneController.dannoSfera(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()), true);
                        break;
                    }
                }
            }
            if (contTuo == contAvversario) {
                selectedGG.subisciDanno(2);
                ovalPaneController.dannoSfera(selectedGG, true);
            }
            ovalPaneController.fineSelezione();
            mainController.stopSelectionMC();
        });
    }

    private boolean controlloBang(MainController mainController) {
        Carta cartaTua;
        boolean var = false;
        for (int i = 0; i < gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().size(); i++) {
            cartaTua = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().get(i);
            if (cartaTua instanceof CartaBang) {
                contTuo++;
                mainController.scartaCarte(cartaTua, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                var = true;
                break;
            }
        }
        return var;
    }

    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "Duello";
    }
}
