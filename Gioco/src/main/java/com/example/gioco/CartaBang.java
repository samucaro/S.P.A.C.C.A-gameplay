package com.example.gioco;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
public class CartaBang extends Carta{
    private GameData gameData = GameData.getInstance();
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
                gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente()).scarta(c);
                break;
            }
        }
        ovalPaneController.startSelection().setOnSucceeded(event -> {
            selectedGG = ovalPaneController.planetSelection();
            ovalPaneController.fineSelezione();
            mainController.stopSelectionMC();
            for(Carta c: selectedGG.getMano()) {
                if(c instanceof CartaMancato) {
                    selectedGG.scarta(c);
                    break;
                }
            }
        });;
    }
    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "Bang";
    }
}
