package com.example.gioco;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CartaMancato extends Carta {
    private final String desc;

    public CartaMancato() {
        desc = "Colpo schivato!";
    }

    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaMancato.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public void usaAbilita(OvalPaneController ovalPaneController, TabelloneGiocoController tabelloneGiocoController) {
    }
    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "Mancato";
    }
}
