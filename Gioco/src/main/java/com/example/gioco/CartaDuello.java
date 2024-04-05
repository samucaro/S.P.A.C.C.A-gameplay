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
                System.out.println("lunghezza mano avv: " + selectedGG.getMano().size());
                System.out.println("carta avv: " + cartaAvversario.toStringNome());
                System.out.println("indice avv: " + i);
                if (cartaAvversario instanceof CartaBang) {
                    contAvversario++;
                    //System.out.println("contAvversario: " + contAvversario);
                    mainController.scartaCarte(cartaAvversario, selectedGG);
                    i--;
                    System.out.println("Scarto avv");
                    if(!controlloBang(mainController)) {
                        gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).subisciDanno(2);
                        ovalPaneController.dannoSfera(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                        //break;
                    }
                }
            }
            if (contTuo == contAvversario) {
                selectedGG.subisciDanno(2);
                ovalPaneController.dannoSfera(selectedGG);
            }
            ovalPaneController.fineSelezione();
            mainController.stopSelectionMC();
            //System.out.println(gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().size());
        });
        contAvversario = 0;
        contTuo = 0;
    }

    private boolean controlloBang(MainController mainController) {
        Carta cartaTua;
        boolean var = false;
        for (int i = 0; i < gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().size(); i++) {
            System.out.println("lunghezza mano tuo: " + gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().size());
            cartaTua = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano().get(i);
            System.out.println("carta tua: " + cartaTua.toStringNome());
            System.out.println("indice tuo: " + i);
            if (cartaTua instanceof CartaBang) {
                contTuo++;
                //System.out.println("contTuo: " + contTuo);
                mainController.scartaCarte(cartaTua, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                System.out.println("Scarto tuo");
                var = true;
                break;
            }
        }
        System.out.println("booleano tuo: " + var);
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
