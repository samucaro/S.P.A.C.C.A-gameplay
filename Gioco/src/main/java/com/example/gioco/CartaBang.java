package com.example.gioco;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
public class CartaBang extends Carta{
    private final GameData gameData = GameData.getInstance();
    private Giocatore selectedGG = null;
    private final String desc = "Spara a un tuo avversario";
    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaBang.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public String getDesc() {
        return desc;
    }
    public void usaAbilita(OvalPaneController ovalPaneController, MainController mainController) {
        for(Carta c: gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).getMano()) {
            if(c instanceof CartaBang) {
                mainController.scartaCarte(c,gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()));
                break;
            }
        }
        ovalPaneController.startSelection().setOnSucceeded(event -> {
            boolean var = false;
            selectedGG = ovalPaneController.planetSelection();
            for(Carta c: selectedGG.getMano()) {
                if(c instanceof CartaMancato) {
                    var = true;
                    mainController.scartaCarte(c,selectedGG);
                    break;
                }
            }
            if (!var) {
                selectedGG.subisciDanno(1);
            }
            ovalPaneController.dannoSfera(selectedGG);
            ovalPaneController.fineSelezione();
            mainController.stopSelectionMC();
        });
    }
    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "Bang";
    }
}
