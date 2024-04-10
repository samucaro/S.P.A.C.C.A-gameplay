package com.example.gioco;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CartaPerdiCarta extends Carta{
    private final GameData gameData;
    private Giocatore giocatoreSelezionato;
    private final String desc;

    public CartaPerdiCarta() {
        gameData = GameData.getInstance();
        giocatoreSelezionato = null;
        desc = "Pesca carta casuale da un avversario a tua scelta";
    }

    public String getDesc() {
        return desc;
    }

    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaPescaCarta.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void usaAbilita(OvalPaneController ovalPaneController, MainController mainController) {
        for(Carta c: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if(c instanceof CartaPerdiCarta) {
                mainController.scartaCarte(c, gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        gestisciEventiAttacco(ovalPaneController, mainController);
    }

    public EventHandler<ActionEvent> gestisciEventiAttacco(OvalPaneController ovalPaneController, MainController mainController) {
        return event -> {
            giocatoreSelezionato = ovalPaneController.planetSelection();
            if(giocatoreSelezionato.getMano().isEmpty()) {
                System.out.println("VUOTAMANOGIOCATORESELEZIONATOOOOOOOOOOOOOO");
                gestisciEventiAttacco(ovalPaneController, mainController).handle(new ActionEvent());
            }
            else {
                int val = (int) (Math.random() * (giocatoreSelezionato.getMano().size()));
                gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).addCarta(giocatoreSelezionato.getMano().get(val));
                giocatoreSelezionato.scarta(giocatoreSelezionato.getMano().get(val));
                ovalPaneController.dannoSfera(giocatoreSelezionato, true);
                ovalPaneController.fineSelezione();
                mainController.stopSelectionMC();
            }
        };
    }

    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "PerdiCarta";
    }
}
